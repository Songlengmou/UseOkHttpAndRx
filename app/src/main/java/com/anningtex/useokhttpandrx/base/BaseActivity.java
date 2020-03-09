package com.anningtex.useokhttpandrx.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author Song
 * @Desc:
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initViews();
    }

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    public void $startActivity(Class<?> claz) {
        Intent intent = new Intent(this, claz);
        startActivity(intent);
    }

    public void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
