package com.practice.shareitdiana.user;


import com.practice.shareitdiana.item.Item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// Просто интерфейс с бизнес-логикой

public interface UserService {

    Collection<User> findAllUsers();

    User findUserById(int id);

    User findUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

}