package com.yicooll.dong.lashou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.activity.AccountManagerActivity;
import com.yicooll.dong.lashou.activity.LoginActivity;
import com.yicooll.dong.lashou.bean.User;
import com.yicooll.dong.lashou.util.SpUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    @BindView(R.id.ll_gologin)
    LinearLayout llGoLogin;
    @BindView(R.id.ll_show_login_info)
    LinearLayout llShowLogin;
    @BindView(R.id.tv_loginname)
    TextView tvLoginname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Gson gson = new Gson();
        User user = gson.fromJson(SpUtil.getString(getActivity(), "user"), User.class);
        if (user != null) {
            llGoLogin.setVisibility(View.GONE);
            llShowLogin.setVisibility(View.VISIBLE);
            tvLoginname.setText(user.getName());
        } else {
            llGoLogin.setVisibility(View.VISIBLE);
            llShowLogin.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.btn_gologin, R.id.ll_show_login_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gologin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.ll_show_login_info:
                startActivity(new Intent(getActivity(), AccountManagerActivity.class));
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(getActivity()).unbind();
    }


}
