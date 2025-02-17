package com.example.bikash.SpringTest.JWT;

import com.example.bikash.SpringTest.Exceptions.JwtFailureResponse;
import com.example.bikash.SpringTest.Exceptions.JwtValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Exception exception = (Exception) request.getAttribute("jwtValidationException");

        String message = (exception==null)?authException.getMessage():exception.getMessage();

        JwtFailureResponse  jwtFailureResponse = new JwtFailureResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                message,
                message,
                request.getRequestURI()
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtFailureResponse));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    }
}

