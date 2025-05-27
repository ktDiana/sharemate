package com.practice.shareitdiana.user;

import com.practice.shareitdiana.item.Item;
import com.practice.shareitdiana.user.dto.UserCreateDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserById(int id);

    Optional<User> findUserByEmail(String email);

    Collection<User> findAllUsers();

    // boolean existsById(int id);

    User save(User user);

    User updateUser(User user);

    void deleteUserById(int id);
}