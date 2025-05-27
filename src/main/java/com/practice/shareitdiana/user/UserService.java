package com.practice.shareitdiana.user;


import com.practice.shareitdiana.item.Item;
import com.practice.shareitdiana.user.dto.UserCreateDto;
import com.practice.shareitdiana.user.dto.UserResponseDto;
import com.practice.shareitdiana.user.dto.UserUpdateDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// Просто интерфейс с бизнес-логикой

public interface UserService {

    Collection<User> findAllUsers();

    User findUserById(int id);

    // User findUserByEmail(String email);

    User createUser(String name, String email);

    User updateUser(int id, UserUpdateDto updatedDto);

    void deleteUser(int id);
}