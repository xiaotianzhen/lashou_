package com.yicooll.dong.lashou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.activity.CityActivity;
import com.yicooll.dong.lashou.activity.NearbyMapActivity;
import com.yicooll.dong.lashou.adapter.GoodsAdapter;
import com.yicooll.dong.lashou.bean.Goods;
import com.yicooll.dong.lashou.bean.JSONResponse;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    @BindView(R.id.recycler_goods)
    RecyclerView recyclerGoods;
    private List<Goods> mGoodsList=new ArrayList<Goods>();
    private GoodsAdapter mGoodsAdapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        loadData();

    }

    @OnClick({R.id.tv_city, R.id.im_map, R.id.im_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_city:
                startActivity(new Intent(getActivity(), CityActivity.class));
                break;
            case R.id.im_map:
                startActivity(new Intent(getActivity(), NearbyMapActivity.class));
                break;
            case R.id.im_search:
                break;
        }

    }

    private void loadData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", "1");
        map.put("size", "5");
        OkhttpHelper.getIntance().post(Api.GOODS_LIST, map, new WaitBaseCallback<JSONResponse<List<Goods>>>(getActivity(), true, true) {
            @Override
            public void onSuccess(Response response, JSONResponse<List<Goods>> data) {
                mGoodsList=data.getData();
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
        mGoodsAdapter=new GoodsAdapter(getActivity(),mGoodsList);
        recyclerGoods.setAdapter(mGoodsAdapter);
        recyclerGoods.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(getActivity()).unbind();
    }
}
