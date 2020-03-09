package com.anningtex.useokhttpandrx.usetwo.okhttp;

/**
 * @Author Song
 * @Desc: 异常类型
 */
public class OkhttpException extends Throwable {
    private int code;

    public OkhttpException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
