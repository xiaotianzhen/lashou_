package com.yicooll.dong.lashou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.activity.GoodsDetailAcitivity;
import com.yicooll.dong.lashou.activity.NearbyGoodsActivity;
import com.yicooll.dong.lashou.bean.Goods;
import com.yicooll.dong.lashou.util.ToolKits;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 45990 on 2017/9/7.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHoler> {

    private Context mContext;
    private List<Goods> mGoodsList = new ArrayList<Goods>();

    public GoodsAdapter(Context context, List<Goods> goodsList) {
        mContext = context;
        mGoodsList = goodsList;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_goods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, final int position) {
        Glide.with(mContext).load(mGoodsList.get(position).getImgUrl()).centerCrop().into(holder.imGoodsIcon);
        holder.tvShortTitle.setText(mGoodsList.get(position).getSortTitle());
        holder.tvDetailDesc.setText(mGoodsList.get(position).getTitle());
        holder.tvPrice.setText(mGoodsList.get(position).getPrice());
        holder.tvValue.setText(mGoodsList.get(position).getValue());

        if (NearbyGoodsActivity.geolon != null && NearbyGoodsActivity.geolat != null) {

            double distance = ToolKits.DistanceOfTwoPoints(NearbyGoodsActivity.geolat, NearbyGoodsActivity.geolon,
                    Double.parseDouble(mGoodsList.get(position).getShop().getLat()), Double.parseDouble(mGoodsList.get(position).getShop().getLon()));
            if (distance > 1000) {
                holder.tvDistance.setText(distance / 1000 + "千米");
            } else {
                holder.tvDistance.setText(distance / 1000 + "米");
            }
        }
        holder.goodsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, GoodsDetailAcitivity.class).putExtra("goods",mGoodsList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        @BindView(R.id.im_goodsIcon)
        ImageView imGoodsIcon;
        @BindView(R.id.tv_shortTitle)
        TextView tvShortTitle;
        @BindView(R.id.tv_detailDesc)
        TextView tvDetailDesc;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.goods_item)
        LinearLayout goodsItem;

        public ViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
