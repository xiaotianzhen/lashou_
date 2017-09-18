package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.view.ToolbarX;


/**
 * Created by sky on 2017/4/29.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private LinearLayout contentPanel;
    private Toolbar mToolbar;
    private ToolbarX toolbarX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        contentPanel = (LinearLayout) findViewById(R.id.contentPanel);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        View view = getLayoutInflater().inflate(getLayoutId(), null, false);
        contentPanel.addView(view);
        toolbarX = new ToolbarX(mToolbar, this);
    }

    public abstract int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
        super.finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
        super.startActivityForResult(intent, requestCode);
    }

    public ToolbarX getToolbar() {

        if (toolbarX == null) {
            toolbarX = new ToolbarX(mToolbar, this);
        }

        return toolbarX;
    }
}
