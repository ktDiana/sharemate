package com.practice.shareitdiana.user.dto;

import com.practice.shareitdiana.user.User;
import org.springframework.stereotype.Component;

@Component

// UserCreateDto -> User -> UserResponseDto

public class UserMapper {

    public User fromCreate(UserCreateDto userCreate) {
        User user = new User();
        user.setName(userCreate.getName());
        user.setEmail(userCreate.getEmail());
        return user;
    }

    public User fromUpdate(User existingUser, UserUpdateDto updatedUser) {
        if (updatedUser.getName() != null && !updatedUser.getName().trim().isEmpty()) {
            existingUser.setName(updatedUser.getName().trim());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().trim().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        return existingUser;
    }

    public UserResponseDto toResponse(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }
}
