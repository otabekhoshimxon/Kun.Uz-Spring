package uz.kun.controller;

import uz.kun.exps.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHanlerController {


    @ExceptionHandler({BadRequestException.class, ItemNotFoundException.class,
            AlreadyExist.class, AlreadyExistPhone.class, AlreadyExistNameAndSurName.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
