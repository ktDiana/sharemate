package com.practice.shareitdiana.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@RequiredArgsConstructor

public class ItemRepositoryImpl implements ItemRepository {

    // временное хранилище данных о вещах вместо базы
    private final HashMap<Integer, Item> items = new HashMap<>();
    private int nextId = 1;

    @Override
    public Collection<Item> findAllItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Optional<Item> findItemById(int id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public boolean existsById(int id) {
        return findItemById(id).isPresent();
    }

    @Override
    public Item createItem(Item item) {
        item.setId(nextId++);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void deleteItemById(int id) {
        items.remove(id);
    }
}
