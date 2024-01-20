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
public class UserRegistrationRequest {
    @NotBlank(message = "This field must not be blank")
    String name;
    @NotBlank(message = "This field must not be blank")
    String email;
    @NotBlank(message = "This field must not be blank")
    String password;
}
