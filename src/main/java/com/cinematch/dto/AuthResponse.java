package com.cinematch.dto;

import lombok.Data;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class AuthResponse {

    private String message;
    private String token;
    private String type;
    private String name;
    
}