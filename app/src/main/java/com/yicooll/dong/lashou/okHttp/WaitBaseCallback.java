package com.yicooll.dong.lashou.okHttp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.yicooll.dong.lashou.view.WaitDialog;

import java.io.IOException;

import okhttp3.Request;


/**
 * Created by Administrator on 2016/12/5 0005.
 */

public abstract class WaitBaseCallback<T> extends BaseCallback<T> {

    private Context mContext;
    private WaitDialog mDialog;
    private boolean canLoading;
    private Handler mhandler;


    public WaitBaseCallback(Context context, boolean canCancle, boolean canLoading) {
        this.mContext = context;
        this.canLoading = canLoading;
        mhandler=new Handler(Looper.getMainLooper());
        if (mContext != null) {
            mDialog = new WaitDialog(mContext);
            mDialog.setCancelable(canCancle);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mDialog.cancel();
                }
            });
        }

    }

    @Override
    public void onRequestBefore(Request request) {
        if (mDialog != null && canLoading && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {
        if (mDialog != null && canLoading && mDialog.isShowing()) {
            mDialog.cancel();
        }
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onResponse() {
        mDialog.dismiss();
    }

}
