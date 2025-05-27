package com.practice.shareitdiana.user.dto;
import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// = @RequestBody для создания пользователя
// Может содержать аннотации валидации (например, @NotNull, @Email) для проверки входных данных.

public class UserCreateDto {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    @NotBlank(message = "Почта пользователя не может быть пустой")
    @Email(message = "Почта должна быть в формате \"user@mail.com\"")
    private String email;

}