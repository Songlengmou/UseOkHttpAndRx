package com.anningtex.useokhttpandrx.usetwo.rx;

/**
 * @Author Song
 * @Desc:
 */
public interface RxHttpHandler {
    void onStart();

    void onSuccess(String s);

    void onError(int code);

    void onFinish();
}
