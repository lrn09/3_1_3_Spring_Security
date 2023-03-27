package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public StringToRoleConverter(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @Override
    public Role convert(String source) {
        return roleServiceImpl.getRoleById(Integer.parseInt(source));
    }
}