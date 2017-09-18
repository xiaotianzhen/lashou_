package com.yicooll.dong.lashou.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTabHost;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.fragment.AccountFragment;
import com.yicooll.dong.lashou.fragment.MoreFragment;
import com.yicooll.dong.lashou.fragment.NearbyFragment;
import com.yicooll.dong.lashou.fragment.ShopFragment;
import com.yicooll.dong.lashou.view.ToolbarX;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tabhost)
    FragmentTabHost tabhost;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    private Class[] fragments;
    private ToolbarX mToolbarX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolbarX=getToolbar();
        mToolbarX.hide();
        fragments = new Class[]{ShopFragment.class, NearbyFragment.class, AccountFragment.class, MoreFragment.class};
        tabhost.setup(this, getSupportFragmentManager(), R.id.fl_content);

        for (int i = 0; i < fragments.length; i++) {
            TabHost.TabSpec tabspec = tabhost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            tabhost.addTab(tabspec, fragments[i], null);
        }
        tabhost.setCurrentTab(0);
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.radio1:
                        tabhost.setCurrentTab(0);
                        break;
                    case R.id.radio2:
                        tabhost.setCurrentTab(1);
                        break;
                    case R.id.radio3:
                        tabhost.setCurrentTab(2);
                        break;
                    case R.id.radio4:
                        tabhost.setCurrentTab(3);
                        break;
                }
                //重新加载菜单
               // supportInvalidateOptionsMenu();
            }

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
