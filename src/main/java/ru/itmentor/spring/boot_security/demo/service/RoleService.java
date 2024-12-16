package ru.itmentor.spring.boot_security.demo.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
@NoArgsConstructor(force = true)
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

}
