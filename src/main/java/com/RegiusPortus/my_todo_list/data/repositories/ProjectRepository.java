package com.RegiusPortus.my_todo_list.data.repositories;

import com.RegiusPortus.my_todo_list.data.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
