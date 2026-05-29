package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }


    @Override
    @Transactional(readOnly = true)
    public Role getRole(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
