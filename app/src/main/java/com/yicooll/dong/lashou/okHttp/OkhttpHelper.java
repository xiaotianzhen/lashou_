package com.yicooll.dong.lashou.okHttp;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/12/4 0004.
 */

public class OkhttpHelper {

    private static OkhttpHelper okhttpHelper;
    private static OkHttpClient mClient;
    private Gson mGson;
    private Handler mHandler;


    private  OkhttpHelper() {
        mClient = new OkHttpClient();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public  static OkhttpHelper getIntance() {

        if (okhttpHelper == null) {
            okhttpHelper = new OkhttpHelper();
        }

        return okhttpHelper;
    }

    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, Methodtype.Get);
        doRequset(request, callback);
    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, Methodtype.Post);
        doRequset(request, callback);
    }

    public void postMultipartForm(String url, Param[] params, File[] files,BaseCallback callback) {
        Request request =buildMultipartFormRequest(url,params,files);
        doRequset(request, callback);
    }

    public void doRequset(Request request, final BaseCallback callback) {

        callback.onRequestBefore(request);
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call.request(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (callback.mType == String.class) {
                        successCallback(callback, response, result);

                    } else {
                        try {
                            Object object = mGson.fromJson(result, callback.mType);
                            successCallback(callback, response, object);
                        } catch (Exception e) {
                            errorCallback(callback, response, response.code(), e);
                        }
                    }
                } else {
                    errorCallback(callback, response, response.code(), null);
                }
            }
        });
    }

    public Request buildRequest(String url, Map<String, String> params, Methodtype type) {
        Request.Builder builder = new Request.Builder();
        if (type == Methodtype.Get) {
            builder.url(url).get();
        } else {
            RequestBody body = buildRequestBody(params);
            builder.url(url).post(body);
        }
        return builder.build();

    }

    public RequestBody buildRequestBody(Map<String, String> params) {
        FormBody.Builder build = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            build.add(entry.getKey(), entry.getValue());
        }
        return build.build();
    }

    public Request buildMultipartFormRequest(String url,Param[] params, File[] files) {


        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null) {
            for (Param params1 : params) {
                builder.addPart(Headers.of("Content-Disposition", "form-data;name=\"" + params1.key + "\""),
                        RequestBody.create(null, params1.value));
            }
        }
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                Log.i("520it", "************************"+filename);
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.setType(MultipartBody.FORM)
                        .addPart(Headers.of("Content-Disposition", "form-data;name=\"mfile\";filename=\"" + filename + "\""),
                                fileBody);
            }
        }

        Request request = new Request.Builder().url(url).post(builder.build()).build();

        return request;
    }

    public void successCallback(final BaseCallback callback, final Response response, final Object object) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, object);

            }
        });
    }

    public void errorCallback(final BaseCallback callback, final Response response, final int code, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, code, e);
            }
        });
    }


    enum Methodtype {
        Get,
        Post
    }


    public static class Param {
        String key;
        String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Param() {

        }
    }
}
