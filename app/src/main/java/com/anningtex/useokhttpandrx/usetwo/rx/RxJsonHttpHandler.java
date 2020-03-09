package com.anningtex.useokhttpandrx.usetwo.rx;

import com.anningtex.useokhttpandrx.usetwo.okhttp.ExceptionCode;
import com.google.gson.Gson;

/**
 * @Author Song
 * @Desc:
 */
public class RxJsonHttpHandler<T> implements RxHttpHandler {
    private Class<T> clazz;

    public RxJsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(String s) {
        T t = new Gson().fromJson(s, clazz);
        if (t != null) {
            onSuccess(t);
        } else {
            onError(ExceptionCode.CODE_DATA_FORMAT);
        }
    }

    public void onSuccess(T t) {

    }

    @Override
    public void onError(int code) {

    }

    @Override
    public void onFinish() {

    }
}
