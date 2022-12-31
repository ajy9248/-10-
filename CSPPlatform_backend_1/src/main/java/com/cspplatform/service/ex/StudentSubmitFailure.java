package com.cspplatform.service.ex;

/**
 * @ClassName : StudentSubmitFailure  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-31 00:20  //时间
 */
public class StudentSubmitFailure extends ServiceException{
    public StudentSubmitFailure() {
        super();
    }

    public StudentSubmitFailure(String message) {
        super(message);
    }

    public StudentSubmitFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentSubmitFailure(Throwable cause) {
        super(cause);
    }

    protected StudentSubmitFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
