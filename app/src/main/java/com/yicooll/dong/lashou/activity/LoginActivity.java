package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.google.gson.Gson;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.JSONResponse;
import com.yicooll.dong.lashou.bean.User;
import com.yicooll.dong.lashou.http.Api;
import com.yicooll.dong.lashou.okHttp.BaseCallback;
import com.yicooll.dong.lashou.okHttp.OkhttpHelper;
import com.yicooll.dong.lashou.util.SpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.rb_account_logn)
    RadioButton rbAccountLogin;
    @BindView(R.id.rb_login_fast)
    RadioButton rbLoginFastb;
    @BindView(R.id.rg_login)
    RadioGroup mRadioGroup;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd_code)
    EditText etPwdCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private Animation lint_move_left;
    private Animation lint_move_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        ButterKnife.bind(this);
        lint_move_left = AnimationUtils.loadAnimation(this, R.anim.anim_line_move_left);
        lint_move_right = AnimationUtils.loadAnimation(this, R.anim.anim_line_move_right);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int radioId) {

                switch (radioId) {
                    case R.id.rb_account_logn:
                        vLine.startAnimation(lint_move_left);
                        btnCode.setVisibility(View.GONE);
                        etUsername.setHint("用户名/邮箱/手机号");
                        etPwdCode.setHint("请输入密码");
                        break;
                    case R.id.rb_login_fast:
                        vLine.startAnimation(lint_move_right);
                        btnCode.setVisibility(View.VISIBLE);
                        etUsername.setHint("请输入手机号");
                        etPwdCode.setHint("请输入密码");
                        break;
                }
            }
        });

        etUsername.addTextChangedListener(new MyTextWatcher());
        etPwdCode.addTextChangedListener(new MyTextWatcher());


    }


    public class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!etUsername.getText().toString().trim().equals("") && !etPwdCode.getText().toString().trim().equals("")) {
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setEnabled(false);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @OnClick({R.id.tv_register, R.id.btn_login, R.id.tv_qq_login, R.id.tv_weixin_login, R.id.tv_sina_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                onLogin();
                break;
            case R.id.im_login_back:
                finish();
                break;
            case R.id.tv_qq_login:
                Platform QZone = ShareSDK.getPlatform(cn.sharesdk.tencent.qzone.QZone.NAME);
                QZone.setPlatformActionListener(this);
                if (QZone.isAuthValid()) {
                    String openid = QZone.getDb().getUserId();
                    String nikeName = QZone.getDb().getUserName();
                    social(nikeName, openid);
                } else {
                    QZone.showUser(null);//开启授权窗口
                }
                //移除授权
                //weibo.removeAccount(true);
                break;
            case R.id.tv_weixin_login:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                if (wechat.isAuthValid()) {
                    String openid = wechat.getDb().getUserId();
                    String nikeName = wechat.getDb().getUserName();
                    social(nikeName, openid);
                } else {
                    wechat.showUser(null);//开启授权窗口
                }
                //移除授权
                //weibo.removeAccount(true);
                break;
            case R.id.tv_sina_login:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.setPlatformActionListener(this);
                if (sina.isAuthValid()) {
                    String openid = sina.getDb().getUserId();
                    String nikeName = sina.getDb().getUserName();
                    social(nikeName, openid);
                } else {
                    sina.showUser(null);//开启授权窗口
                }
                //移除授权
                //weibo.removeAccount(true);
                break;
        }
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        social(platform.getDb().getUserName(), platform.getDb().getUserId());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "授权错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
    }

    public void social(String nikeName, String uid) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", nikeName);
        params.put("pwd", uid);
        OkhttpHelper.getIntance().post(Api.SOCIAL, params, new BaseCallback<JSONResponse<User>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, JSONResponse<User> data) {
                if (data.getCode() == 1) {
                    SpUtil.putString(LoginActivity.this, "user", new Gson().toJson(data.getData()));
                    Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse() {

            }
        });

    }

    private void onLogin() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", etUsername.getText().toString().trim());
        params.put("pwd", etPwdCode.getText().toString().trim());
        OkhttpHelper.getIntance().post(Api.LOGIN, params, new BaseCallback<JSONResponse<User>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, JSONResponse<User> data) {
                if (data.getCode() == 1) {
                    SpUtil.putString(LoginActivity.this, "user", new Gson().toJson(data.getData()));
                    Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse() {

            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
