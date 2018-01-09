package com.interjoy.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.interjoy.FruitsIdentification.R;
import com.interjoy.framework.FrameworkActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainLoginActivity extends AppCompatActivity {

    private Tencent mTencent;
    private String openidString;
    private ImageView iv_qq_login;
    private ImageView iv_sms_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_login);

        mTencent = Tencent.createInstance("1106650352",getApplicationContext());//这里的“1106650352”为自己申请的Appid
        //qq登录
        iv_qq_login =  (ImageView)findViewById(R.id.qq_login);
        iv_qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencent.login(MainLoginActivity.this,"all",new BaseUiListener());
            }
        });

        //账号登录
        iv_sms_login = (ImageView)findViewById(R.id.sms_login);
        iv_sms_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }

    }

    private class BaseUiListener implements IUiListener {
        public void onComplete(Object response){
            // TODO Auto-generated method stub
            /*
            * 下面隐藏的是用户登录成功后 登录用户数据的获取的方法
            * 共分为两种  一种是简单的信息的获取,另一种是通过UserInfo类获取用户较为详细的信息
            *有需要看看
            * */
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                Log.v("----TAG--", "-------------"+response.toString());
                openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);

                mTencent.setAccessToken(((JSONObject) response).getString("access_token"),((JSONObject) response).getString("expires_in"));


                Log.v("TAG", "-------------"+openidString);
                //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /*到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？  */
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);

            //    info.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    //用户信息获取到了

                    try {

                        Toast.makeText(getApplicationContext(), ((JSONObject) o).getString("nickname")+"登录成功", Toast.LENGTH_SHORT).show();
                        Log.v("UserInfo",o.toString());
                        Intent intent1 = new Intent(MainLoginActivity.this, FrameworkActivity.class);
                        String nick_name=((JSONObject) o).getString("nickname");
                        intent1.putExtra("nickname",nick_name);
                        startActivity(intent1);
                        finish();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Log.v("UserInfo","onError");
                }

                @Override
                public void onCancel() {
                    Log.v("UserInfo","onCancel");
                }
            });
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
        }



    }


}
