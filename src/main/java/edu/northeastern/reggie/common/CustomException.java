package edu.northeastern.reggie.common;

/**
 * self defined the exception, show the runtime exception error message
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
