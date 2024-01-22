package com.RegiusPortus.my_todo_list.data.repositories;

import com.RegiusPortus.my_todo_list.data.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Task findByName(String taskName);
}
