package com.blog.Blogging_Application_API.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotfoundException extends RuntimeException{

    String resouceName;
    String fieldName;
    long fieldValue;

    public ResourceNotfoundException(String resouceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s", resouceName , fieldName , fieldValue));
        this.resouceName = resouceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
