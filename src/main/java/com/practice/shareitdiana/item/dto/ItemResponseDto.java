package com.practice.shareitdiana.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class ItemResponseDto {

    int id;
    String name;
    String description;
    Boolean available;

}
