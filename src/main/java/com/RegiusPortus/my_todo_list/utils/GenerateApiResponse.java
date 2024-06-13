package com.RegiusPortus.my_todo_list.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class GenerateApiResponse {

    public static final String USER_ALREADY_EXIST = "A user with this info already exist try logging in instead";
    public static final String INVALID_EMAIL = "Enter a valid email";
    public static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
    public static final String WEAK_PASSWORD = "password must contain Upper case letters, lowercase letters, digits and special characters";
    public static final String THIS_USER_DOES_NOT_EXIST = "You do not have an account, try registering instead";
    public static final String INCORRECT_PASSWORD =  "email or password is incorrect please check and enter valid details";
    public static final Object SUCCESSFULLY_LOGGEDIN = "you have successfully logged in";
    public static final String INCORRECT_CONFIRM_PASSWORD = "Check and confirm password ";
    public static final String TAX_ALREADY_EXIST = "You have a task with this name already, i think you should work on completing the task instead" ;
    public static final String TASK_CREATED_SUCCESSFULLY = "Task created successfully, now lets work on completing it champ";
    public static final String TASK_DOES_NOT_EXIST = "There is no task with this name in your task list " ;
    public static final String SUCCESSFULLY_REMOVED_TASK = "successfully removed task";
    public static final String USER_IS_NOT_LOGGEDIN = "To create a new new project you need to be logged in";
    public static final String PROJECT_ALREADY_EXIST = "You have an existing project with this name i think you should work on completing it instead";
    public static final String PROJECT_CREATED_SUCCESSFULLY = "Project created Successfully";
    public static final String PROJECT_DOES_NOT_EXIST = "You do not have a project with this info";
    public static final String PROJECT_REMOVED_SUCCESSFULLY = "Project removed successfully" ;
    public static final String TASK_ADDED_TO_PROJECT_SUCCESSFULLY = "Task added to project successfully,remember completing a project depends, starts with completing a task,let go my champ";

    public static  ResponseEntity<ApiResponse> created(Object data){
        return new ResponseEntity<>(
                ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .build(),HttpStatus.CREATED);


    }

    public static  ApiResponse validationError(Object data) {
        return
                ApiResponse.builder()
                        .data(data)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .isSuccessful(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build();

    }

   public static ResponseEntity<ApiResponse> ok(Object data){
        return new ResponseEntity<>(ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true).build(),HttpStatus.OK);

   }


}
