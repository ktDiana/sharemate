package com.practice.shareitdiana.item.dto;

import com.practice.shareitdiana.item.ItemStatus;
import com.practice.shareitdiana.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ItemCreateDto {

    @NotBlank(message = "Поле с названием вещи не может быть пустым")
    String name;

    @NotBlank(message = "Поле с описанием вещи не может быть пустым")
    String description;

    // Не включаем owner в Dto, чтобы не возвращать полные данные о владельце (в таске readme был намёк..........)
    // int ownerId;

    @NotNull(message = "Поле 'available' обязательно")
    Boolean available;
}