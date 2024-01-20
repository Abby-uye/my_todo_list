package com.RegiusPortus.my_todo_list.services;

import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.data.repositories.UserRepository;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import com.RegiusPortus.my_todo_list.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private UserRepository userRepository;

    public ApiResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserException {
        User foundUser = userRepository.findByEmail(userRegistrationRequest.getEmail());
        if (foundUser != null) throw new UserException(GenerateApiResponse.USER_ALREADY_EXIST);
        if (!(validEmail(userRegistrationRequest.getEmail()))) throw new UserException(GenerateApiResponse.INVALID_EMAIL);
        User newUser = modelMapper.map(userRegistrationRequest, User.class);
        userRepository.save(newUser);
        return GenerateApiResponse.created(GenerateApiResponse.USER_CREATED_SUCCESSFULLY).getBody();
    }

    private boolean validEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$");
    }
}
