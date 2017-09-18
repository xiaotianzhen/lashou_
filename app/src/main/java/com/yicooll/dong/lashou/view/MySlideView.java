package com.yicooll.dong.lashou.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.yicooll.dong.lashou.R;
import com.yicooll.dong.lashou.activity.CityActivity;
import com.yicooll.dong.lashou.activity.CityActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by luo on 2017/5/23.
 */

public class MySlideView extends View {
    private int mTouchSlop;
    private Rect mBound;
    private Paint mPaint;
    private int backgroupColor;
    private int mWidth;
    private int mHeight;
    private int defHeight = 600;
    private int defWidth = 100;
    private int mTextHeight = 20;
    private int downY;
    private int moveY;
    private boolean isSlide = false;
    protected int position;  //当前的位置
    private String selectTxt;
    private onTouchListener mListener;

    public void setListener(onTouchListener listener) {
        mListener = listener;
    }

    public MySlideView(Context context) {
        this(context, null);
    }

    public MySlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void initPaint() {

        mBound = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        backgroupColor = getResources().getColor(R.color.gray);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth != MeasureSpec.EXACTLY) {
            if (modeHeight == MeasureSpec.EXACTLY) {
                mWidth = defWidth;
                mHeight = sizeHeight;
            } else {
                mWidth = defWidth;
                mHeight = defHeight;
            }
        }

        if (modeWidth == MeasureSpec.EXACTLY) {
            if (modeHeight != MeasureSpec.EXACTLY) {
                mWidth = sizeWidth;
                mHeight = defHeight;
            } else {
                mWidth = sizeWidth;
                mHeight = sizeHeight;
            }
        }
        if (CityActivity.firstPinYin.size() != 0) {
            mTextHeight = mHeight / CityActivity.firstPinYin.size();
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = y;
                int dy = moveY - downY;
                //如果是竖直方向滑动
                if (Math.abs(dy) > mTouchSlop) {
                    isSlide = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                backgroupColor = getResources().getColor(R.color.darkgray);
                mTextHeight = mHeight / CityActivity.firstPinYin.size();
                position = y / (mHeight / (CityActivity.firstPinYin.size() + 1));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSlide) {
                    backgroupColor = getResources().getColor(R.color.darkgray);
                    mTextHeight = mHeight / CityActivity.firstPinYin.size();
                    position = y / (mHeight / CityActivity.firstPinYin.size() + 1) + 1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                backgroupColor = getResources().getColor(R.color.gray);
                mTextHeight = mHeight / CityActivity.firstPinYin.size();
                position = 0;
                invalidate();
                mListener.showText(selectTxt, true);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(backgroupColor);
        canvas.drawRect(0, 0, (float) mWidth, mHeight, mPaint);
        for (int i = 0; i < CityActivity.firstPinYin.size(); i++) {
            String textView = CityActivity.firstPinYin.get(i);
            if (i == position - 1) {
                mPaint.setColor(getResources().getColor(R.color.colorAccent));
                selectTxt = CityActivity.firstPinYin.get(i);
                mListener.showText(selectTxt, false);
            } else {
                mPaint.setColor(getResources().getColor(R.color.white));
            }
            mPaint.setTextSize(40);
            mPaint.getTextBounds(textView, 0, textView.length(), mBound);
            canvas.drawText(textView, (mWidth - mBound.width()) * 1 / 2, mTextHeight - mBound.height(), mPaint);
            mTextHeight += mHeight / CityActivity.firstPinYin.size();

        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility != VISIBLE) {
            if (CityActivity.firstPinYin.size() > 0) {
                mTextHeight = mHeight / CityActivity.firstPinYin.size();
            }
        }
    }

    public interface onTouchListener {
        void showText(String str, boolean dismiss);
    }
}
