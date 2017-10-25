package com.ruiaa.bottomnavigation;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by ruiaa on 2017/10/20.
 */

public class BottomBarView extends LinearLayout {

    private ScrollFrameView scrollFrameView = null;
    private ArrayList<ItemView> itemViews = new ArrayList<>();
    private int select = 0;
    private OnSelectListener onSelectListener = null;

    public BottomBarView(Context context) {
        super(context);
    }

    public BottomBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemView getItem(int index) {
        return index < itemViews.size() ? itemViews.get(index) : null;
    }

    public int getSelect() {
        return select;
    }

    public BottomBarView setSelect(int select) {
        return setSelect(select, true);
    }

    public BottomBarView setSelect(int select, boolean setAlpha) {
        for (int i = 0; i < itemViews.size(); i++) {
            if (i == select) {
                if (setAlpha) itemViews.get(select).showActive(1);
            } else if (i == this.select) {
                if (setAlpha) itemViews.get(this.select).showActive(0);
            } else {
                itemViews.get(i).showActive(0);
            }
        }
        this.select = select;
        if (onSelectListener != null) onSelectListener.onSelect(select);
        return this;
    }

    public BottomBarView setSelectWithFrame(int select) {
        scrollFrameView.setCurrentItem(select, false);
        return this;
    }

    public BottomBarView refreshAlpha() {
        for (int i = 0; i < itemViews.size(); i++) {
            if (i == select) {
                itemViews.get(i).showActive(1);
            } else {
                itemViews.get(i).showActive(0);
            }
        }
        return this;
    }

    public BottomBarView setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
        return this;
    }

    public BottomBarView init() {
        setSelect(select);
        return this;
    }

    public BottomBarView onScroll(float percent) {
        if (percent == 0) return this;
        if (percent > 0 && itemViews.size() > select + 1)
            itemViews.get(select + 1).showActive(Math.abs(percent));
        else if (percent < 0 && select - 1 >= 0)
            itemViews.get(select - 1).showActive(Math.abs(percent));
        itemViews.get(select).showActive(1 - Math.abs(percent));
        return this;
    }

    public BottomBarView addItem(ItemView itemView) {
        addView(itemView.itemView);
        itemViews.add(itemView);
        itemView.itemView.setOnClickListener(new OnClickListener() {
            final int position = itemViews.size() - 1;

            @Override
            public void onClick(View v) {
                if (scrollFrameView != null) {
                    scrollFrameView.setCurrentItem(position, false);
                } else {
                    setSelect(position);
                }
            }
        });
        return this;
    }

    public int getItemSize() {
        return itemViews.size();
    }

    public BottomBarView setScrollFrameView(ScrollFrameView scrollFrameView) {
        this.scrollFrameView = scrollFrameView;
        return this;
    }


    /*
    *       itemView 默认配置
    * */
    public int textActiveColor = 0xff1296db;
    public int textInActiveColor = 0xff707070;
    public int textSize = 12;//sp
    public int imgSizeW = 32;//dp
    public int imgSizeH = 32;//dp
    public int textImgDistance = 2;//dp

    public int dotSizeW = 16;//dp
    public int dotSizeH = 16;//dp
    public int dotBgColor = 0xffff4444;
    public int dotTextSize = 10;//sp
    public int dotTextColor = 0xffffffff;
    public int dotOffsetTop = 4;//dp
    public int dotOffsetCenter = 12;//dp

    public BottomBarView setTextColor(@ColorInt int activeColor, @ColorInt int inActiveColor) {
        textActiveColor = activeColor;
        textInActiveColor = inActiveColor;
        return this;
    }

    public BottomBarView setTextColorRes(@ColorRes int activeColor, @ColorRes int inActiveColor) {
        textActiveColor = BottomNavUtils.getColor(getContext(), activeColor);
        textInActiveColor = BottomNavUtils.getColor(getContext(), inActiveColor);
        return this;
    }

    public BottomBarView setTextSize(int sizeSp) {
        textSize = sizeSp;
        return this;
    }

    public BottomBarView setTextSizeRes(@DimenRes int size) {
        textSize = (int) BottomNavUtils.getDimen(getContext(), size);
        return this;
    }

    public BottomBarView setImgSize(int widthDp, int heightDp) {
        imgSizeW = widthDp;
        imgSizeH = heightDp;
        return this;
    }

    public BottomBarView setImgSizeRes(@DimenRes int w, @DimenRes int h) {
        imgSizeW = (int) BottomNavUtils.getDimen(getContext(), w);
        imgSizeH = (int) BottomNavUtils.getDimen(getContext(), h);
        return this;
    }

    public BottomBarView setImgSize(int dp) {
        return setImgSize(dp, dp);
    }

    public BottomBarView setImgSizeRes(@DimenRes int size) {
        return setImgSizeRes(size, size);
    }

    public BottomBarView setTextImgDistance(int dp) {
        textImgDistance = dp;
        return this;
    }

    public BottomBarView setTextImgDistanceRes(@DimenRes int distance) {
        textImgDistance = (int) BottomNavUtils.getDimen(getContext(), distance);
        return this;
    }

    public BottomBarView setDotSize(int widthDp, int heightDp) {
        dotSizeW = widthDp;
        dotSizeH = heightDp;
        return this;
    }

    public BottomBarView setDotSizeRes(@DimenRes int w, @DimenRes int h) {
        dotSizeW = (int) BottomNavUtils.getDimen(getContext(), w);
        dotSizeH = (int) BottomNavUtils.getDimen(getContext(), h);
        return this;
    }

    public BottomBarView setDotSize(int dp) {
        dotSizeW = dp;
        dotSizeH = dp;
        return this;
    }

    public BottomBarView setDotSizeRes(@DimenRes int size) {
        return setDotSize(size, size);
    }

    public BottomBarView setDotTextSize(int sp) {
        this.dotTextSize = sp;
        return this;
    }

    public BottomBarView setDotTextSizeRes(@DimenRes int size) {
        dotTextSize = (int) BottomNavUtils.getDimen(getContext(), size);
        return this;
    }

    public BottomBarView setDotTextColor(int color) {
        this.dotTextColor = color;
        return this;
    }

    public BottomBarView setDotTextColorRes(@ColorRes int color) {
        this.dotTextColor = BottomNavUtils.getColor(getContext(), color);
        return this;
    }

    public BottomBarView setDotBgColor(int color) {
        this.dotBgColor = color;
        return this;
    }

    public BottomBarView setDotBgColorRes(@ColorRes int color) {
        this.dotBgColor = BottomNavUtils.getColor(getContext(), color);
        return this;
    }

    public BottomBarView setDotOffset(int topDp, int centerDp) {
        this.dotOffsetTop = topDp;
        this.dotOffsetCenter = centerDp;
        return this;
    }

    public BottomBarView setDotOffsetRes(@DimenRes int topDp, @DimenRes int centerDp) {
        this.dotOffsetTop = (int) BottomNavUtils.getDimen(getContext(), topDp);
        this.dotOffsetCenter = (int) BottomNavUtils.getDimen(getContext(), centerDp);
        return this;
    }
}
