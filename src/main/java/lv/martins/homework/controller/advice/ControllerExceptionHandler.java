package lv.martins.homework.controller.advice;

import lv.martins.homework.exceptions.ConflictException;
import lv.martins.homework.exceptions.CustomException;
import lv.martins.homework.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseModel> handleGenericException(Exception e){
        return ResponseEntity.internalServerError().body(new ExceptionResponseModel(e.getMessage(), 5001));
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseModel> handleNotFoundException(NotFoundException e){
        return ResponseEntity.status(404).body(new ExceptionResponseModel(e.getMessage(), e.getCode()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseModel> handleCustomException(CustomException e){
        return ResponseEntity.badRequest().body(new ExceptionResponseModel(e.getMessage(), e.getCode()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponseModel> handleInvalidArgumentException(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseModel("Invalid argument value '%s'='%s'. Expected %s".formatted(e.getName(), e.getValue(), e.getRequiredType() == null ? "'null'" : e.getRequiredType().getSimpleName()), 4001));
    }

}
