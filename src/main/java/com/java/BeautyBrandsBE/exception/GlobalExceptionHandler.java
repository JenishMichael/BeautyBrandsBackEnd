package com.java.BeautyBrandsBE.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Save Method
    @ExceptionHandler(CategoryAlreadyPresentException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyPresentException(CategoryAlreadyPresentException categoryAlreadyPresent, HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), categoryAlreadyPresent.getMessage(),httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField()+": "+ fieldError.getDefaultMessage()).collect(Collectors.joining(", "))
        ,httpServletRequest.getRequestURI());
        System.out.println("All "+methodArgumentNotValidException.getBindingResult().getFieldErrors());
        return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    // Update Method
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException, HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), categoryNotFoundException.getMessage(),httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    //Deactivate & Activate Category
    @ExceptionHandler(CategoryStateException.class)
    public  ResponseEntity<ErrorResponse> handleCategoryStateException(CategoryStateException categoryStateException, HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), categoryStateException.getMessage(),httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    //SubCategory Create
    @ExceptionHandler(SubCategoryAlreadyPresentException.class)
    public ResponseEntity<ErrorResponse> handleSubCategoryAlreadyPresentException(SubCategoryAlreadyPresentException subCategoryAlreadyPresentException, HttpServletRequest httpServletRequest){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), subCategoryAlreadyPresentException.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    //SubCategory getAll
    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubCategoryNotFound(SubCategoryNotFoundException ex, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),httpServletRequest.getRequestURI() );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //Deactivate or Activate SubCategory
    @ExceptionHandler(SubCategoryStatusException.class)
    public ResponseEntity<ErrorResponse> handleSubCategoryStatusException(SubCategoryStatusException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //Listing Create
    @ExceptionHandler(SubCategoryMismatchException.class)
    public ResponseEntity<ErrorResponse> handleSubCategoryMismatchException(SubCategoryMismatchException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // To Get All True Listing
    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleListingNotFoundException(ListingNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListingStateException.class)
    public ResponseEntity<ErrorResponse> handleListingStateException(ListingStateException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
