package com.practice.shareitdiana.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

// Для исходящих данных
// т.е. в контроллере для ответа клиенту

public class UserResponseDto {
    int id;
    String name;
    String email;

}
