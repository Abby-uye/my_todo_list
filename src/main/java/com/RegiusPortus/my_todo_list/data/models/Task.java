package com.RegiusPortus.my_todo_list.data.models;

import com.RegiusPortus.my_todo_list.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime dueDate;
    private Status status;
//    @ManyToOne
//    private User user;

}
