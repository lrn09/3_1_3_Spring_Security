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
//        Role adminRole = new Role("ROLE_USER");
//        roleRepository.save(adminRole);
//        Role userRole = new Role("ROLE_ADMIN");
//        roleRepository.save(userRole);
//
//        User adminUser = new User();
//        adminUser.setFirstName("Ivan");
//        adminUser.setLastName("Ivanov");
//        adminUser.setAge(24);
//        adminUser.setUsername("ivan@mail.ru");
//        adminUser.setPassword(passwordEncoder.encode("1234"));
//
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(roleRepository.getRoleByName("ROLE_ADMIN").get());
//        adminRoles.add(roleRepository.getRoleByName("ROLE_USER").get());
//
//        adminUser.setRoles(adminRoles);
//        userRepository.save(adminUser);
//
//        User regularUser = new User();
//        regularUser.setFirstName("Petr");
//        regularUser.setLastName("Petrov");
//        regularUser.setAge(31);
//        regularUser.setUsername("petr@gmail.com");
//        regularUser.setPassword(passwordEncoder.encode("1234"));
//
//        Set<Role> regularUserRoles = new HashSet<>();
//        regularUserRoles.add(roleRepository.getRoleByName("ROLE_USER").get());
//
//        regularUser.setRoles(regularUserRoles);
//        userRepository.save(regularUser);
//    }
}
