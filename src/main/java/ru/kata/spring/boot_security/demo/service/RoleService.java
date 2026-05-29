package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {

    Role addRole(Role role);

    Role getRole(String roleName);

}
