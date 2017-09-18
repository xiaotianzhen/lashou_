package com.yicooll.dong.lashou.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.Goods;
import com.yicooll.dong.lashou.bean.JSONResponse;
import com.yicooll.dong.lashou.bean.Shop;
import com.yicooll.dong.lashou.http.Api;
import com.yicooll.dong.lashou.okHttp.BaseCallback;
import com.yicooll.dong.lashou.okHttp.OkhttpHelper;
import com.yicooll.dong.lashou.view.ToolbarX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class NearbyMapActivity extends BaseActivity implements LocationSource, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,
        AMap.OnInfoWindowClickListener {

    @BindView(R.id.map)
    MapView mMapView;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener;
    private AMap aMap;
    private double lon = 113.32466;
    private double lat = 23.013994;
    private List<Goods> mGoodsList = new ArrayList<>();
    private boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getToolbar().hide();

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            // 设置定位监听
            aMap.setLocationSource(this);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            //绑定maker点击事件
            aMap.setOnMarkerClickListener(this);
            //设置自定义infoWindow样式
            aMap.setInfoWindowAdapter(this);
            aMap.setOnInfoWindowClickListener(this);
        }
        mMapView.onCreate(savedInstanceState);
    }


    @OnClick({R.id.im_back,R.id.im_updater})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.im_back:
                finish();
                break;
            case R.id.im_updater:
                loadData(lon, lat, 1000);
                break;

        }
    }

    /**
     * 加载附近的商家
     *
     * @param lon
     * @param lat
     * @param radius
     */

    private void loadData(final double lon, final double lat, int radius) {
        Map<String, String> map = new HashMap<>();
        map.put("lon", String.valueOf(lon));
        map.put("lat", String.valueOf(lat));
        map.put("radius", radius + "");
        OkhttpHelper.getIntance().post(Api.NEARBY_GODDS, map, new BaseCallback<JSONResponse<List<Goods>>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, JSONResponse<List<Goods>> data) {
                mGoodsList = data.getData();
                addMaker(mGoodsList);
                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lon), 35, 0, 0)));
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onResponse() {

            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nearby_map;
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {


            if (mListener != null && aMapLocation != null) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                      /*  lon = aMapLocation.getLongitude();
                        lat = aMapLocation.getLatitude();*/
                        if (!isLoad) {
                            loadData(lon, lat, 1000);
                            isLoad = true;
                        }
                        mListener.onLocationChanged(aMapLocation);

                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
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


    private void addMaker(List<Goods> goodsList) {

        for (Goods goods : goodsList) {
            Shop shop = goods.getShop();
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.parseDouble(shop.getLat()), Double.parseDouble(shop.getLon())));
            markerOption.title(shop.getName()).snippet("￥" + goods.getPrice());
            markerOption.draggable(false);//设置Marker可拖动
            //不同的商品类型设置不同的图标

            switch (Integer.parseInt(goods.getCategoryId())) {
                case 1:
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.ic_life)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    break;
                case 2:
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.ic_food)));
                    break;
                case 3:
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.ic_luck)));
                    break;
                case 4:
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.ic_hotel)));
                    break;
                case 5:
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.ic_movie)));
                    break;
            }
            markerOption.setFlat(true);//设置marker平贴地图效果
            aMap.addMarker(markerOption).setObject(goods);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
            setAMapLocationOption();
        }

    }

    @Override
    public void deactivate() {

        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        mMapView.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
