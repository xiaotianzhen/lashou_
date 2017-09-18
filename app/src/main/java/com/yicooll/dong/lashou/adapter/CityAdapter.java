package com.yicooll.dong.lashou.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 45990 on 2017/9/6.
 */

public class CityAdapter extends RecyclerView.Adapter {


    private List<City> mCityList = new ArrayList<City>();

    public CityAdapter(List<City> cityList) {
        mCityList = cityList;
    }

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITME = 1;
    private String cityLocation="";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==TYPE_HEAD){
            return new HeadViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city_head,parent,false));
        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_city_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==TYPE_HEAD){
            HeadViewHoler headViewHoler= (HeadViewHoler) holder;
            headViewHoler.tvLocation.setText(cityLocation);
        }else {
            City city = mCityList.get(position-1);
            ItemViewHolder itemViewHolder= (ItemViewHolder) holder;
            if (position == 1) {
                itemViewHolder.tvSortkey.setVisibility(View.VISIBLE);
                itemViewHolder.tvSortkey.setText(city.getSortkey());
            } else {
                if (mCityList.get(position-1).getSortkey().equals(mCityList.get(position - 2).getSortkey())) {
                    itemViewHolder.tvSortkey.setVisibility(View.GONE);
                } else {
                    itemViewHolder.tvSortkey.setVisibility(View.VISIBLE);
                    itemViewHolder.tvSortkey.setText(mCityList.get(position).getSortkey());
                }
            }
            itemViewHolder.tvCityname.setText(city.getName());
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {

            return TYPE_HEAD;
        }
        return TYPE_ITME;
    }

    @Override
    public int getItemCount() {
        return mCityList.size()+1;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sortkey)
        TextView tvSortkey;
        @BindView(R.id.tv_cityName)
        TextView tvCityname;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeadViewHoler extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_location)
        TextView tvLocation;
        public HeadViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
