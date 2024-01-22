package com.RegiusPortus.my_todo_list.services;

import com.RegiusPortus.my_todo_list.data.models.Task;
import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.data.repositories.TaskRepository;
import com.RegiusPortus.my_todo_list.data.repositories.UserRepository;
import com.RegiusPortus.my_todo_list.dtos.UserLoginRequest;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskCreationRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskRemovalRequest;
import com.RegiusPortus.my_todo_list.exceptions.TaskException;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import com.RegiusPortus.my_todo_list.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ApiResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserException {
        User foundUser = userRepository.findByEmail(userRegistrationRequest.getEmail());
        if (foundUser != null) throw new UserException(GenerateApiResponse.USER_ALREADY_EXIST);
        if (!(validEmail(userRegistrationRequest.getEmail()))) throw new UserException(GenerateApiResponse.INVALID_EMAIL);
        if (!(validPassword(userRegistrationRequest.getPassword())))throw new UserException(GenerateApiResponse.WEAK_PASSWORD);
        if (!(isValidConfirmPassword(userRegistrationRequest.getPassword(),userRegistrationRequest.getConfirmPassword()))) throw new UserException(GenerateApiResponse.INCORRECT_CONFIRM_PASSWORD);
        User newUser = modelMapper.map(userRegistrationRequest, User.class);
        userRepository.save(newUser);
        return GenerateApiResponse.created(GenerateApiResponse.USER_CREATED_SUCCESSFULLY).getBody();
    }


    public ApiResponse login(UserLoginRequest userLoginRequest) throws UserException {
        User foundUser = userRepository.findByEmail(userLoginRequest.getEmail());
        if (foundUser == null) throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        if (!(foundUser.getPassword().equals(userLoginRequest.getPassword()))) throw new UserException(GenerateApiResponse.INCORRECT_PASSWORD);
        foundUser.setLoggedIn(true);
        userRepository.save(foundUser);
        return GenerateApiResponse.ok(GenerateApiResponse.SUCCESSFULLY_LOGGEDIN).getBody();

    }

    public ApiResponse createTask(TaskCreationRequest taskCreationRequest) throws UserException, TaskException {
        User foundUser  = userRepository.findByEmail(taskCreationRequest.getUserEmail());
        if (foundUser == null) throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        Set<Task> foundUserListOfTask = foundUser.getTaskList();
        if (existingTask(taskCreationRequest.getName(),taskCreationRequest.getUserEmail())) throw new TaskException(GenerateApiResponse.TAX_ALREADY_EXIST);
        Task task = modelMapper.map(taskCreationRequest,Task.class);
        foundUserListOfTask.add(task);
        taskRepository.save(task);
        userRepository.save(foundUser);
        return GenerateApiResponse.created(GenerateApiResponse.TASK_CREATED_SUCCESSFULLY).getBody();
    }


    public ApiResponse removeTask(TaskRemovalRequest taskRemovalRequest) throws UserException {
        User foundUser = userRepository.findByEmail(taskRemovalRequest.getUserEmail());
        if (foundUser == null) throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        Task foundTask = foundUser.getTaskList().equals(taskRepository.findByName(taskRemovalRequest.getTaskName()));
        return null;
    }

    private  boolean isValidConfirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    private boolean validPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    private boolean validEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$");
    }
    private boolean existingTask (String taskName, String userEmail){
        Set<Task> userTaskList= userRepository.findByEmail(userEmail).getTaskList();
        for (Task task: userTaskList) {
            if (task.getName().equals(taskName)) return true;
        }
    return false;
    }
private Task isFoundTask(String taskName, String userEmail){
        User foundUser = userRepository.findByEmail(userEmail);
        Set<>
}

}
