package com.manica.productscatalogue.exceptions;

public class UnAuthorisedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnAuthorisedException(String message) {
        super(message);
    }
}
