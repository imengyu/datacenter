package com.imengyu.datacenter.exception;

public class ConstraintViolationExceptionResponseEntity extends ErrorResponseEntity {

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public ConstraintViolationExceptionResponseEntity(int code, String message, String error) {
        super(code, message);
        this.error = error;
    }
}
