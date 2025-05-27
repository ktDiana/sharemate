package com.practice.shareitdiana.user;

import com.practice.shareitdiana.user.dto.UserCreateDto;
import com.practice.shareitdiana.user.dto.UserMapper;
import com.practice.shareitdiana.user.dto.UserResponseDto;
import com.practice.shareitdiana.user.dto.UserUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

// Все Dto проходят через контроллер
// сначала я не использовала ResponseEntity, но подумала, что из-за headers это нужно + это помощь с кодом ответов,
// так что использую ... в надежде ... что не сделала ...... хуже ............

@RestController
@RequestMapping("/users")
@Validated

public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // GET - всех юзеров
    @GetMapping
    public ResponseEntity<Collection<UserResponseDto>> findAllUsers() {
        return ResponseEntity.ok(
                userService.findAllUsers().stream()
                        .map(userMapper::toResponse)
                        .toList());
    }

    // GET - юзера по айди
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable int id) {
        return ResponseEntity.ok(
                userMapper.toResponse(userService.findUserById(id)));
    }

    // POST - нового юзера
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto createdUser) {
        // закидываем данные о новом юзере сразу в сервис
        User user = userService.createUser(createdUser.getName(), createdUser.getEmail());
        // возвращаем созданносохраненного юзера-дто в ответ
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toResponse(user));
    }

    // PATCH - обновить юзера
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UserUpdateDto updatedUser) {
        // обновляем данные о юзере сразу в сервисе
        User finallyUser = userService.updateUser(id, updatedUser);
        // в маппере производим замену/обновление нужных полей, возвращаем созданносохраненного юзера-дто в ответ
        return ResponseEntity.ok()
                .body(userMapper.toResponse(finallyUser));
    }

    // DELETE - юзера по айди
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

// АЛИШЕР СКАЗАЛ !!!
// @PostMapping
// public UserResponseDto createUser(@Valid @RequestBody UserCreateDto createdUser) {
//    User user = userMapper.fromCreate(createdUser);
//    return userMapper.toResponse(userService.createUser(user));
// }