package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.bean.Goods;
import com.yicooll.dong.lashou.util.ToolKits;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailAcitivity extends BaseActivity {

    private Goods goods;
    @BindView(R.id.goods_icon)
    ImageView goodsIcon;
    @BindView(R.id.goods_title)
    TextView goodsTitle;
    @BindView(R.id.goods_desc)
    TextView goodsDesc;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_value)
    TextView tvGoodsValue;
    @BindView(R.id.shop_title)
    TextView shopTitle;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.shop_distance)
    TextView shopDistance;
    @BindView(R.id.shop_tel)
    TextView shopTel;
    @BindView(R.id.wv_goods_detial)
    WebView wvGoodsDetail;
    @BindView(R.id.wv_goods_warn)
    WebView wvGoodsWarn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().hide();
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goods = (Goods) bundle.get("goods");
        }
        if (goods != null) {

            //渲染商品信息
            Glide.with(this).load(goods.getImgUrl()).placeholder(R.drawable.icon_logo).into(goodsIcon);
            goodsTitle.setText(goods.getSortTitle());
            goodsDesc.setText(goods.getTitle());
            tvGoodsPrice.setText(goods.getPrice() + ".00");
            tvGoodsValue.setText(goods.getValue() + ".00");

            //渲染商家信息
            shopTitle.setText(goods.getShop().getName());
            shopAddress.setText(goods.getShop().getAddress());
            shopTel.setText(goods.getShop().getTel());

            if (NearbyGoodsActivity.geolon != null && NearbyGoodsActivity.geolat != null) {

                double distance = ToolKits.DistanceOfTwoPoints(NearbyGoodsActivity.geolat, NearbyGoodsActivity.geolon,
                        Double.parseDouble(goods.getShop().getLat()), Double.parseDouble(goods.getShop().getLon()));
                if (distance > 1000) {
                    shopDistance.setText(">" + Integer.parseInt(distance / 1000 + "") + "km");
                } else {
                    shopDistance.setText(distance / 1000 + "米");
                }
            } else {
                shopDistance.setText("");
            }

            //设置webview内容的宽度自适应屏幕的宽度
            WebSettings settings = wvGoodsDetail.getSettings();
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            WebSettings settings2 = wvGoodsWarn.getSettings();
            settings2.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            String[] data = htmlSub(goods.getDetail());

            if (data[0] != null)
                wvGoodsDetail.loadDataWithBaseURL("", data[0].toString(), "text/html", "utf-8", "");
            if (data[1] != null)
                wvGoodsWarn.loadDataWithBaseURL("", data[1].toString(), "text/html", "utf-8", "");

        }

    }


    public String[] htmlSub(String html) {
        char[] str = html.toCharArray();
        int len = str.length;
        int n = 0;
        String[] data = new String[3];
        int oneindex = 0;
        int secindex = 1;
        for (int i = 0; i < len; i++) {
            if (str[i] == '【') {
                n++;
                if (n == 1) oneindex = i;
                if (n == 2) secindex = i;
            }
        }

        if (oneindex > 0 && secindex > 1) {
            data[0] = html.substring(oneindex, secindex);
            data[1] = html.substring(secindex, len - 6);
        }
        return data;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @OnClick({R.id.detail_back, R.id.btn_buy, R.id.shop_call,})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.btn_buy:
                break;
            case R.id.shop_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + goods.getShop().getTel()));
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
