package com.kotori.smarthome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by kotori on 2017/2/22.
 * viewpager的适配器
 */
public class TabsAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }
    // 构造方法，方便赋值
    public TabsAdapter(FragmentManager fm,List<Fragment> f) {
        super(fm);
        this.mFragments = f;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
