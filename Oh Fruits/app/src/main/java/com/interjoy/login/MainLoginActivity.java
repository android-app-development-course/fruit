package com.interjoy.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.interjoy.FruitsIdentification.R;

public class MainLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //qq登录
        ImageView iv_qq_login =  (ImageView)findViewById(R.id.qq_login);
        iv_qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //待添加
            }
        });

        //账号登录
        ImageView iv_sms_login = (ImageView)findViewById(R.id.sms_login);
        iv_sms_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}
