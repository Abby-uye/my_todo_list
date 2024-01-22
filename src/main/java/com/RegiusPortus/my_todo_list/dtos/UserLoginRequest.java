package com.RegiusPortus.my_todo_list.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserLoginRequest {
    @NotBlank(message = " Sorry but you have to input your email to login")
    String email;
    @NotBlank(message = " Sorry but you have to input  your password to login")
    String password;
}
