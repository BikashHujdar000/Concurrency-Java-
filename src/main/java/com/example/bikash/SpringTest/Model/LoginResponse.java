package com.example.bikash.SpringTest.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private  String  accessToken;
    private  String refreshToken;
    private  User user ;

}
