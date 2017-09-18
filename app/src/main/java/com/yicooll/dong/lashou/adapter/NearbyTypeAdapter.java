package com.yicooll.dong.lashou.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.NearbyType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 45990 on 2017/9/11.
 */

public class NearbyTypeAdapter extends BaseAdapter {

    private List<NearbyType> mTypeList = new ArrayList<NearbyType>();

    public NearbyTypeAdapter(List<NearbyType> list) {
        this.mTypeList = list;
    }

    @Override
    public int getCount() {
        return mTypeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            converView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_nearby_type_item,null);
            ButterKnife.bind(viewHolder, converView);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.tvTyepName.setText(mTypeList.get(position).getTypeName());
        int color=mTypeList.get(position).getNameColor();
        viewHolder.tvTyepName.setTextColor(mTypeList.get(position).getNameColor());
        Drawable drawable=viewGroup.getContext().getResources().getDrawable(mTypeList.get(position).getTypeIcon());
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        viewHolder.tvTyepName.setCompoundDrawables(null,drawable,null,null);
        return converView;
    }

    public class ViewHolder {

        @BindView(R.id.tv_typeName)
        TextView tvTyepName;

    }
}
