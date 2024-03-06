package com.shopsmart.productservice.exception;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3811768928456940938L;

    public ProductNotFoundException() {}

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductNotFoundException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
