package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Set<Role> showAvailableRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Transactional
    @Override
    public Role getRoleById(int id) {
        return roleRepository.getById(id);
    }

    @Transactional
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name).orElseThrow();
    }

    @Transactional
    @Override
    public Map<Integer, String> getAvailableRolesAsMap() {
        List<Object[]> resultList = roleRepository.findIdAndAuthority();
        return resultList.stream()
                .collect(Collectors.toMap(
                        e -> (Integer) e[0],
                        e -> (String) e[1]));
    }
}
