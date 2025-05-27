package com.practice.shareitdiana.booking.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class BookingResponseDto {

    int id;
    LocalDateTime start;
    LocalDateTime end;
    int itemId;
    String itemName;
    int bookerId;
    boolean approved;
}