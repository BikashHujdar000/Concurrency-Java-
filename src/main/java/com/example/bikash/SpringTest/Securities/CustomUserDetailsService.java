package com.example.bikash.SpringTest.Securities;

import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private  final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user =  userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        UserDetails customUserDetail = new CustomUserDetail(user);
        return customUserDetail;
    }
}
