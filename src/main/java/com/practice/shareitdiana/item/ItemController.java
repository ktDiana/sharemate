package com.practice.shareitdiana.item;

import com.practice.shareitdiana.item.dto.ItemCreateDto;
import com.practice.shareitdiana.item.dto.ItemMapper;
import com.practice.shareitdiana.item.dto.ItemResponseDto;
import com.practice.shareitdiana.item.dto.ItemUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@Validated

public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<ItemResponseDto>> findAllItems(
            @RequestHeader(value = "X-Sharer-User-Id", required = false) Integer ownerId) {

        Collection<Item> items = (ownerId == null)
                ? itemService.findAllItems()
                : itemService.findAllItemsByOwner(ownerId);

        return ResponseEntity.ok(
                items.stream()
                        .map(itemMapper::toResponse)
                        .toList());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItemById(@PathVariable int itemId) {
        Item item = itemService.findItemById(itemId);
        return ResponseEntity.ok(itemMapper.toResponse(item));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemResponseDto>> searchAvailableItems(
            @RequestParam String text,
            @RequestHeader(value = "X-Sharer-User-Id", required = false) Integer ownerId) {

        if (text == null || text.isBlank()) {
            return ResponseEntity.ok(List.of());
        }

        Collection<Item> items = (ownerId == null)
                ? itemService.searchAllAvailableItems(text)
                : itemService.searchAvailableItemsByOwner(text, ownerId);

        return ResponseEntity.ok(
                items.stream()
                        .map(itemMapper::toResponse)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(
            @Valid @RequestBody ItemCreateDto createdItem,
            @RequestHeader("X-Sharer-User-Id") Integer ownerId) {

        Item item = itemMapper.fromCreate(createdItem, ownerId);
        Item savedItem = itemService.createItem(item, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemMapper.toResponse(savedItem));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable int itemId,
            @Valid @RequestBody ItemUpdateDto updatedItem,
            @RequestHeader("X-Sharer-User-Id") Integer ownerId) {

        Item existing = itemService.findItemById(itemId);
        if (existing.getOwnerId() != ownerId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Item updated = itemMapper.fromUpdate(existing, updatedItem);
        Item saved = itemService.updateItem(updated);
        return ResponseEntity.ok(itemMapper.toResponse(saved));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable int itemId,
            @RequestHeader("X-Sharer-User-Id") Integer ownerId) {

        Item item = itemService.findItemById(itemId);
        if (item.getOwnerId() != ownerId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}