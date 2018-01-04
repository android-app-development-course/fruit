package com.interjoy.logopage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.interjoy.FruitsIdentification.R;
import com.interjoy.login.BaseActivity;
import com.interjoy.login.LoginActivity;
import com.interjoy.login.MainLoginActivity;

public class LogoActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟三秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LogoActivity.this,
                        MainLoginActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);

    }
}