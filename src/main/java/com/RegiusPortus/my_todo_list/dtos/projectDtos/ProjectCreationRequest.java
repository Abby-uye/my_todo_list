package com.RegiusPortus.my_todo_list.dtos.projectDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProjectCreationRequest {
    @NotBlank(message = "This field must not be blank")
    private String name;
    @NotNull(message = "This field must not be blank")
    private LocalDate startDate;
    @NotNull(message = "This field must not be blank")
    private LocalDate dueDate;
    private String description;
    @NotBlank(message = "This field must not be blank")
    private String userEmail;
}
