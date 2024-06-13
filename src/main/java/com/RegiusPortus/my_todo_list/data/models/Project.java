package com.RegiusPortus.my_todo_list.data.models;

import com.RegiusPortus.my_todo_list.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime dueDate;
    private Status status;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Task> taskList;
//    @ManyToOne
//    private User user;

}
