package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r left join fetch r.users where r.authority = :authority")
    Optional<Role> getRoleByName(String authority);

    @Query("select r.id, r.authority from Role r")
    List<Object[]> findIdAndAuthority();

}
