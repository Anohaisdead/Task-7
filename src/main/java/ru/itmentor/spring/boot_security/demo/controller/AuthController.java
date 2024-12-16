package ru.itmentor.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserDto;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RegisterService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;

    @Autowired
    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid UserDto userDto) {
        User user = convertToEntity(userDto);
        registerService.register(user);
        return ResponseEntity.ok("User '" + user.getUsername() + "' registered successfully!");
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
