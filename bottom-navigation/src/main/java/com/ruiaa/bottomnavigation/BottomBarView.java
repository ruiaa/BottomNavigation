package com.ruiaa.bottomnavigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        return setSelect(select,true);
    }

    public BottomBarView setSelect(int select, boolean setAlpha) {
        for(int i=0;i<itemViews.size();i++){
            if (i==select){
                if (setAlpha) itemViews.get(select).showActive(1);
            }else if (i==this.select){
                if (setAlpha) itemViews.get(this.select).showActive(0);
            }else {
                itemViews.get(i).showActive(0);
            }
        }
        this.select = select;
        if (onSelectListener != null) onSelectListener.onSelect(select);
        return this;
    }

    public BottomBarView setSelectWithFrame(int select){
        scrollFrameView.setCurrentItem(select,false);
        return this;
    }

    public BottomBarView refreshAlpha(){
        for(int i=0;i<itemViews.size();i++){
            if (i==select){
                itemViews.get(i).showActive(1);
            }else {
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
            final int position=itemViews.size() - 1;
            @Override
            public void onClick(View v) {
                if (scrollFrameView != null) {
                    scrollFrameView.setCurrentItem(position,false);
                }else {
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
    public int activeTextColor=0xff1296db;
    public int inActiveTextColor=0xff707070;
    public int dotTextColor=0xffffff;
    public int dotBgColor=0xffff4444;

    public static class ItemView {
        private BottomBarView bottomBarView;
        private View itemView;
        private View active = null;
        private View inActive = null;
        private ImageView activeImg;
        private TextView activeText;
        private ImageView inActiveImg;
        private TextView inActiveText;
        private TextView dotView = null;

        public ItemView(BottomBarView bottomBarView) {
            this.bottomBarView=bottomBarView;
            itemView = LayoutInflater.from(bottomBarView.getContext()).inflate(R.layout.bottom_bar_item, bottomBarView, false);
            dotView = itemView.findViewById(R.id.bottom_bar_item_dot);
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
        public ItemView setContent(String text, Drawable activeImg, Drawable inActiveImg) {
            setActive(text, activeImg);
            setInActive(text, inActiveImg);
            return this;
        }

        public ItemView setContent(String text, @DrawableRes int activeImg, @DrawableRes int inActiveImg) {
            return setContent(text, getDrawable(activeImg), getDrawable(inActiveImg));
        }

        public ItemView setActive(String text, Drawable img) {
            if (active == null) {
                active = ((ViewStub) itemView.findViewById(R.id.bottom_bar_item_active)).inflate();
                active.setAlpha(0);
                activeImg = active.findViewById(R.id.bottom_bar_item_img);
                activeText = active.findViewById(R.id.bottom_bar_item_text);
                activeText.setTextColor(bottomBarView.activeTextColor);
            }

            if (img != null) activeImg.setImageDrawable(img);
            if (text != null) activeText.setText(text);
            return this;
        }

        public ItemView setActive(String text, @DrawableRes int img) {
            return setActive(text, getDrawable(img));
        }

        public ItemView setInActive(String text, Drawable img) {
            if (inActive == null) {
                inActive = ((ViewStub) itemView.findViewById(R.id.bottom_bar_item_inActive)).inflate();
                inActive.setAlpha(1);
                inActiveImg = inActive.findViewById(R.id.bottom_bar_item_img);
                inActiveText = inActive.findViewById(R.id.bottom_bar_item_text);
                inActiveText.setTextColor(bottomBarView.inActiveTextColor);
            }

            if (img != null) inActiveImg.setImageDrawable(img);
            if (text != null) inActiveText.setText(text);
            return this;
        }

        public ItemView setInActive(String text, @DrawableRes int img) {
            return setInActive(text, getDrawable(img));
        }

        public ItemView setTextColor(@ColorInt int activeColor, @ColorInt int inActiveColor) {
            activeText.setTextColor(activeColor);
            inActiveText.setTextColor(inActiveColor);
            return this;
        }

        public ItemView setTextColorRes(@ColorRes int activeColor, @ColorRes int inActiveColor) {
            setTextColor(getColor(activeColor),getColor(inActiveColor));
            return this;
        }

        public ItemView setImgSize(int px) {
            ViewGroup.LayoutParams lp = activeImg.getLayoutParams();
            lp.width = lp.height = px;
            lp = inActiveImg.getLayoutParams();
            lp.width = lp.height = px;
            return this;
        }

        public ItemView setTextSize(float size){
            activeText.setTextSize(size);
            inActiveText.setTextSize(size);
            return this;
        }

        /*
        *   设置活动与非活动item透明度
        * */
        private ItemView showActive(float percent) {
            active.setAlpha(percent);
            inActive.setAlpha(1 - percent);
            return this;
        }

        /*
        *   小圆点
        * */
        public ItemView setDotVisible(boolean visible) {
            dotView.setVisibility(visible ? VISIBLE : GONE);
            return this;
        }

        public ItemView setDotVisible() {
            return setDotVisible(true);
        }

        public ItemView setDotSize(int widthInPx, int heightInPx) {
            ViewGroup.LayoutParams lp = dotView.getLayoutParams();
            lp.width = widthInPx;
            lp.height = heightInPx;
            dotView.setLayoutParams(lp);
            return this;
        }

        public ItemView setDotSize(int px) {
            return setDotSize(px, px);
        }

        public ItemView setDotTextSize(float size) {
            dotView.setTextSize(size);
            return this;
        }

        public ItemView setDotText(String text) {
            dotView.setText(text);
            return this;
        }

        public ItemView setDotText(String text, int max, String replace) {
            if (text != null && text.length() > max) text = replace;
            return setDotText(text);
        }

        public ItemView setDotOffset(int offsetTop, int offsetCenterHorizontal) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) dotView.getLayoutParams();
            lp.setMargins(offsetCenterHorizontal, offsetTop, 0, 0);
            return this;
        }

        public View getItemView() {
            return itemView;
        }

        public View getActive() {
            return active;
        }

        public View getInActive() {
            return inActive;
        }

        private Drawable getDrawable(@DrawableRes int drawableFromR) {
            if (Build.VERSION.SDK_INT >= 21) {
                return itemView.getContext().getResources().getDrawable(drawableFromR, null);
            } else {
                return itemView.getContext().getResources().getDrawable(drawableFromR);
            }
        }

        private int getColor(@ColorRes int colorFromR) {
            if (Build.VERSION.SDK_INT >= 23) {
                return itemView.getContext().getResources().getColor(colorFromR, null);
            } else {
                return itemView.getContext().getResources().getColor(colorFromR);
            }
        }
    }

    public static interface OnSelectListener {
        public void onSelect(int position);
    }
}
