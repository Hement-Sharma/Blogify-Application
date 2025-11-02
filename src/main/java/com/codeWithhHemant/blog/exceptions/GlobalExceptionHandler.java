package com.codeWithhHemant.blog.exceptions;

import com.codeWithhHemant.blog.paylods.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        ApiResponse apiResponse = new ApiResponse(e.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> resp = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach( error->{
           String fieldName = ((FieldError)error).getField(); //typecastin error to FieldError
            String errorMessage = error.getDefaultMessage();
            resp.put(fieldName,errorMessage);
        });

        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }

}
