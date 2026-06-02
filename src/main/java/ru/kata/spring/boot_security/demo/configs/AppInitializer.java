package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserCreateDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {

    private final String name;
    private final String password;
    private final UserService userService;
    private final RoleService roleService;
    private final String defaultRoleName;
    private final String adminRoleName;

    public AppInitializer(@Value("${app.defaultadmin.username}") String name,
                          @Value("${app.defaultadmin.password}") String password,
                          @Value("${app.default.role}") String defaultRoleName,
                          @Value("${app.admin.role}") String adminRoleName,
                          UserService userService,
                          RoleService roleService) {
        this.name = name;
        this.password = password;
        this.defaultRoleName = defaultRoleName;
        this.adminRoleName = adminRoleName;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role userRole = roleService.getRole(defaultRoleName);
        Role adminRole = roleService.getRole(adminRoleName);
        if (userRole == null) {
            roleService.addRole(new Role(Role.USER_ID, defaultRoleName));
        }
        if (adminRole == null) {
            roleService.addRole(new Role(Role.ADMIN_ID, adminRoleName));
        }
        UserCreateDto firstAdmin = new UserCreateDto();
        firstAdmin.setName(name);
        firstAdmin.setPassword(password);
        firstAdmin.setAge(30);
        firstAdmin.setRoles(List.of(String.valueOf(Role.ADMIN_ID), String.valueOf(Role.USER_ID)));
        userService.addUser(firstAdmin);
        System.out.println("Администратор по умолчанию создан");
    }
}
