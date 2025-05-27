package com.practice.shareitdiana.request;

import com.practice.shareitdiana.user.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ItemRequest {
    private int id;
    private String description;
    private User requester;
    private LocalDateTime created;
}

