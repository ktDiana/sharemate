package com.practice.shareitdiana.user;

import com.practice.shareitdiana.exception.ConflictException;
import com.practice.shareitdiana.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> {
                    log.error("Пользователь с данным id ({}) не найден", id);
                    return new UserNotFoundException("Пользователь с данным id (" + id + ") не найден");
                });
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("Пользователь с данным email ({}) не найден", email);
                    return new UserNotFoundException("Пользователь с данным email (" + email + ") не найден");
                });
    }

    @Override
    public User createUser(User user) {
        // проверяем на дубликат почты
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("Данный email уже используется: " + user.getEmail());
        }
        log.info("Создан новый пользователь: {}", user.getName());
        return userRepository.createUser(user);
    }

    @Override
    public User updateUser(User updatedUser) {
        // ищем существующего юзера по айди
        if (!userRepository.existsById(updatedUser.getId())) {
            log.error("Пользователь с id ({}) не найден", updatedUser.getId());
            throw new UserNotFoundException("Пользователь с id (" + updatedUser.getId() + ") не найден");
        }

        // проверяем на дубликат почты
        validateUniqueEmail(updatedUser);

        log.info("Обновлён пользователь: {}", updatedUser.getName());
        return userRepository.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с данным id (" + id + ") не найден"));
        log.info("Удалён пользователь: {}", user.getName());
        userRepository.deleteUserById(id);
    }

    private void validateUniqueEmail(User updatedUser) {
        Optional<User> existingUserWithEmail = userRepository.findUserByEmail(updatedUser.getEmail());
        if (existingUserWithEmail.isPresent() && existingUserWithEmail.get().getId() != updatedUser.getId()) {
            log.error("Пользователь с данным email ({}) уже существует", updatedUser.getEmail());
            throw new ConflictException("Данный email уже используется: " + updatedUser.getEmail());
        }
    }
}