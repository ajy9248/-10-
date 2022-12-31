package com.cspplatform.service.ex;

/**
 * @ClassName : StudentsRepeatedImport  //类名
 * @Description :   //描述
 * @Author : MayBlackCat  //作者
 * @Date: 2022-12-30 18:55  //时间
 */
public class StudentsRepeatedImport extends ServiceException{
    public StudentsRepeatedImport() {
        super();
    }

    public StudentsRepeatedImport(String message) {
        super(message);
    }

    public StudentsRepeatedImport(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentsRepeatedImport(Throwable cause) {
        super(cause);
    }

    protected StudentsRepeatedImport(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
