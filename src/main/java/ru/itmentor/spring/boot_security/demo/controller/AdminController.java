package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserDto;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/new")
    public UserDto createUserForm() {
        return new UserDto();
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        userService.create(user);
        return ResponseEntity.ok("User created successfully!");
    }

    @GetMapping("edit/{id}")
    public ResponseEntity<UserDto> editUserForm(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        user.setId(id);
        userService.create(user);
        return "User updated successfully!";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody Map<String, Long> payload) {
        Long id = payload.get("id");
        userService.delete(id);
        return "User deleted successfully!";
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        List<Role> roles = userDto.getRoles().stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toList());
        user.setRoles(new HashSet<>(roles));
        return user;
    }
}
