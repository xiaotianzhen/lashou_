package com.yicooll.dong.lashou.activity;

import android.os.Bundle;
import android.view.View;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.util.SpUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManagerActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getToolbar().hide();

    }

    @OnClick({R.id.btn_logout, R.id.im_account_manager_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                SpUtil.clearShare(this);
                finish();
                break;
            case R.id.im_account_manager_back:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_manager;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
