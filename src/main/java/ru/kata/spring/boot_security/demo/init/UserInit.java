package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


@Component
public class UserInit {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostConstruct
//    private void initUsers() {
//
//        Role adminRole = roleRepository.getRoleByName("ROLE_ADMIN").orElseThrow();
//        Role userRole = roleRepository.getRoleByName("ROLE_USER").orElseThrow();
//
//        User adminUser = new User();
//        adminUser.setFirstName("Ivan");
//        adminUser.setLastName("Ivanov");
//        adminUser.setUsername("ivanivanov");
//        adminUser.setPassword(passwordEncoder.encode("1234"));
//
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminRoles.add(userRole);
//
//        adminUser.setRoles(adminRoles);
//
//        userRepository.save(adminUser);
//
//        User regularUser = new User();
//        regularUser.setFirstName("Petr");
//        regularUser.setLastName("Petrov");
//        regularUser.setUsername("petrpetrov");
//        regularUser.setPassword(passwordEncoder.encode("1234"));
//
//        Set<Role> regularUserRoles = new HashSet<>();
//        regularUserRoles.add(userRole);
//
//        regularUser.setRoles(regularUserRoles);
//
//        userRepository.save(regularUser);
//
//
//    }
}
