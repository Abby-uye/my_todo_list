package com.RegiusPortus.my_todo_list.controllers;

import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.services.UserService;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/my_todo_list/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserRegistrationController {
    private UserService userService;
    @PostMapping("user/register")
    public ResponseEntity<ApiResponse> registerUser (@RequestBody UserRegistrationRequest userRegistrationRequest) throws UserException {
        return new ResponseEntity<>(userService.registerUser(userRegistrationRequest), HttpStatus.CREATED);
    }
}
