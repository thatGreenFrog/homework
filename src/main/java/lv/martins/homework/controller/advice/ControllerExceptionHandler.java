package lv.martins.homework.controller.advice;

import lv.martins.homework.exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponseModel handleGenericException(Exception e){
        return new ExceptionResponseModel(e.getMessage(), 5001);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponseModel> handleConflictException(ConflictException e){
        return ResponseEntity
                .status(409)
                .header("Location", ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(e.getDuplicateId())
                        .toUri()
                        .toString())
                .body(new ExceptionResponseModel(e.getMessage(), e.getCode()));
    }

}
