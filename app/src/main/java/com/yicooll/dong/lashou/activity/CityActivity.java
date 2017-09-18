package com.yicooll.dong.lashou.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.adapter.CityAdapter;
import com.yicooll.dong.lashou.bean.City;
import com.yicooll.dong.lashou.bean.JSONResponse;
import com.yicooll.dong.lashou.http.Api;
import com.yicooll.dong.lashou.okHttp.BaseCallback;
import com.yicooll.dong.lashou.okHttp.OkhttpHelper;
import com.yicooll.dong.lashou.view.MySlideView;
import com.yicooll.dong.lashou.view.ToolbarX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class CityActivity extends BaseActivity implements MySlideView.onTouchListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_city)
    RecyclerView recyclerCity;
    @BindView(R.id.myslideView)
    MySlideView myslideView;
    @BindView(R.id.im_back)
    ImageView imBack;


    private CityAdapter mCityAdapter;
    private List<City> mCityList = new ArrayList<City>();
    private ToolbarX mToolbarX;
    public static List<String> firstPinYin = new ArrayList<String>();

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getCityInfo();
        mToolbarX = getToolbar();
        mToolbarX.hide();


        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        setAMapLocationOption();

    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {

            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    mCityAdapter.setCityLocation(aMapLocation.getCity());
                    Log.i("yicooll", "city" + "**********************************"+aMapLocation.getCity());
                    mCityAdapter.notifyDataSetChanged();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    public void setAMapLocationOption() {

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);


        //获取一次定位结果：
        //该方法默认为false。
        //mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    private void initView() {
        mCityAdapter = new CityAdapter(mCityList);
        recyclerCity.setAdapter(mCityAdapter);
        recyclerCity.setLayoutManager(new LinearLayoutManager(this));
        firstPinYin.clear();
        for (int i = 0; i < mCityList.size(); i++) {

            if (i > 0 && !mCityList.get(i).getSortkey().equals(mCityList.get(i - 1).getSortkey())) {
                firstPinYin.add(mCityList.get(i).getSortkey());
            } else if (i == 0) {
                firstPinYin.add(mCityList.get(0).getSortkey());
            }

        }
        myslideView.setListener(this);
        myslideView.invalidate();
    }


    @OnClick({R.id.im_back, R.id.im_refresh})
     public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                CityActivity.this.finish();
                break;
            case R.id.im_refresh:
                getCityInfo();
                mCityAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_city;
    }

    /**
     * 获取城市数据
     */
    public void getCityInfo() {

        OkhttpHelper.getIntance().get(Api.CITY_LIST, new BaseCallback<JSONResponse<List<City>>>() {


            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, JSONResponse<List<City>> data) {
                mCityList = data.getData();
                initView();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(CityActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
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

    @Override
    public void showText(String str, boolean dismiss) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();
    }
}
