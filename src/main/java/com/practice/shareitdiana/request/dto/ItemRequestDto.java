package com.practice.shareitdiana.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ItemRequestDto {

    @NotBlank(message = "Описание запроса не может быть пустым")
    String description;
}