package com.RegiusPortus.my_todo_list.dtos.projectDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddTaskToProjectRequest {
    @NotBlank(message = "This field must not be blank")
    String projectName;
    @NotBlank(message = "This field must not be blank")
    String taskName;
    @NotBlank(message = "This field must not be blank")
    String userEmail;
}
