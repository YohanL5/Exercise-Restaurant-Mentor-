package com.restaurant.Restaurant.exception;

import com.restaurant.Restaurant.exception.impl.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> clentNotFoundHandler (ClientNotFoundException clientNotFoundException){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.CLIENT_NOT_FOUND)
                .description(clientNotFoundException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(clientNotFoundException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> dataAlreadyExistsHandler(DataAlreadyExistsException dataAlreadyExistsException ){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.DATA_ALREADY_EXISTS)
                .description(dataAlreadyExistsException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(dataAlreadyExistsException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DocumentExistsException.class)
    public ResponseEntity<CustomErrorResponse> documentExistsHandler (DocumentExistsException documentExistsException){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.DOCUMENT_ALREADY_EXISTS)
                .description(documentExistsException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(documentExistsException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FantasyNameExistsException.class)
    public ResponseEntity<CustomErrorResponse> fantasyNameExistsHandler(FantasyNameExistsException fantasyNameExistsException){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.FANTASY_NAME_ALREADY_EXISTS)
                .description(fantasyNameExistsException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(fantasyNameExistsException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(InvalidOrIncompleteDataException.class)
    public ResponseEntity<CustomErrorResponse> invalidOrIncompleteDataHandler (InvalidOrIncompleteDataException invalidOrIncompleteDataException){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.INVALID_OR_INCOMPLETE_DATA)
                .description(invalidOrIncompleteDataException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(invalidOrIncompleteDataException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> productNotFoundException (ProductNotFoundException productNotFoundException){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.PRODUCT_NOT_FOUND)
                .description(productNotFoundException.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(productNotFoundException.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<CustomErrorResponse> internalServerError (InternalServerError internalServerError){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .code(ExceptionCode.INTERNAL_SERVER_ERROR)
                .description(internalServerError.getMessage())
                .timeStamp(LocalDateTime.now())
                .exception(internalServerError.getCause().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
