package com.RegiusPortus.my_todo_list.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private  Long id;
   private  String name;
   private String email;
   private String password;
   private transient boolean isLoggedIn;
   @OneToMany(fetch = FetchType.EAGER)
   private Set<Task> taskList;
   @OneToMany(fetch = FetchType.EAGER)
   private Set<Project> projectList;
}
