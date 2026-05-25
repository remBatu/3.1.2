package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Component
public class FirstAdminInitializer implements CommandLineRunner {

    private final String name;
    private final String password;
    private final UserService userService;

    public FirstAdminInitializer(@Value("${app.defaultadmin.username}") String name,
                                 @Value("${app.defaultadmin.password}") String password,
                                 UserService userService) {
        this.name = name;
        this.password = password;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User firstAdmin = new User(name, 30, password, Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        userService.addUser(firstAdmin);
        System.out.println("Администратор по умолчанию создан");
    }
}
