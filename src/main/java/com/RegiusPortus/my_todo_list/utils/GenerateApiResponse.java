package com.RegiusPortus.my_todo_list.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class GenerateApiResponse {

    public static final String USER_ALREADY_EXIST = "A user with this info already exist try logging in instead";
    public static final String INVALID_EMAIL = "Enter a valid email";
    public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
    public static final String WEAK_PASSWORD = "password must contain Upper case letters, lowercase letters, digits and special characters";

    public static  ResponseEntity<ApiResponse> created(Object data){
        return new ResponseEntity<>(
                ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .build(),HttpStatus.CREATED);


    }

    public static  ResponseEntity<ApiResponse> validationError(Object data){
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .data(data)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .isSuccessful(false).statusCode(HttpStatus.BAD_REQUEST.value()).build(),HttpStatus.BAD_REQUEST
        );

    }
}
