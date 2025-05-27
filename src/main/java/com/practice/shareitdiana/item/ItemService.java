package com.practice.shareitdiana.item;

import com.practice.shareitdiana.item.dto.ItemCreateDto;
import com.practice.shareitdiana.item.dto.ItemResponseDto;
import com.practice.shareitdiana.item.dto.ItemUpdateDto;

import java.util.Collection;
import java.util.List;

// Просто интерфейс с бизнес-логикой

public interface ItemService {

    Collection<Item> findAllItems();

    Item findItemById(int id);

    Collection<Item> findAllItemsByOwner(int userId);

    Collection<Item> searchAvailableItemsByOwner(String text, Integer ownerId);

    Item createItem(Item item, int ownerId);

    Item updateItem(Item item);

    void deleteItem(int id);

    Collection<Item> searchAllAvailableItems(String text);

}