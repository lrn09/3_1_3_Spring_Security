package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Map;
import java.util.Set;

public interface RoleService {
    public Set<Role> showAvailableRoles();

    public Role getRoleById(int id);

    public Role getRoleByName(String name);

    Map<Integer, String> getAvailableRolesAsMap();
}
