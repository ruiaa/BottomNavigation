package com.ruiaa.bottomnavigation;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by ruiaa on 2017/10/21.
 */

public class ScrollFrameView extends ViewPager {

    private BottomBarView bottomBarView = null;
    private boolean isSelectByBar = false;

    public ScrollFrameView(Context context) {
        super(context);
    }

    public ScrollFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollFrameView setFragmentList(android.app.FragmentManager fm, List<android.app.Fragment> list) {
        setAdapter(new ViewPagerAdapterV13(fm, list));
        addOnPageChangeListener(new OnChangeListener());
        return this;
    }

    public ScrollFrameView setFragmentList(android.support.v4.app.FragmentManager fm, List<android.support.v4.app.Fragment> list){
        setAdapter(new ViewPagerAdapterV4(fm,list));
        addOnPageChangeListener(new OnChangeListener());
        return this;
    }

    public ScrollFrameView setBottomBarView(BottomBarView bottomBarView) {
        this.bottomBarView = bottomBarView;
        bottomBarView.setScrollFrameView(this);
        setOffscreenPageLimit(bottomBarView.getItemSize() - 1);
        return this;
    }

    @Override
    public void setCurrentItem(int item) {
        isSelectByBar = true;
        super.setCurrentItem(item,false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        isSelectByBar = true;
        super.setCurrentItem(item, smoothScroll);
    }

    public class OnChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffsetPixels == 0) return;
            //LogUtil.i("onPageScrolled####click " + position + "   current " + getCurrentItem() + "    offset" + positionOffset);
            //打开右边offset:0->1  ,  打开左边offset:1->0(0->-1)

                bottomBarView.onScroll(position == bottomBarView.getSelect() ? positionOffset : positionOffset - 1);
        }

        @Override
        public void onPageSelected(int position) {
            bottomBarView.setSelect(getCurrentItem());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == SCROLL_STATE_IDLE) {

                bottomBarView.refreshAlpha();
            }
        }
    }
}
