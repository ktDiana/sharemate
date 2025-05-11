package com.practice.shareitdiana.item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findItemById(int id);

    Collection<Item> findAllItems();

    boolean existsById(int id);

    Item createItem(Item item);

    Item updateItem(Item item);

    void deleteItemById(int id);
}
