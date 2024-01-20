package com.RegiusPortus.my_todo_list.services;

import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;

public interface UserService {

    ApiResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserException;
}
