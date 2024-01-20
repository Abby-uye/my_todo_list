package com.RegiusPortus.my_todo_list.exceptions;

import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> userException(UserException userException) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .data(userException.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .isSuccessful(false)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}