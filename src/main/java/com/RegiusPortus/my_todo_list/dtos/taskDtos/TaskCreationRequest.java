package com.RegiusPortus.my_todo_list.dtos.taskDtos;

import com.RegiusPortus.my_todo_list.Status;
import com.RegiusPortus.my_todo_list.data.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaskCreationRequest {
    @NotBlank(message = "the name field must not be left blank")
    private String name;
    private LocalDateTime dueDate;
    @NotBlank(message = "This field must not be blank")
    private String userEmail;

}
