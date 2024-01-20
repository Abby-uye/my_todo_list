package com.RegiusPortus.my_todo_list.data.repositories;

import com.RegiusPortus.my_todo_list.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);



}
