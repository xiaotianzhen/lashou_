package com.yicooll.dong.lashou.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class WaitDialog extends ProgressDialog {


    public WaitDialog(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setMessage("正在加载，请稍后...");


    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }
}
