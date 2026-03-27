package com.back.global.exception;

import com.back.global.rsData.RsData;

public class ServiceException extends RuntimeException {

    private final String msg;
    private final String resultCode;

    public ServiceException(String msg, String resultCode) {
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
