package com.practice.shareitdiana.item.dto;

import com.practice.shareitdiana.item.ItemStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class ItemResponseDto {

    int id;
    int ownerId;
    String name;
    String description;
    Boolean available;
}
