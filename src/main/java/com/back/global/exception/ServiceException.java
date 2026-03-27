package com.back.global.exception;

import com.back.global.rsData.RsData;

public class ServiceException extends RuntimeException {

    private final String msg;
    private final String resultCode;

    public ServiceException(String resultCode,String msg) {
        super(msg);
        this.msg = msg;
        this.resultCode = resultCode;
    }

    public RsData<Void> getRsData() {
        return new RsData<>(
                msg,
                resultCode
        );
    }

}
