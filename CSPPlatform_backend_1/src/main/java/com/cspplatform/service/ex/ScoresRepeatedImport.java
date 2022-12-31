package com.cspplatform.service.ex;

public class ScoresRepeatedImport extends ServiceException{
    public ScoresRepeatedImport() {
        super();
    }

    public ScoresRepeatedImport(String message) {
        super(message);
    }

    public ScoresRepeatedImport(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoresRepeatedImport(Throwable cause) {
        super(cause);
    }

    protected ScoresRepeatedImport(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}