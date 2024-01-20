package com.RegiusPortus.my_todo_list.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Builder
@Data
public class ApiResponse {
    private Object data;
    private HttpStatus httpStatus;
   private int statusCode;
   private boolean isSuccessful;
}
