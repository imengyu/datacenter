package com.imengyu.datacenter.exception;

public class BadTokenException extends RuntimeException {
    public BadTokenException(String msg) {
        super(msg);
    }
}
