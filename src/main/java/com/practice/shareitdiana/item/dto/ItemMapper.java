package com.practice.shareitdiana.item.dto;

import com.practice.shareitdiana.item.Item;
import com.practice.shareitdiana.item.ItemStatus;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item fromCreate(ItemCreateDto createdItem, int ownerId) {
        Item item = new Item();
        item.setName(createdItem.getName());
        item.setOwnerId(ownerId);
        item.setDescription(createdItem.getDescription());
        item.setStatus(createdItem.getAvailable() != null ?
                (createdItem.getAvailable() ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE) : ItemStatus.AVAILABLE);
        return item;
    }

    public Item fromCreate(ItemCreateDto createdItem) {
        Item item = new Item();
        item.setName(createdItem.getName());
        item.setDescription(createdItem.getDescription());
        item.setStatus(createdItem.getAvailable() != null ?
                (createdItem.getAvailable() ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE) : ItemStatus.AVAILABLE);
        return item;
    }

    public Item fromUpdate(Item existingItem, ItemUpdateDto updatedItem) {
        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
        if (updatedItem.getName() != null && !updatedItem.getName().trim().isEmpty()) {
            existingItem.setName(updatedItem.getName());
        }
        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
        if (updatedItem.getDescription() != null && !updatedItem.getDescription().trim().isEmpty()) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
        if (updatedItem.getAvailable() != null) {
            existingItem.setStatus(updatedItem.getAvailable() ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE);
        }
        return existingItem;
    }

//    public Item fromUpdate(Item existingItem, ItemUpdateDto updatedItem) {
//        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
//        if (updatedItem.getName() != null) {
//            existingItem.setName(updatedItem.getName());
//        }
//        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
//        if (updatedItem.getDescription() != null) {
//            existingItem.setDescription(updatedItem.getDescription());
//        }
//        // если НЕ пустой параметр, то ОБНОВЛЯЕМ имеющуюся вещь
//        if (updatedItem.getAvailable() != null) {
//            existingItem.setStatus(updatedItem.getAvailable() ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE);
//        }
//        return existingItem;
//    }

    public ItemResponseDto toResponse(Item item) {
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setId(item.getId());
        itemResponseDto.setOwnerId(item.getOwnerId());
        itemResponseDto.setName(item.getName());
        itemResponseDto.setDescription(item.getDescription());
        // если выражение ТРУ, то ДОСТУПЕН
        // иначе ФОЛЗ = НЕДОСТУПЕН
        itemResponseDto.setAvailable(item.getStatus() == ItemStatus.AVAILABLE);
        return itemResponseDto;
    }
}
