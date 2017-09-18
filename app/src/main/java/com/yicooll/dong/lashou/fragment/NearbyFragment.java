package com.yicooll.dong.lashou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.activity.NearbyGoodsActivity;
import com.yicooll.dong.lashou.adapter.NearbyTypeAdapter;
import com.yicooll.dong.lashou.bean.NearbyType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NearbyFragment extends Fragment implements AMapLocationListener {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.gridV)
    GridView gridV;
    @BindView(R.id.location_update)
    ProgressBar locationUpdate;

    private List<NearbyType> mTypeList = new ArrayList<NearbyType>();
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private Double geolat;
    private Double geolon;
    private String desc = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        setAMapLocationOption();
        mTypeList.clear();
        initData();
        gridV.setAdapter(new NearbyTypeAdapter(mTypeList));
        gridV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //模拟器测试数据
                geolon = 113.32466;
                geolat = 23.013994;
                int categoryId = mTypeList.get(position).getCategoryId();
                if (geolat != null && geolon != null) {

                    startActivity(new Intent(getActivity(), NearbyGoodsActivity.class)
                            .putExtra("geolat", geolat).putExtra("geolon", geolon).putExtra("desc", desc)
                            .putExtra("category", categoryId));
                } else {
                    Toast.makeText(getActivity(), "定位失败，请重试后再查看分类信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {

        NearbyType type = new NearbyType();
        type.setTypeName("美食");
        type.setNameColor(R.color.foodText);
        type.setTypeIcon(R.drawable.food);
        type.setCategoryId(1);
        mTypeList.add(type);
        NearbyType type1 = new NearbyType();
        type1.setTypeName("电影");
        type1.setNameColor(R.color.movieText);
        type1.setTypeIcon(R.drawable.movie);
        type1.setCategoryId(2);
        mTypeList.add(type1);
        NearbyType type2 = new NearbyType();
        type2.setTypeName("酒店");
        type2.setNameColor(R.color.movieText);
        type2.setTypeIcon(R.drawable.hotel);
        type2.setCategoryId(3);
        mTypeList.add(type2);
        NearbyType type3 = new NearbyType();
        type3.setTypeName("ktv");
        type3.setNameColor(R.color.movieText);
        type3.setTypeIcon(R.drawable.ktv);
        type3.setCategoryId(4);
        mTypeList.add(type3);
        NearbyType type4 = new NearbyType();
        type4.setTypeName("火锅");
        type4.setNameColor(R.color.foodText);
        type4.setTypeIcon(R.drawable.huoguo);
        type4.setCategoryId(5);
        mTypeList.add(type4);
        NearbyType type5 = new NearbyType();
        type5.setTypeName("美容美发");
        type5.setNameColor(R.color.meirongmeifa);
        type5.setTypeIcon(R.drawable.meirongmrifa);
        type5.setCategoryId(6);
        mTypeList.add(type5);
        NearbyType type6 = new NearbyType();
        type6.setTypeName("休闲娱乐");
        type6.setNameColor(R.color.movieText);
        type6.setTypeIcon(R.drawable.xiuxianyule);
        type6.setCategoryId(7);
        mTypeList.add(type6);
        NearbyType type7 = new NearbyType();
        type7.setTypeName("生活服务");
        type7.setNameColor(R.color.meirongmeifa);
        type7.setTypeIcon(R.drawable.liftserver);
        type7.setCategoryId(8);
        mTypeList.add(type7);
        NearbyType type8 = new NearbyType();
        type8.setTypeName("全部");
        type8.setNameColor(R.color.all);
        type8.setTypeIcon(R.drawable.all);
        type8.setCategoryId(9);
        mTypeList.add(type8);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(getActivity()).unbind();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                geolat = aMapLocation.getLatitude();
                geolon = aMapLocation.getLongitude();
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
}
