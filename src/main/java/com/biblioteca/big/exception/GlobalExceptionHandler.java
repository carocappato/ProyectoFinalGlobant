package com.biblioteca.big.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> bookNotFoundException(BookNotFoundException ex,
                                                   WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<?> reservationNotFoundException(ReservationNotFoundException res,
                                                          WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(res.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<?> bookAlreadyExistsException(BookAlreadyExistsException ex,
                                                        WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> userAlreadyExistsException(UserAlreadyExistsException ex,
                                                        WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalEmailFormatException.class)
    public ResponseEntity<?> illegalEmailFormatException(IllegalEmailFormatException ex,
                                                         WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}

