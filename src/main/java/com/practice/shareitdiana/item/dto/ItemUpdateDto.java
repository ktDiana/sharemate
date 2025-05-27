package com.practice.shareitdiana.item.dto;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class ItemUpdateDto {
    // Убрали аннотацию @NotBlank для patch, потому что что-то может передаваться null

    // @Size(max = 150)
    String name;

    //@Size(max = 200)
    String description;

    Boolean available;

}