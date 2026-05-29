package ru.kata.spring.boot_security.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserUpdateDto {
    private Long id;
    private String name;
    private Integer age;
    private List<String> roles;
}
