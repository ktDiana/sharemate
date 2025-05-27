package com.practice.shareitdiana.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

// Просто класс-сущность для таблиц, репозитория (хранилища) и сервиса

public class User {

    int id;

    // @NotBlank(message = "Имя пользователя не может быть пустым")
    String name;

    // @NotBlank(message = "Почта пользователя не может быть пустой")
    @Email(message = "Некорректный формат email")
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

}