package com.ruiaa.bottomnavigation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ruiaa on 2017/10/24.
 */

public class ViewPagerAdapterV13 extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ViewPagerAdapterV13(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
