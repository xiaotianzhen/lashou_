package com.yicooll.dong.lashou.view;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.yicooll.dong.lashou.R;


/**
 * Created by sky on 2017/4/29.
 */

public class ToolbarX {

    private Toolbar mToolbar;
    private AppCompatActivity mActivity;   //v7包的AppCompatActivity才可以
    private ActionBar mActionBar;
    private RelativeLayout mRelativeLayout;

    public ToolbarX(Toolbar toolbar, AppCompatActivity activity) {

        this.mActivity = activity;
        this.mToolbar = toolbar;

        mRelativeLayout = (RelativeLayout) mToolbar.findViewById(R.id.rl_custom);

        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mActivity.finish();
            }
        });

    }

    /**
     * 因为toolbar设置标题需要放在setSupportActionBar(toolbar)前面，所以这里用actionBar设置标题
     *
     * @param str
     * @return
     */
    public ToolbarX setTitle(String str) {

        mActionBar.setTitle(str);
        return this;
    }

    public ToolbarX setSubtitle(String str) {
        mActionBar.setSubtitle(str);
        return this;
    }

    public ToolbarX setTitle(int Resid) {

        mActionBar.setTitle(Resid);
        return this;
    }

    public ToolbarX setSubtitle(int Resid) {

        mActionBar.setSubtitle(Resid);
        return this;
    }

    public ToolbarX setLogo(int Resid) {

        mToolbar.setLogo(Resid);

        return this;
    }

    public ToolbarX setLogo(Drawable drawable) {

        mToolbar.setLogo(drawable);
        return this;
    }

    public ToolbarX setNavigationIcon(int Resid) {

        mToolbar.setNavigationIcon(Resid);
        return this;
    }

    public ToolbarX setNavigationIcon(Drawable drawable) {

        mToolbar.setNavigationIcon(drawable);
        return this;
    }

    public ToolbarX setDisplayHomeAsUpEnabled(boolean b) {

        mActionBar.setDisplayHomeAsUpEnabled(b);
        return this;
    }

    public ToolbarX setCustomView(View view){

        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(view);
        return this;
    }

    public ToolbarX hide(){

        mActionBar.hide();
        return this;
    }

}
