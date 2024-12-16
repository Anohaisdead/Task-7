package ru.itmentor.spring.boot_security.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String password;
    private List<String> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles=user.getRoles().stream()
                .map(Role::getName)
                .toList();
    }
}
