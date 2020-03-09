package com.anningtex.useokhttpandrx.usetwo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.anningtex.useokhttpandrx.R;
import com.anningtex.useokhttpandrx.base.BaseActivity;
import com.anningtex.useokhttpandrx.bean.LoginBean;
import com.anningtex.useokhttpandrx.dialog.LoadingView;
import com.anningtex.useokhttpandrx.otheractivity.TwoActivity;
import com.anningtex.useokhttpandrx.usetwo.okhttp.ExceptionCode;
import com.anningtex.useokhttpandrx.usetwo.okhttp.OkhttpException;
import com.anningtex.useokhttpandrx.usetwo.rx.RxHttp;
import com.anningtex.useokhttpandrx.usetwo.rx.RxJsonHttpHandler;
import com.anningtex.useokhttpandrx.utilsmanger.MD5Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * desc:第二种运用 OkHttp  +  Rx
 */
public class LoginTwoActivity extends BaseActivity {
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.login_edit_username)
    EditText loginEditUsername;
    @BindView(R.id.login_edit_password)
    EditText loginEditPassword;

    private static final String TAG = "Rx_Ok";
    private RxHttp mRxHttp;

    private LoadingView loadingView;
    private String name, password;
    private String URL = "http://192.168.0.155:8080/login_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login_two;
    }

    @Override
    protected void initViews() {
        loadingView = new LoadingView(LoginTwoActivity.this);

        mRxHttp = new RxHttp();
    }

    @OnClick(R.id.login_tv_login)
    public void onViewClicked() {
        postListenerData();
    }

    private void postListenerData() {
        name = loginEditUsername.getText().toString();
        password = loginEditPassword.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", MD5Util.md5(password));
        Log.e("response", String.valueOf(map));

        mRxHttp.post(URL, map, new RxJsonHttpHandler<LoginBean>(LoginBean.class) {
                    @Override
                    public void onStart() {
                        // 请求之前，弹出dialog 等
                        Log.e(TAG, "onStart");
                        loadingView.show();
                    }

                    @Override
                    public void onSuccess(LoginBean responseOkBean) {
                        loadingView.dismiss();

                        int code = responseOkBean.getCode();
                        if (code == 1) {
                            Object data = responseOkBean.getData();
                            Log.e(TAG, data.toString());

                            $startActivity(TwoActivity.class);
                        } else {
                            String msg = responseOkBean.getMsg();
                            if (TextUtils.isEmpty(msg)) {
                                Log.e(TAG, "xxx失败");
                            } else {
                                Log.e(TAG, msg);
                            }
                        }
                    }

                    @Override
                    public void onError(int code) {
                        switch (code) {
                            case ExceptionCode.CODE_REQUEST_FAIL:
                                Log.e(TAG, "请求失败");
                                toastShort("请求失败");
                                break;
                            case ExceptionCode.CODE_NET_ERROR:
                                Log.e(TAG, "网络异常");
                                toastShort("网络异常");
                                break;
                            case ExceptionCode.CODE_DATA_FORMAT:
                                Log.e(TAG, "请求数据格式不正确");
                                toastShort("请求数据格式不正确");
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFinish() {
                        // 请求结束：关闭dialog 等
                        Log.e(TAG, "onFinish");
                        loadingView.dismiss();
                    }
                }
        );
    }

    /**
     * -----------------------------------------------------------------------------------
     */
    public void get(View v) {
        mRxHttp.get(URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        if (throwable.getClass().equals(OkhttpException.class)) {
                            int code = ((OkhttpException) throwable).getCode();
                            switch (code) {
                                case ExceptionCode.CODE_REQUEST_FAIL:
                                    // TODO: 请求失败
                                    break;
                                case ExceptionCode.CODE_NET_ERROR:
                                    // TODO: 网络异常
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    public void get2(View v) {
        String url = mEtUrl.getText().toString().trim();

        mRxHttp.get(url, new RxJsonHttpHandler<LoginBean>(LoginBean.class) {

            @Override
            public void onStart() {
                // 请求之前，弹出dialog 等
                Log.e(TAG, "onStart");
            }

            @Override
            public void onSuccess(LoginBean responseOkBean) {
                int code = responseOkBean.getCode();
                if (code == 1) {
                    Object data = responseOkBean.getData();
                    Log.e(TAG, data.toString());
                    // TODO: setView...设置界面数据
                } else {
                    String msg = responseOkBean.getMsg();
                    if (TextUtils.isEmpty(msg)) {
                        Log.e(TAG, "xxx失败");
                    } else {
                        Log.e(TAG, msg);
                    }
                }
            }

            @Override
            public void onError(int code) {
                switch (code) {
                    case ExceptionCode.CODE_REQUEST_FAIL:
                        Log.e(TAG, "请求失败");
                        toastShort("请求失败");
                        break;
                    case ExceptionCode.CODE_NET_ERROR:
                        Log.e(TAG, "网络异常");
                        toastShort("网络异常");
                        break;
                    case ExceptionCode.CODE_DATA_FORMAT:
                        Log.e(TAG, "请求数据格式不正确");
                        toastShort("请求数据格式不正确");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFinish() {
                // 请求结束：关闭dialog 等
                Log.e(TAG, "onFinish");
            }
        });
    }
}
