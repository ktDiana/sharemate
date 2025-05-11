package com.practice.shareitdiana.item.dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ItemUpdateDto {
    // Убрали аннотацию @NotBlank для patch, потому что что-то может передаваться null

    String name;

    String description;

    Boolean available;

}
