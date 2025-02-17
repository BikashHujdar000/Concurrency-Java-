package com.example.bikash.SpringTest.Exceptions;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtFailureResponse {

    private  int status;
    private  String message;
    private  String error;
    private  String path ;

}
