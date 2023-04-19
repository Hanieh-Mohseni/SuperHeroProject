package com.sg.superhero.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
//@RestController
public class ControllerExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Could not save your data. "
            + "Please ensure it is valid and try again.";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Error> handleUserNotFoundException(EmptyResultDataAccessException ex,
                                                                   WebRequest request) {
        Error err = new Error();
        err.setMessage("Could not get your game.");
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> globalExceptionHandler(Exception ex,
                                                              WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
