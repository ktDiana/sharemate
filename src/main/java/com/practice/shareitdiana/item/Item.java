package com.practice.shareitdiana.item;

import com.practice.shareitdiana.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
//@Entity
//@Table(name = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

//    @ManyToOne
//    @JoinColumn(name = "owner_id")
    User owner;

    @Size(max = 150)
    String name;

    @Size(max = 200)
    String description;

    @Enumerated(EnumType.STRING)
    ItemStatus status;

    public Item(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        // если доступ ТРУ, то ДОСТУПЕН
        // если ФОЛЗ, то НЕДОСТУПЕН
        this.status = available != null && available ? ItemStatus.AVAILABLE : ItemStatus.UNAVAILABLE;
    }
}