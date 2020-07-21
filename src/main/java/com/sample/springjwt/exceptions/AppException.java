package com.sample.springjwt.exceptions;

public class AppException extends RuntimeException{

    private AppMsg appMsg;

    public AppException(AppMsg appMsg) {
        super(appMsg.toString());
        this.appMsg = appMsg;
    }

    public AppMsg getAppMsg() {
        return appMsg;
    }
}
