package com.practice.shareitdiana.user;

import com.practice.shareitdiana.exception.EmailAlreadyExistsException;
import com.practice.shareitdiana.exception.UserNotFoundException;
import com.practice.shareitdiana.user.dto.UserUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

//    @Override
//    public User findUserByEmail(String email) {
//        return userRepository.findUserByEmail(email)
//                .orElseThrow(() -> {
//                    log.error("Пользователь с данным email ({}) не найден", email);
//                    return new UserNotFoundException("Пользователь с данным email (" + email + ") не найден");
//                });
//    }

    @Override
    public User createUser(String name, String email) {
        // проверяем на дубликат почты
        userRepository.findUserByEmail(email).ifPresent(usr -> {
            log.warn("Данный email уже используется: " + email);
            throw new EmailAlreadyExistsException("Данный email уже используется: " + email);
        });
        User user = new User(0, name, email);
        log.info("Создан новый пользователь: {}", name);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, UserUpdateDto updatedUser) {
        User existingUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id (" + id + ") не найден"));
        if (updatedUser.getEmail() != null) {
            userRepository.findUserByEmail(updatedUser.getEmail()).ifPresent(usr -> {
                if (usr.getId() != id) {
                    log.warn("Email уже используется: " + updatedUser.getEmail());
                    throw new EmailAlreadyExistsException("Email уже используется: " + updatedUser.getEmail());
                }
            });
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        log.info("Обновлён пользователь: {}", updatedUser.getName());
        return userRepository.updateUser(existingUser);
    }

    @Override
    public void deleteUser(int id) {
        if (!userRepository.findUserById(id).isPresent()) {
            throw new UserNotFoundException("Пользователь с данным id (" + id + ") не найден");

        }
        log.info("Удалён пользователь: ({})", id);
        userRepository.deleteUserById(id);
    }
}