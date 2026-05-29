package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dto.UserCreateDto;
import ru.kata.spring.boot_security.demo.dto.UserUpdateDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPage() {
        return "adminpanel";
    }

    @GetMapping("/newuser")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "newuser";
    }

    @GetMapping("/users")
    public String showUsersTable(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String showUserEditPage(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getUser(id));
        return "edituser";
    }

    @PostMapping("/users")
    public String addNewUser(@ModelAttribute("user") UserCreateDto userCreateDto) {
        userService.addUser(userCreateDto);
        return "redirect:/admin/users";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user") UserUpdateDto userUpdateDto, @PathVariable Long id) {
        userUpdateDto.setId(id);
        userService.updateUser(userUpdateDto);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }


}
