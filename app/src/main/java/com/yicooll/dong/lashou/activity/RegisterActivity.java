package com.yicooll.dong.lashou.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.JSONResponse;
import com.yicooll.dong.lashou.bean.User;
import com.yicooll.dong.lashou.http.Api;
import com.yicooll.dong.lashou.okHttp.BaseCallback;
import com.yicooll.dong.lashou.okHttp.OkhttpHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_comfirm_pwd)
    EditText etComfirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.im_register_back,R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_register_back:
                finish();
                break;
            case R.id.btn_register:
                onRegister();
                break;
        }
    }

    private void onRegister() {

        if (etRegisterName.getText().toString().trim().equals("")) {
            etRegisterName.requestFocus();
            etRegisterName.setError("请输入用户名");
            return;
        }
        if (etRegisterPwd.getText().toString().trim().equals("")) {
            etRegisterPwd.requestFocus();
            etRegisterPwd.setError("请输入密码");
            return;
        }
        if (!etComfirmPwd.getText().toString().trim().equals(etRegisterPwd.getText().toString().trim())) {
            etRegisterPwd.requestFocus();
            etRegisterPwd.setError("确认密码不一致");
            return;
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("name",etRegisterName.getText().toString().trim());
        map.put("pwd",etComfirmPwd.getText().toString().trim());
        OkhttpHelper.getIntance().post(Api.REIGISTER, map, new BaseCallback<JSONResponse<User>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, JSONResponse<User> result) {
                if(result.getCode()==1){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {
                    Toast.makeText(RegisterActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
