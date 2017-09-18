package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.adapter.GoodsAdapter;
import com.yicooll.dong.lashou.bean.Goods;
import com.yicooll.dong.lashou.bean.JSONResponse;
import com.yicooll.dong.lashou.bean.NearbyType;
import com.yicooll.dong.lashou.http.Api;
import com.yicooll.dong.lashou.okHttp.OkhttpHelper;
import com.yicooll.dong.lashou.okHttp.WaitBaseCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class NearbyGoodsActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.location_update)
    ProgressBar locationUpdate;
    private List<NearbyType> mTypeList = new ArrayList<NearbyType>();
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public static Double geolon, geolat;
    private String desc = "";
    private int categoryId;

    @BindView(R.id.nearby_goods)
    RecyclerView nearbyGoods;
    private List<Goods> mGoodsList = new ArrayList<Goods>();
    private GoodsAdapter mGoodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        ButterKnife.bind(this);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        Intent intent = getIntent();
        geolat = intent.getDoubleExtra("geolat", 0);
        geolon = intent.getDoubleExtra("geolon", 0);
        desc = intent.getStringExtra("desc");
        categoryId = intent.getIntExtra("category", 1);
        if (geolat == null || geolon == null) {
            setAMapLocationOption();
        } else {
            tvLocation.setText(desc);
            locationUpdate.setVisibility(View.GONE);
        }
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nearby_goods;
    }

    @OnClick(R.id.nearby_back)
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。

                Bundle bundle = aMapLocation.getExtras();

                if (bundle != null) {
                    desc = bundle.getString("desc");
                    tvLocation.setText(desc);
                    locationUpdate.setVisibility(View.GONE);
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

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

    private void loadData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", "1");
        map.put("size", "5");
        map.put("lat", String.valueOf(geolat));
        map.put("lon", String.valueOf(geolon));
        map.put("category", categoryId + "");
        map.put("radius", 1000 + "");
        OkhttpHelper.getIntance().post(Api.NEARBY_GODDS, map, new WaitBaseCallback<JSONResponse<List<Goods>>>(NearbyGoodsActivity.this, true, true) {
            @Override
            public void onSuccess(Response response, JSONResponse<List<Goods>> data) {
                mGoodsList = data.getData();
                if(mGoodsList!=null){
                    initView();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void initView() {
        mGoodsAdapter = new GoodsAdapter(this, mGoodsList);
        nearbyGoods.setAdapter(mGoodsAdapter);
        nearbyGoods.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
