package com.example.bikash.SpringTest.Controller;


import com.example.bikash.SpringTest.DTOS.RoleDTO;
import com.example.bikash.SpringTest.Model.Role;
import com.example.bikash.SpringTest.Service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO.getName());
    }
}
