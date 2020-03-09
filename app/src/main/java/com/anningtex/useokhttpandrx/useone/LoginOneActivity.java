package com.anningtex.useokhttpandrx.useone;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.anningtex.useokhttpandrx.R;
import com.anningtex.useokhttpandrx.base.BaseActivity;
import com.anningtex.useokhttpandrx.dialog.LoadingView;
import com.anningtex.useokhttpandrx.useone.utilsone.OkHttpUtil;
import com.anningtex.useokhttpandrx.otheractivity.OneActivity;
import com.anningtex.useokhttpandrx.utilsmanger.MD5Util;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Administrator
 * desc: 第一种只运用 OkHttp
 */
public class LoginOneActivity extends BaseActivity {
    @BindView(R.id.login_edit_username)
    EditText loginEditUsername;
    @BindView(R.id.login_edit_password)
    EditText loginEditPassword;

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
        return R.layout.activity_login_one;
    }

    @Override
    protected void initViews() {
        loadingView = new LoadingView(LoginOneActivity.this);
    }

    @OnClick(R.id.login_tv_login)
    public void onViewClicked() {
        loginToMain();
    }

    private void loginToMain() {
        loadingView.show();

        name = loginEditUsername.getText().toString();
        password = loginEditPassword.getText().toString();

        FormBody.Builder requestBuild = new FormBody.Builder();
        //添加请求体
        RequestBody requestBody = requestBuild
                .add("username", name)
                .add("password", MD5Util.md5(password))
                .build();

        OkHttpUtil.sendOkHttpRequest(URL, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fail ", e.getMessage());
                loadingView.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("success ", result);

                if (response.body() != null) {
                    response.body().close();
                }
                loadingView.dismiss();

                $startActivity(OneActivity.class);
            }
        });
    }
}
