package com.practice.shareitdiana.booking;

import com.practice.shareitdiana.item.Item;
import com.practice.shareitdiana.user.User;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Booking {

    int id;                                         // уникальный идентификатор бронирования;
    LocalDateTime start;                            // дата и время начала бронирования;
    LocalDateTime end;                              // дата и время конца бронирования;
    Item item;                                      // вещь, которую пользователь бронирует;
    User booker;                                    // пользователь, который осуществляет бронирование;

    @Enumerated
    BookingStatus status;                           // статус бронирования

}