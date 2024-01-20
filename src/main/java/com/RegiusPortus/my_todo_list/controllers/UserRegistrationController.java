package com.RegiusPortus.my_todo_list.controllers;

import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.services.UserService;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import com.RegiusPortus.my_todo_list.utils.GenerateApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/my_todo_list/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserRegistrationController {
    private UserService userService;
    @PostMapping("user/register")
    public ResponseEntity<ApiResponse> registerUser (@RequestBody @Valid UserRegistrationRequest userRegistrationRequest, BindingResult result) throws UserException {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append("Field '")
                        .append(error.getField())
                        .append("' ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            return GenerateApiResponse.validationError(errorMessage.toString());
        }
        return new ResponseEntity<>(userService.registerUser(userRegistrationRequest), HttpStatus.CREATED);
    }
}
