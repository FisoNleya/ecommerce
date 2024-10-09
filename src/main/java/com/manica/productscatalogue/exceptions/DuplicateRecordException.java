package com.manica.productscatalogue.exceptions;

public class DuplicateRecordException extends RuntimeException {
    private static final long serialVersionUID = 2343432L;

    public DuplicateRecordException(String message) {
        super(message);
    }
}
