package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RoleService {
    public Set<Role> showAvailableRoles();

    public Role getRoleById(int id);

    public Role getRoleByName(String name);
}
