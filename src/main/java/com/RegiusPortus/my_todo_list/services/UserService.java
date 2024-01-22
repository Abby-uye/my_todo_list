package com.RegiusPortus.my_todo_list.services;

import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.dtos.UserLoginRequest;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskCreationRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskRemovalRequest;
import com.RegiusPortus.my_todo_list.exceptions.TaskException;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;

public interface UserService {

    ApiResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserException;
    ApiResponse login(UserLoginRequest userLoginRequest) throws UserException;
    ApiResponse createTask(TaskCreationRequest taskCreationRequest) throws UserException, TaskException;
    ApiResponse removeTask(TaskRemovalRequest taskRemovalRequest) throws UserException;

}
