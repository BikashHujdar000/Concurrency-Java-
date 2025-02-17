package com.example.bikash.SpringTest.Controller;

import com.example.bikash.SpringTest.Exceptions.JwtValidationException;
import com.example.bikash.SpringTest.JWT.JwtService;
import com.example.bikash.SpringTest.Model.LoginRequest;
import com.example.bikash.SpringTest.Model.LoginResponse;
import com.example.bikash.SpringTest.Model.RefreshTokenRequest;
import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private  final UserDetailsService userDetailsService;
    private   final JwtService jwtService;
    private  final UserRepository userRepository;


    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;

    }



    @PostMapping("/signin")
    public ResponseEntity<?> logIn(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =  this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // ✅ Manually set SecurityContextHolder for the first login request
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = this.jwtService.generateToken(loginRequest.getUsername(), true);
        String  refreshToken = this.jwtService.generateToken(loginRequest.getUsername(), false);
        User user = userRepository.findByEmail(loginRequest.getUsername()).get();
        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken, user);
        return  new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws JwtValidationException {

        if(this.jwtService.validateToken(refreshTokenRequest.getRefreshToken()))
        {

            String username =  this.jwtService.getUsernameFromToken(refreshTokenRequest.getRefreshToken());
            String accessToken = this.jwtService.generateToken(refreshTokenRequest.getRefreshToken(), true);
            String refreshToken = this.jwtService.generateToken(refreshTokenRequest.getRefreshToken(), false);
            User user = userRepository.findByEmail(username).get();
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken, user);
            return  new ResponseEntity<>(loginResponse, HttpStatus.OK);

        }
        return  new ResponseEntity<>("Invalid Refresh Token",HttpStatus.BAD_REQUEST);
    }

}
