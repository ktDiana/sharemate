package com.practice.shareitdiana.comment;

import com.practice.shareitdiana.item.Item;
import com.practice.shareitdiana.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Comment {

    int id;                 // уникальный идентификатор комментария;
    String text;               // содержимое комментария;
    Item item;               // вещь, к которой относится комментарий;
    User author;             // автор комментария;
    LocalDateTime created;            // дата создания комментария.

}
