package com.example.taskflow_1.exceptionHandler;

public class OperationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OperationException(String msg) {
        super(msg);
    }
}
