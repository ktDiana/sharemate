package com.practice.shareitdiana.item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
//@Entity
//@Table(name = "items")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor

public class Item {

//    @Id
//    @GeneratedValue(strategy = IDENTITY)
    int id;

    int ownerId;

    @Size(max = 150)
    String name;

    @Size(max = 200)
    String description;

    @Enumerated(EnumType.STRING)
    ItemStatus status;
}