package com.cspplatform.service.ex;

public class SuffixErrorException extends ServiceException{
    public SuffixErrorException(String message) {
        super(message);
    }

    public SuffixErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuffixErrorException(Throwable cause) {
        super(cause);
    }

    protected SuffixErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
