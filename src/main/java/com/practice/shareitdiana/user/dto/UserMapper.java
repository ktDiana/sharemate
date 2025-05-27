package com.practice.shareitdiana.user.dto;

import com.practice.shareitdiana.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

// UserCreateDto -> User -> UserResponseDto

public class UserMapper {

    public User fromCreate(UserCreateDto createdDto) {
        User user = new User();
//        user.setName(createdDto.getName());
//        user.setEmail(createdDto.getEmail());
//        return user;
        return new User(0, createdDto.getName(), createdDto.getEmail());
    }

    public void fromUpdate(User existingUser, UserUpdateDto updatedUser) {
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
    }

    public UserResponseDto toResponse(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }
}