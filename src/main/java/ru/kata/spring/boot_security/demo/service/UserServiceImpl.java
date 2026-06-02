package ru.kata.spring.boot_security.demo.service;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.dto.UserCreateDto;
import ru.kata.spring.boot_security.demo.dto.UserUpdateDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final String defaultRoleName;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           @Value("${app.default.role}") String defaultRoleName) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.defaultRoleName = defaultRoleName;
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким именем не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с этим id не найден"));
    }

    @Override
    @Transactional
    public void addUser(UserCreateDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getReferenceById(Role.USER_ID));
        if (dto.getRoles() != null) {
            dto.getRoles().forEach(id -> roles.add(roleRepository.getReferenceById(Long.parseLong(id))));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void updateUser(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с этим id не найден"));
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getReferenceById(Role.USER_ID));
        if (dto.getRoles() != null) {
            dto.getRoles().forEach(id -> roles.add(roleRepository.getReferenceById(Long.parseLong(id))));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
