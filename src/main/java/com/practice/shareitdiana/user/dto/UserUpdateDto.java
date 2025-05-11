package com.practice.shareitdiana.user.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserUpdateDto {

    // @NotBlank
    private String name;

    // @NotBlank
    @Email(message = "Почта должна содержать символ @")
    private String email;

}