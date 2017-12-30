package com.interjoy.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.interjoy.FruitsIdentification.MainActivity;
import com.interjoy.FruitsIdentification.R;
import com.interjoy.framework.FrameworkActivity;

public class LoginActivity extends BaseActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberpassword;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        rememberpassword = (CheckBox)findViewById(R.id.remember_password);
        accountEdit =(EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        boolean isRemember = preferences.getBoolean("remember_password",false);
        if (isRemember){
            String account = preferences.getString("account","");
            String password = preferences.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberpassword.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (account.equals("Ruan")&&password.equals("123456")){
                    Toast.makeText(LoginActivity.this,"Login Successfully!!!", Toast.LENGTH_SHORT).show();
                    editor = preferences.edit();
                    if (rememberpassword.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, FrameworkActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
