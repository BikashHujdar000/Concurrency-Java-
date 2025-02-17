package com.example.bikash.SpringTest.Service;
import com.example.bikash.SpringTest.Model.Address;
import com.example.bikash.SpringTest.Model.Role;
import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.AddressRepository;
import com.example.bikash.SpringTest.Repos.RoleRepository;
import com.example.bikash.SpringTest.Repos.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleService roleService;

    @Transactional
    public User saveUser(User userDto) {

        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new ValidationException(" User with Email : " + userDto.getEmail() + " already exists");
        }
        Set<Role> roles = new HashSet<>();
        userDto.getRoles().forEach(role -> {roleRepository.findByName(role.getName()).ifPresent(roles::add);});
        userDto.setRoles(roles);
        userDto.getRoles().size();
        Address savedAddress = addressRepository.save(userDto.getAddress());
        userDto.setAddress(savedAddress);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userDto);
    }


    public User findByUsernameQuery(String username)
    {
        User user =  this.userRepository.findByUsernameByQuery(username);
        return  user;

    }
}
