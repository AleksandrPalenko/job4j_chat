package ru.job4j.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserDTO {

    @NotBlank(message = "login must be empty")
    @Size(min = 1, max = 15, message
            = "Message must be between 1 and 15 characters")
    private String login;
    @NotBlank(message = "password must be non null")
    @Size(min = 1, max = 200, message
            = "Password must be between 1 and 8 characters")
    private String password;
}
