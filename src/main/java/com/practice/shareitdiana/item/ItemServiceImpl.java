package com.practice.shareitdiana.item;

import com.practice.shareitdiana.exception.ItemNotFoundException;
import com.practice.shareitdiana.exception.UserNotFoundException;
import com.practice.shareitdiana.user.User;
import com.practice.shareitdiana.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j

public class ItemServiceImpl implements ItemService {

    public final ItemRepository itemRepository;
    public final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<Item> findAllItems() {
        return itemRepository.findAllItems();
    }

    // поиск доступных вещей В ПРИНЦИПЕ
    @Override
    public Collection<Item> searchAllAvailableItems(String text) {
        // если пользователь вообще ничего не ввёл
        if (text == null || text.isBlank()) {
            return List.of();
        }
        // работа с текстом - чувствительность к регистру
        String lowerText = text.toLowerCase();
        Collection<Item> result = itemRepository.findAllItems().stream()
                .filter(item -> item.getStatus() == ItemStatus.AVAILABLE)
                .filter(item -> {
                    boolean matches = item.getName().toLowerCase().contains(lowerText)
                            || item.getDescription().toLowerCase().contains(lowerText);
                    return matches;
                })
                .toList();
        log.info("Найдено {} вещей", result.size());
        return result;
    }

    // поиск всех вещей ОПРЕДЕЛЕННОГО ВЛАДЕЛЬЦА
    @Override
    public Collection<Item> findAllItemsByOwner(int ownerId) {
        userRepository.findUserById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным id (" + ownerId + ") не найден"));
        Collection<Item> result = itemRepository.findAllItems().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .toList();
        log.info("Найдено {} вещей у владельца ({})", result.size(), ownerId);
        return result;
    }

    // поиск доступных вещей У ОПРЕДЕЛЕННОГО ВЛАДЕЛЬЦА
    @Override
    public Collection<Item> searchAvailableItemsByOwner(String text, Integer ownerId) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        String lowerText = text.toLowerCase();
        return findAllItemsByOwner(ownerId).stream()
                .filter(item -> item.getStatus() == ItemStatus.AVAILABLE)
                .filter(item -> item.getName().toLowerCase().contains(lowerText)
                        || item.getDescription().toLowerCase().contains(lowerText))
                .toList();
    }

    @Override
    public Item findItemById(int id) {
        return itemRepository.findItemById(id)
                .orElseThrow(() -> {
                    log.error("Вещь с данным id ({}) не найдена", id);
                    return new ItemNotFoundException("Вещь с данным id (" + id + ") не найдена");
                });
    }

    @Override
    //@Transactional
    public Item createItem(Item item, int ownerId) {
        User owner = userRepository.findUserById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным id (" + ownerId + ") не найден"));
        item.setOwnerId(ownerId);
        log.info("Пользователь {} добавил новую вещь: {}", owner.getName(), item.getName());
        return itemRepository.createItem(item);
    }

    @Override
    //@Transactional
    public Item updateItem(Item updatedItem) {
        Item item = itemRepository.findItemById(updatedItem.getId())
                .orElseThrow(() -> new ItemNotFoundException("Вещь с данным id (" + updatedItem.getId() + ") не найдена"));
        log.info("Пользователь ({}) обновил вещь: {}", updatedItem.getOwnerId(), item.getName());
        return itemRepository.updateItem(updatedItem);
    }

    @Override
    public void deleteItem(int id) {
        Item item = itemRepository.findItemById(id)
                .orElseThrow(() -> new ItemNotFoundException("Вещь с данным id (" + id + ") не найдена"));
        log.info("Пользователь ({}) удалил вещь: {}", item.getOwnerId(), item.getName());
        itemRepository.deleteItemById(id);
    }

}