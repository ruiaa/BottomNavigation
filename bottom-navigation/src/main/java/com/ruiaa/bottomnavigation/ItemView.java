package com.ruiaa.bottomnavigation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ruiaa on 2017/10/24.
 */
public class ItemView {
    public BottomBarView bottomBarView;
    public View itemView;
    public View active = null;
    public View inActive = null;
    public ImageView activeImg = null;
    public TextView activeText;
    public ImageView inActiveImg = null;
    public TextView inActiveText;
    public TextView dotView = null;
    public GradientDrawable dotBg;

    public ItemView(BottomBarView bottomBarView) {
        this.bottomBarView = bottomBarView;
        itemView = LayoutInflater.from(bottomBarView.getContext()).inflate(R.layout.bottom_bar_item, bottomBarView, false);
        setDotDefaultStyle();
    }

    /*
    *   设置活动与非活动item透明度
    * */
    public ItemView showActive(float percent) {
        active.setAlpha(percent);
        inActive.setAlpha(1 - percent);
        return this;
    }

    /*
    *   自定义item
    * */
    public ItemView setActiveLayout(@LayoutRes int layoutId) {
        if (active != null) return this;

        ViewStub viewStub = itemView.findViewById(R.id.bottom_bar_item_inActive);
        viewStub.setLayoutResource(layoutId);
        active = viewStub.inflate();
        active.setAlpha(0);
        return this;
    }

    public ItemView setInActiveLayout(@LayoutRes int layoutId) {
        if (inActive != null) return this;

        ViewStub viewStub = itemView.findViewById(R.id.bottom_bar_item_active);
        viewStub.setLayoutResource(layoutId);
        inActive = viewStub.inflate();
        inActive.setAlpha(1);
        return this;
    }

    /*
    *   默认item : img + text
    * */
    public ItemView setActive(String text, Drawable img) {
        if (active == null) {
            active = ((ViewStub) itemView.findViewById(R.id.bottom_bar_item_active)).inflate();
            active.setAlpha(0);
            activeImg = active.findViewById(R.id.bottom_bar_item_img);
            activeText = active.findViewById(R.id.bottom_bar_item_text);
            if (inActiveImg != null) setContentDefaultStyle();
        }

        if (img != null) activeImg.setImageDrawable(img);
        if (text != null) activeText.setText(text);
        return this;
    }

    public ItemView setActive(String text, @DrawableRes int img) {
        return setActive(text, BottomNavUtils.getDrawable(itemView.getContext(), img));
    }

    public ItemView setInActive(String text, Drawable img) {
        if (inActive == null) {
            inActive = ((ViewStub) itemView.findViewById(R.id.bottom_bar_item_inActive)).inflate();
            inActive.setAlpha(1);
            inActiveImg = inActive.findViewById(R.id.bottom_bar_item_img);
            inActiveText = inActive.findViewById(R.id.bottom_bar_item_text);
            if (activeImg != null) setContentDefaultStyle();
        }

        if (img != null) inActiveImg.setImageDrawable(img);
        if (text != null) inActiveText.setText(text);
        return this;
    }

    public ItemView setInActive(String text, @DrawableRes int img) {
        return setInActive(text, BottomNavUtils.getDrawable(itemView.getContext(), img));
    }

    public ItemView setContent(String text, Drawable activeImg, Drawable inActiveImg) {
        setActive(text, activeImg);
        setInActive(text, inActiveImg);
        return this;
    }

    public ItemView setContent(String text, @DrawableRes int activeImg, @DrawableRes int inActiveImg) {
        return setContent(text, BottomNavUtils.getDrawable(itemView.getContext(), activeImg), BottomNavUtils.getDrawable(itemView.getContext(), inActiveImg));
    }


    //text样式
    public ItemView setTextColor(@ColorInt int activeColor, @ColorInt int inActiveColor) {
        activeText.setTextColor(activeColor);
        inActiveText.setTextColor(inActiveColor);
        return this;
    }

    public ItemView setTextColorRes(@ColorRes int activeColor, @ColorRes int inActiveColor) {
        setTextColor(BottomNavUtils.getColor(itemView.getContext(), activeColor), BottomNavUtils.getColor(itemView.getContext(), inActiveColor));
        return this;
    }

    public ItemView setTextSize(float sizeSp) {
        activeText.setTextSize(sizeSp);
        inActiveText.setTextSize(sizeSp);
        return this;
    }

    //img样式
    public ItemView setImgSize(int width, int height) {
        ViewGroup.LayoutParams lp = activeImg.getLayoutParams();
        lp.width = width;
        lp.height = height;
        activeImg.setLayoutParams(lp);
        lp = inActiveImg.getLayoutParams();
        lp.width = width;
        lp.height = height;
        inActiveImg.setLayoutParams(lp);
        return this;
    }

    public ItemView setImgSizeInDp(int widthInDp, int heightInDp) {
        return setImgSize(BottomNavUtils.dp2px(itemView.getContext(), widthInDp), BottomNavUtils.dp2px(itemView.getContext(), heightInDp));
    }

    public ItemView setImgSizeRes(@DimenRes int width, @DimenRes int height) {
        return setImgSize(BottomNavUtils.getDimenInPx(itemView.getContext(), width), BottomNavUtils.getDimenInPx(itemView.getContext(), height));
    }

    public ItemView setImgSize(int px) {
        return setImgSize(px, px);
    }

    public ItemView setImgSizeInDp(int dp) {
        return setImgSizeInDp(dp, dp);
    }

    public ItemView setImgSizeRes(@DimenRes int dp) {
        return setImgSizeRes(dp, dp);
    }

    //位置
    public ItemView setTextImgDistance(int px) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) activeImg.getLayoutParams();
        lp.setMargins(0, 0, 0, px);
        activeImg.setLayoutParams(lp);
        lp = (ViewGroup.MarginLayoutParams) inActiveImg.getLayoutParams();
        lp.setMargins(0, 0, 0, px);
        inActiveImg.setLayoutParams(lp);
        return this;
    }

    public ItemView setTextImgDistanceInDp(int dp) {
        return setTextImgDistance(BottomNavUtils.dp2px(itemView.getContext(), dp));
    }

    public ItemView setTextImgDistanceRes(@DimenRes int dp) {
        return setTextImgDistance(BottomNavUtils.getDimenInPx(itemView.getContext(), dp));
    }

    private ItemView setContentDefaultStyle() {
        setTextColor(bottomBarView.textActiveColor, bottomBarView.textInActiveColor);
        setTextSize(bottomBarView.textSize);
        setImgSizeInDp(bottomBarView.imgSizeW, bottomBarView.imgSizeH);
        setTextImgDistanceInDp(bottomBarView.textImgDistance);
        return this;
    }

    /*
    *   小圆点
    * */
    //可见性
    public ItemView setDotVisible(boolean visible) {
        dotView.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ItemView setDotVisible() {
        return setDotVisible(true);
    }

    //位置
    public ItemView setDotOffset(int offsetTopPx, int offsetCenterHorizontalPx) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) dotView.getLayoutParams();
        lp.setMargins(offsetCenterHorizontalPx, offsetTopPx, 0, 0);
        dotView.setLayoutParams(lp);
        return this;
    }

    public ItemView setDotOffsetInDp(int offsetTopDp, int offsetCenterHorizontalDp) {
        return setDotOffset(BottomNavUtils.dp2px(itemView.getContext(), offsetTopDp), BottomNavUtils.dp2px(itemView.getContext(), offsetCenterHorizontalDp));
    }

    public ItemView setDotOffsetRes(@DimenRes int offsetTop, @DrawableRes int offsetCenterHorizontal) {
        return setDotOffset(BottomNavUtils.getDimenInPx(itemView.getContext(), offsetTop), BottomNavUtils.getDimenInPx(itemView.getContext(), offsetCenterHorizontal));
    }

    //背景颜色
    public ItemView setDotBgColor(@ColorInt int color) {
        dotBg.setColor(color);
        if (Build.VERSION.SDK_INT >= 16) {
            dotView.setBackground(dotBg);
        } else {
            dotView.setBackgroundDrawable(dotBg);
        }
        return this;
    }

    public ItemView setDotBgColorRes(@ColorRes int color) {
        return setDotBgColor(BottomNavUtils.getColor(itemView.getContext(), color));
    }

    //整体大小
    public ItemView setDotSize(int widthInPx, int heightInPx) {
        ViewGroup.LayoutParams lp = dotView.getLayoutParams();
        lp.width = widthInPx;
        lp.height = heightInPx;
        dotView.setLayoutParams(lp);
        return this;
    }

    public ItemView setDotSizeInDp(int widthInDp, int heightInDp) {
        return setDotSize(BottomNavUtils.dp2px(itemView.getContext(), widthInDp), BottomNavUtils.dp2px(itemView.getContext(), heightInDp));
    }

    public ItemView setDotSizeRes(@DimenRes int width, @DrawableRes int height) {
        return setDotSize(BottomNavUtils.getDimenInPx(itemView.getContext(), width), BottomNavUtils.getDimenInPx(itemView.getContext(), height));
    }

    public ItemView setDotSize(int px) {
        return setDotSize(px, px);
    }

    public ItemView setDotSizeInDp(int dp) {
        return setDotSize(BottomNavUtils.dp2px(itemView.getContext(), dp));
    }

    public ItemView setDotSizeRes(@DrawableRes int size) {
        return setDotSize(BottomNavUtils.getDimenInPx(itemView.getContext(), size));
    }

    //字体大小
    public ItemView setDotTextSize(float sizeInSp) {
        dotView.setTextSize(sizeInSp);
        return this;
    }

    public ItemView setDotTextSizeRes(@DimenRes int size) {
        return setDotTextSize(BottomNavUtils.getDimen(itemView.getContext(), size));
    }

    //字体颜色
    public ItemView setDotTextColor(@ColorInt int color) {
        dotView.setTextColor(color);
        return this;
    }

    public ItemView setDotTextColorRes(@ColorRes int color) {
        return setDotTextColor(BottomNavUtils.getColor(itemView.getContext(), color));
    }

    //内容
    public ItemView setDotText(String text) {
        dotView.setText(text);
        return this;
    }

    public ItemView setDotText(String text, int max, String replace) {
        if (text != null && text.length() > max) text = replace;
        return setDotText(text);
    }

    private void setDotDefaultStyle() {
        dotView = itemView.findViewById(R.id.bottom_bar_item_dot);
        dotBg = new GradientDrawable();
        dotBg.setShape(GradientDrawable.OVAL);
        dotBg.setSize(10, 10);
        setDotBgColor(bottomBarView.dotBgColor);
        setDotTextColor(bottomBarView.dotTextColor);
        setDotTextSize(bottomBarView.dotTextSize);
        setDotSizeInDp(bottomBarView.dotSizeW, bottomBarView.dotSizeH);
        setDotOffsetInDp(bottomBarView.dotOffsetTop, bottomBarView.dotOffsetCenter);
    }
}
