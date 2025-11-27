package com.codeWithhHemant.blog.exceptions;

import com.codeWithhHemant.blog.paylods.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
           String fieldName = ((FieldError)error).getField(); //typecasting error to FieldError
            String errorMessage = error.getDefaultMessage();
            resp.put(fieldName,errorMessage);
        });

        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> handleIOException(IOException ex) {
        ApiResponse response = new ApiResponse("File operation failed on server: " + ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException ex){
        ApiResponse response = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCreadentialsException(BadCredentialsException ex){
        ApiResponse response = new ApiResponse("Invalid UserName Or Password !!!",false);
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

}
