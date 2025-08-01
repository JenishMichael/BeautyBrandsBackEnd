package com.java.BeautyBrandsBE.exception;

public class CategoryAlreadyPresentException extends RuntimeException {
    public CategoryAlreadyPresentException(String message) {
        super(message);
    }
}
