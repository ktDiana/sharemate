package com.practice.shareitdiana.item;

import com.practice.shareitdiana.item.dto.ItemCreateDto;
import com.practice.shareitdiana.item.dto.ItemMapper;
import com.practice.shareitdiana.item.dto.ItemResponseDto;
import com.practice.shareitdiana.item.dto.ItemUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")

public class ItemController {

    public final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    // GET - все вещи ОПРЕДЕЛЕННОГО ВЛАДЕЛЬЦА (если он null - тогда просто ВСЕ ВЕЩИ)
    @GetMapping
    public ResponseEntity<Collection<ItemResponseDto>> findAllItems(
            @RequestHeader(value = "X-Sharer-User-Id", required = false) Integer ownerId) {

        // создаём коллэкшн -------- айди владельца null?
        Collection<Item> items = (ownerId == null)
                ? itemService.findAllItems()  // Да, налл, так что передаём вообще всё
                : itemService.findAllItemsByOwner(ownerId); // айди знаем -> передаём вещи владельца

        return ResponseEntity.ok(items.stream()
                .map(itemMapper::toResponse)
                .toList());
    }

    // GET - вещь по айди вещи
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItemById(@PathVariable int itemId) {
        return ResponseEntity.ok(
                itemMapper.toResponse(itemService.findItemById(itemId)));
    }

    // GET - доступные вещи владельца (если он null - тогда просто все доступные вещи)
    @GetMapping("/search")
    public ResponseEntity<List<ItemResponseDto>> searchAvailableItems(
            @RequestParam String text,
            @RequestHeader(value = "X-Sharer-User-Id", required = false) Integer ownerId) {

        if (text == null || text.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        // создаём коллэкшн -------- айди владельца null?
        Collection<Item> items = (ownerId == null)
                ? itemService.searchAllAvailableItems(text)                     // Да, налл, так что передаём вообще все достуные
                : itemService.searchAvailableItemsByOwner(text, ownerId);       // Ищем среди вещей владельца
        return ResponseEntity.ok(items.stream()
                .map(itemMapper::toResponse)
                .toList());
    }

    // POST - новую вещь
    @PostMapping()
    public ResponseEntity<ItemResponseDto> createItem(
            @Valid @RequestBody ItemCreateDto createdItem,
            @RequestHeader("X-Sharer-User-Id") int ownerId) {

        // сначала сохранили через маппер ДТО В ОБЪЕКТ для сервиса
        Item item = itemMapper.fromCreate(createdItem);
        // объект маппера кидаем в сервис
        Item savedItem = itemService.createItem(item, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemMapper.toResponse(savedItem));
    }

    // PATCH - обновить вещь
    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable int itemId,
            @Valid @RequestBody ItemUpdateDto updatedItem,
            @RequestHeader("X-Sharer-User-Id") int userId) {

        // находим существующую вещь
        Item existing = itemService.findItemById(itemId);
        // если наш владелец вещи оказывается не наш владелец вещи
        if (existing.getOwner().getId() != userId) {
            // HttpStatus.FORBIDDEN (403) — означает: доступ запрещён, даже если пользователь аутентифицирован.
            // Пример: Пользователь 1 хочет удалить вещь пользователя 2, но не имеет на это прав.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // если это наш владелец, то обновляем вещь ДТО В ОБЪЕКТ
        Item toUpdate = itemMapper.fromUpdate(existing, updatedItem);
        // сохраняем в сервисе -> в хранилище
        Item updated = itemService.updateItem(toUpdate);
        return ResponseEntity.ok()
                .body(itemMapper.toResponse(updated));
    }

    // DELETE - удалить вещь по айди
    @DeleteMapping("/{itemId}")
    // Void, в отличие от void, — это объектный тип (класс java.lang.Void). Он используется в дженериках ...
    // (ResponseEntity<Void>), чтобы явно указать, что тело ответа отсутствует.
    public ResponseEntity<Void> deleteItem(
            @PathVariable int itemId,
            @RequestHeader("X-Sharer-User-Id") int ownerId) {

        Item item = itemService.findItemById(itemId);
        if (item.getOwner().getId() != ownerId) {
            // снова не даём доступ
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}

