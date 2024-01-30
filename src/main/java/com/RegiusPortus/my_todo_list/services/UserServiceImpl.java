package com.RegiusPortus.my_todo_list.services;

import com.RegiusPortus.my_todo_list.data.models.Project;
import com.RegiusPortus.my_todo_list.data.models.Task;
import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.data.repositories.ProjectRepository;
import com.RegiusPortus.my_todo_list.data.repositories.TaskRepository;
import com.RegiusPortus.my_todo_list.data.repositories.UserRepository;
import com.RegiusPortus.my_todo_list.dtos.UserLoginRequest;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.AddTaskToProjectRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.ProjectCreationRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.ProjectRemovalRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskCreationRequest;
import com.RegiusPortus.my_todo_list.dtos.taskDtos.TaskRemovalRequest;
import com.RegiusPortus.my_todo_list.exceptions.ProjectException;
import com.RegiusPortus.my_todo_list.exceptions.TaskException;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.utils.ApiResponse;
import com.RegiusPortus.my_todo_list.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

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


    public ApiResponse removeTask(TaskRemovalRequest taskRemovalRequest) throws UserException, TaskException {
        User foundUser = userRepository.findByEmail(taskRemovalRequest.getUserEmail());
        if (foundUser == null) throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        Task foundTask = findTask(taskRemovalRequest.getTaskName(),taskRemovalRequest.getUserEmail());
        if (foundTask == null)throw new TaskException(GenerateApiResponse.TASK_DOES_NOT_EXIST);
        removeTask(foundTask, foundUser.getEmail());
        userRepository.save(foundUser);
        return GenerateApiResponse.ok(GenerateApiResponse.SUCCESSFULLY_REMOVED_TASK).getBody();
    }


    public ApiResponse createProject(ProjectCreationRequest projectCreationRequest) throws UserException, ProjectException {
        User foundUser = userRepository.findByEmail(projectCreationRequest.getUserEmail());
        if(foundUser==null) throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        if (existingProject(projectCreationRequest.getUserEmail(),projectCreationRequest.getName())) throw new ProjectException(GenerateApiResponse.PROJECT_ALREADY_EXIST);
        Project newProject = modelMapper.map(projectCreationRequest,Project.class);
        foundUser.getProjectList().add(newProject);
        projectRepository.save(newProject);
        userRepository.save(foundUser);
        return GenerateApiResponse.created(GenerateApiResponse.PROJECT_CREATED_SUCCESSFULLY).getBody();
    }

    @Override
    public ApiResponse removeProject(ProjectRemovalRequest projectRemovalRequest) throws UserException, ProjectException {
        User foundUser = userRepository.findByEmail(projectRemovalRequest.getUserEmail());
        if (foundUser==null) throw  new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
        Project foundProject = findProject(projectRemovalRequest.getUserEmail() ,projectRemovalRequest.getName());
        if(foundProject == null) throw new ProjectException(GenerateApiResponse.PROJECT_DOES_NOT_EXIST);
        removeProject(foundProject, projectRemovalRequest.getUserEmail());
        userRepository.save(foundUser);
        return GenerateApiResponse.ok(GenerateApiResponse.PROJECT_REMOVED_SUCCESSFULLY).getBody();
    }

    @Override
    public ApiResponse addTaskToProject(AddTaskToProjectRequest addTaskToProjectRequest) throws UserException, ProjectException, TaskException {
    User foundUser = userRepository.findByEmail(addTaskToProjectRequest.getUserEmail());
    if (foundUser == null)throw new UserException(GenerateApiResponse.THIS_USER_DOES_NOT_EXIST);
    Project foundProject = findProject(addTaskToProjectRequest.getUserEmail(), addTaskToProjectRequest.getProjectName());
    if (!(existingProject(addTaskToProjectRequest.getUserEmail(), addTaskToProjectRequest.getProjectName()))) throw new ProjectException(GenerateApiResponse.PROJECT_DOES_NOT_EXIST);
    Task fondTask = findTask(addTaskToProjectRequest.getTaskName(), addTaskToProjectRequest.getUserEmail());
    if (!(existingTask(addTaskToProjectRequest.getTaskName(), addTaskToProjectRequest.getUserEmail()))) throw new TaskException(GenerateApiResponse.TASK_DOES_NOT_EXIST);
    foundProject.getTaskList().add(fondTask);
    return GenerateApiResponse.ok(GenerateApiResponse.TASK_ADDED_TO_PROJECT_SUCCESSFULLY).getBody();
    }

    private boolean existingProject(String userEmail, String projectName) {
        User foundUser = userRepository.findByEmail(userEmail);
        Set<Project> projectList = foundUser.getProjectList();
        for (Project project: projectList) {
            if (project.getName().equals(projectName)){
                return true;
            }

        }
        return false;
    }
    private void removeProject(Project project,String userEmail){
        User foundUser = userRepository.findByEmail(userEmail);
        foundUser.getProjectList().remove(project);
                projectRepository.delete(project);
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
        User foundUser = userRepository.findByEmail(userEmail);
        Set<Task> userTaskList= foundUser.getTaskList();
        for (Task task: userTaskList) {
            if (task.getName().equals(taskName)) return true;
        }
    return false;
    }
private Task findTask(String taskName, String userEmail){
        User foundUser = userRepository.findByEmail(userEmail);
        Set<Task> userTaskList = foundUser.getTaskList();
        Iterator<Task> iterator = userTaskList.iterator();
        while (iterator.hasNext()){
            Task foundtask = iterator.next();
            if (foundtask.getName().equals(taskName)){
                return foundtask;
            }
        }
      return null;
}
    private void removeTask(Task task, String userEmail){
        User foundUser = userRepository.findByEmail(userEmail);
        foundUser.getTaskList().remove(task);
        taskRepository.delete(task);
    }
    private Project findProject(String userEmail,String projectName){
        User foundUser = userRepository.findByEmail(userEmail);
        Set<Project> listOfProject = foundUser.getProjectList();
        for (Project project :listOfProject) {
            if (project.getName().equals(projectName)) return  project;
        }
    return null;
    }
}
