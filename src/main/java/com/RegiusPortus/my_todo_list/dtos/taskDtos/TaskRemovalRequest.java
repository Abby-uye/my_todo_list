package com.RegiusPortus.my_todo_list.dtos.taskDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaskRemovalRequest {
    @NotBlank(message = "This field must not be null")
    String taskName;
    String userEmail;
}
