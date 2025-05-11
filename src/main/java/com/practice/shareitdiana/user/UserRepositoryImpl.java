package com.practice.shareitdiana.user;

import com.practice.shareitdiana.exception.UserNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor

public class UserRepositoryImpl implements UserRepository {

    // временное хранилище данных о пользователях вместо базы
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @Override   // может вернуть пользователя, а может и не вернуть
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override   // может вернуть пользователя, а может и не вернуть
    public Optional<User> findUserByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Collection<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean existsById(int id) {
        return users.containsKey(id);
    }

    @Override
    public User createUser(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(int id) {
        users.remove(id);
    }
}
