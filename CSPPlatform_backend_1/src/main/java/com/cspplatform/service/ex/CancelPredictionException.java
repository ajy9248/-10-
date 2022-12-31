package com.cspplatform.service.ex;

public class CancelPredictionException extends ServiceException{
    public CancelPredictionException() {
        super();
    }

    public CancelPredictionException(String message) {
        super(message);
    }

    public CancelPredictionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelPredictionException(Throwable cause) {
        super(cause);
    }

    protected CancelPredictionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
