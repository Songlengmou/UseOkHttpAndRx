package com.anningtex.useokhttpandrx.otheractivity;

import android.os.Bundle;
import android.view.View;

import com.anningtex.useokhttpandrx.R;
import com.anningtex.useokhttpandrx.base.BaseActivity;
import com.anningtex.useokhttpandrx.useone.LoginOneActivity;
import com.anningtex.useokhttpandrx.usetwo.LoginTwoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * desc: OkHttp+Rx  与  单独的OkHttp  使用
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                $startActivity(LoginOneActivity.class);
                break;
            case R.id.btn_two:
                $startActivity(LoginTwoActivity.class);
                break;
            default:
                break;
        }
    }
}
