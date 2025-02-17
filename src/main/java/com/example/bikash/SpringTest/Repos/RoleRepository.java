package com.example.bikash.SpringTest.Repos;

import com.example.bikash.SpringTest.Model.Role;
import com.example.bikash.SpringTest.Model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(RoleType name);

    Optional<Role> findByName(RoleType name);
}

