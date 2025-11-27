package com.codeWithhHemant.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;
    private String fieldValue2;

    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){

        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName,String fieldName,String fieldValue2){

        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue2));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue2 = fieldValue2;
    }

}
