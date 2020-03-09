package com.anningtex.useokhttpandrx.usetwo.rx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.anningtex.useokhttpandrx.usetwo.okhttp.ExceptionCode;
import com.anningtex.useokhttpandrx.usetwo.okhttp.OkhttpException;
import com.anningtex.useokhttpandrx.usetwo.utilstwo.HttpUtil;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author Song
 * @Desc:
 */
public class RxHttp {
    private HttpUtil httpUtil = new HttpUtil();

    /**
     * GET
     *
     * @param url
     * @return
     */
    public Observable<String> get(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Exception {
                httpUtil.get(url, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(new OkhttpException(ExceptionCode.CODE_NET_ERROR));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String body = response.body().string();
                            Log.e("responseJson", body);
                            emitter.onNext(body);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new OkhttpException(ExceptionCode.CODE_REQUEST_FAIL));
                        }
                    }
                });
            }
        });
    }

    public void get(String url, final RxHttpHandler handler) {
        get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        handler.onStart();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        handler.onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        //强转为我们自定义的 OkHttpException，onError中传入异常类型
                        if (throwable.getClass().equals(OkhttpException.class)) {
                            int code = ((OkhttpException) throwable).getCode();
                            handler.onError(code);
                            handler.onFinish();
                        }
                    }

                    @Override
                    public void onComplete() {
                        handler.onFinish();
                    }
                });
    }

    /**
     * POST
     *
     * @param url
     * @param requestParams
     * @return
     */
    public Observable<String> post(final String url, final Map<String, String> requestParams) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Exception {
                httpUtil.post(url, requestParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.onError(new OkhttpException(ExceptionCode.CODE_NET_ERROR));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String body = response.body().string();
                            Log.e("responseJson", body);
                            emitter.onNext(body);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new OkhttpException(ExceptionCode.CODE_REQUEST_FAIL));
                        }
                    }
                });
            }
        });
    }

    public void post(String url, Map<String, String> requestParams, final RxHttpHandler handler) {
        post(url, requestParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        handler.onStart();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        handler.onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        //强转为我们自定义的 OkHttpException，onError中传入异常类型
                        if (throwable.getClass().equals(OkhttpException.class)) {
                            int code = ((OkhttpException) throwable).getCode();
                            handler.onError(code);
                            handler.onFinish();
                        }
                    }

                    @Override
                    public void onComplete() {
                        handler.onFinish();
                    }
                });
    }
}
