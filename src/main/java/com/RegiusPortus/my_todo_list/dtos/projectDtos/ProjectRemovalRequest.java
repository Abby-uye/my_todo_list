package com.RegiusPortus.my_todo_list.dtos.projectDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectRemovalRequest {
    @NotBlank(message = "This field must not be blank")
    String name;
    @NotBlank(message = "This field must not be blank")
    String userEmail;
}
