package edu.northeastern.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Global Exception Handler
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Exception Handler Method
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sqlExceptionHandler(SQLIntegrityConstraintViolationException sqlEx) {
        log.error(sqlEx.getMessage());

        if (sqlEx.getMessage().contains("Duplicate entry")) {
            String[] split = sqlEx.getMessage().split(" ");
            String msg = split[2] + " already existed! ";
            return R.error(msg);
        }
        return R.error("Unknown Error ... !!!");
    }

    /**
     * Exception Handler Method
     */
    @ExceptionHandler(CustomException.class)
    public R<String> sqlExceptionHandler(CustomException customEx) {
        log.error(customEx.getMessage());

        return R.error(customEx.getMessage());
    }
}
