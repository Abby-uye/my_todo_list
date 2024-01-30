package com.RegiusPortus.my_todo_list.userTest;

import com.RegiusPortus.my_todo_list.data.models.User;
import com.RegiusPortus.my_todo_list.data.repositories.ProjectRepository;
import com.RegiusPortus.my_todo_list.data.repositories.UserRepository;
import com.RegiusPortus.my_todo_list.dtos.UserLoginRequest;
import com.RegiusPortus.my_todo_list.dtos.UserRegistrationRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.AddTaskToProjectRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.ProjectCreationRequest;
import com.RegiusPortus.my_todo_list.dtos.projectDtos.ProjectRemovalRequest;
import com.RegiusPortus.my_todo_list.exceptions.ProjectException;
import com.RegiusPortus.my_todo_list.exceptions.UserException;
import com.RegiusPortus.my_todo_list.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
//@AfterEach
//    void deleteAfter(){
//        userRepository.deleteAll();
//    }
    @Test
    void TestThatCanRegisterUser() throws UserException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("Abby");
        userRegistrationRequest.setEmail("princessabby133@gmail.com");
        userRegistrationRequest.setPassword("Pass12@*");
        userRegistrationRequest.setConfirmPassword("Pass12@*");
        userService.registerUser(userRegistrationRequest);
        assertEquals(1,userRepository.count());
    }
    @Test
    void TestThatCanRegister2UserAndCountIsTwo() throws UserException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("Abby");
        userRegistrationRequest.setEmail("princessa133@gmail.com");
        userRegistrationRequest.setPassword("Pass12@*");
        userRegistrationRequest.setConfirmPassword("Pass12@*");
        userService.registerUser(userRegistrationRequest);
        assertEquals(2,userRepository.count());
    }
    @Test
void testThatCAnThrowExceptionIfUserAlreadyExist() throws UserException {
    UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
    userRegistrationRequest.setName("Abby");
    userRegistrationRequest.setEmail("princessabby133@gmail.com");
    userRegistrationRequest.setPassword("Pass12@*");
    userRegistrationRequest.setConfirmPassword("Pass12@*");

    assertThrows(UserException.class,() -> userService.registerUser(userRegistrationRequest));
}
@Test
    void testThatCAnThrowExceptionIfWrongConfirmPassword() throws UserException {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("uye");
        userRegistrationRequest.setEmail("princessloveabby133@gmail.com");
        userRegistrationRequest.setPassword("Pass12@*");
        userRegistrationRequest.setConfirmPassword("Pass12@");

        assertThrows(UserException.class,() -> userService.registerUser(userRegistrationRequest));
    }
    @Test
    void testThatCAnThrowExceptionIfWrongPasswordWhenLoggingIn() throws UserException {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("princessabby133@gmail.com");
        userLoginRequest.setPassword("Pass12");
        assertThrows(UserException.class,() -> userService.login(userLoginRequest));
    }
    @Test
    void testThatCAnLoginWithCorrectPassword() throws UserException {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("princessabby133@gmail.com");
        userLoginRequest.setPassword("Pass12@*");
        assertThrows(UserException.class,() -> userService.login(userLoginRequest));
    }
    @Test
    void testThatUserCannotCreateProjectIfUserIsNotLoggedin() throws ProjectException, UserException {
        ProjectCreationRequest projectCreationRequest = new ProjectCreationRequest();
        projectCreationRequest.setDescription("Learn javascript");
        projectCreationRequest.setName("JavaScript");
        projectCreationRequest.setUserEmail("princessabby133@gmail.com");
        projectCreationRequest.setStartDate(LocalDate.of(2024,01,27));
        projectCreationRequest.setDueDate(LocalDate.of(2024,01,27));

        assertThrows(UserException.class,() -> userService.createProject(projectCreationRequest));
    }

    @Test
    void testThatUserCanCreateProjectIfUserIsLoggedin() throws ProjectException, UserException {
        ProjectCreationRequest projectCreationRequest = new ProjectCreationRequest();
        projectCreationRequest.setUserEmail("princessabby133@gmail.com");
        projectCreationRequest.setDescription("Learn javascript");
        projectCreationRequest.setName("JavaScript");
        projectCreationRequest.setStartDate(LocalDate.of(2024, 01, 27));
        projectCreationRequest.setDueDate(LocalDate.of(2024, 01, 27));
        userService.createProject(projectCreationRequest);
        assertEquals(1, projectRepository.count());
    }
    @Test
    void testwhenUserCreatesProject_ProjectIsAddedToUserListOfProject() throws ProjectException, UserException {
        ProjectCreationRequest projectCreationRequest = new ProjectCreationRequest();
        projectCreationRequest.setUserEmail("princessabby133@gmail.com");
        projectCreationRequest.setDescription("Learn javascript");
        projectCreationRequest.setName("Java");
        projectCreationRequest.setStartDate(LocalDate.of(2024, 01, 27));
        projectCreationRequest.setDueDate(LocalDate.of(2024, 01, 27));
        userService.createProject(projectCreationRequest);
        User foundUser = userRepository.findByEmail(projectCreationRequest.getUserEmail());
        assertEquals(2, foundUser.getProjectList().size());
    }
    @Test
    void testThatWhenIRemoveAProjectCountReduce() throws ProjectException, UserException {
        ProjectRemovalRequest projectRemovalRequest = new ProjectRemovalRequest ();
        projectRemovalRequest.setName("JavaScript");
        projectRemovalRequest.setUserEmail("princessabby133@gmail.com");
        userService.removeProject(projectRemovalRequest);
        User foundUser = userRepository.findByEmail(projectRemovalRequest.getUserEmail());
        assertEquals(4,foundUser.getProjectList().size());
    }
    @Test
    void testThatCanAddTaskToProject(){
        AddTaskToProjectRequest addTaskToProjectRequest = new AddTaskToProjectRequest();
        addTaskToProjectRequest.setProjectName("JavaScript");
        addTaskToProjectRequest.setTaskName("Read chapter ");
        addTaskToProjectRequest.setUserEmail("princessabby133@gmail.com");
        userService.addTaskToProject(addTaskToProjectRequest);
    }

}