package com.example.bikash.SpringTest.Service;

import com.example.bikash.SpringTest.Model.Role;
import com.example.bikash.SpringTest.Model.RoleType;
import com.example.bikash.SpringTest.Repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String roleName) {
        RoleType roleType = RoleType.valueOf(roleName);
        if (roleRepository.existsByName(roleType)) {
            throw new IllegalArgumentException("Role " + roleName + " already exists.");
        }
        Role newRole = new Role();
        newRole.setName(roleType);
        return roleRepository.save(newRole);
    }
}
