package com.practice.shareitdiana.user.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserUpdateDto {
    // @Size(max = 255, message = "Имя должно содержать от 1 до 255 символов")
    // @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String name;

    // @Size(max = 255)
    @Email(message = "Формат email должен соответствовать \"user@mail.com\"", regexp = ".*")
    // @NotBlank(message = "Почта пользователя должна быть указана")
    private String email;
}