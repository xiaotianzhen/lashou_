package com.yicooll.dong.lashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yicooll.dong.lashou.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.btn_guide)
    Button btnGuide;

    private int[] imges = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private List<ImageView> mImageViewList = new ArrayList<ImageView>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gide);
        ButterKnife.bind(this);
        initData();
        vpGuide.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                vpGuide.addView(mImageViewList.get(position));
                return mImageViewList.get(position);
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViewList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position==mImageViewList.size()-1){
                    btnGuide.setVisibility(View.VISIBLE);
                    Animation animatino= AnimationUtils.loadAnimation(GuideActivity.this,R.anim.anim_bt_guide_in);
                    btnGuide.startAnimation(animatino);
                }else {
                    btnGuide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {

        for (int i = 0; i < imges.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imges[i]);
            mImageViewList.add(image);
        }
    }

    @OnClick(R.id.btn_guide)
    public void onClick() {
        startActivity(new Intent(GuideActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }
}
