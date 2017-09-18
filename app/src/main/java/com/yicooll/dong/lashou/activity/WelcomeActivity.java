package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.util.SpUtil;

public class WelcomeActivity extends AppCompatActivity {

    private static final int WELCOMEHANDLER = 1;
    private boolean isFrist ;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WELCOMEHANDLER) {
                isFrist= SpUtil.getBoolean(WelcomeActivity.this,"isfirst",true);
                if(isFrist){
                    SpUtil.putBoolean(WelcomeActivity.this,"isfirst",false);
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcom);
        mHandler.sendEmptyMessageDelayed(WELCOMEHANDLER, 3000);
    }
}
