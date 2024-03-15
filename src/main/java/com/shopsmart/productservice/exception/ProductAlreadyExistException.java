package com.shopsmart.productservice.exception;

import java.io.Serial;

public class ProductAlreadyExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2560786044694461699L;

    public ProductAlreadyExistException() {}

    public ProductAlreadyExistException(String message) {
        super(message);
    }

    public ProductAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public ProductAlreadyExistException(String message,
                                        Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
