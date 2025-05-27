package com.practice.shareitdiana.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j

public class UserRepositoryImpl implements UserRepository {

    // временное хранилище данных о пользователях вместо базы
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();             // ПОЧТЫЫЫЫЫЫЫ
    private int countId = 0;

    @Override
    public User save(User user) {           // заместитель create
        int id = ++countId;
        user.setId(id);
        users.put(id, user);
        emails.add(user.getEmail());
        return user;
    }

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

//    @Override
//    public boolean existsById(int id) {
//        return users.containsKey(id);
//    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(int id) {
        User removed = users.remove(id);
        if (removed != null) {
            emails.remove(removed.getEmail());
        }
    }
}