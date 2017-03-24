package com.kotori.smarthome.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.adapter.TabsAdapter;
import com.kotori.smarthome.fragment.ControlFragment;
import com.kotori.smarthome.fragment.HomeFragment;
import com.kotori.smarthome.fragment.SystemFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    // 定义一个布局
    private LayoutInflater layoutInflater;
    // 定义viewpager
    private ViewPager mViewPager;
    // 定义数组存放Fragment界面
    private Class fragmentArray[]={HomeFragment.class,ControlFragment.class,SystemFragment.class};
    // 定义数组存放
    private int mImageArray[]={R.drawable.tab_home_btn,R.drawable.tab_control_btn,R.drawable.tab_system_btn};
    // 定义模块名称
    private String mTextArray[]={"家庭状态","控制平台","系统设置"};
    private List<Fragment> list = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPage();
    }

    //初始化fragment
    private void initPage() {
        HomeFragment f1 = new HomeFragment();
        ControlFragment f2 = new ControlFragment();
        SystemFragment f3 = new SystemFragment();

        list.add(f1);
        list.add(f2);
        list.add(f3);

        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),list));
        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    //初始化View
    private void initView() {
        //实例化布局
        layoutInflater = LayoutInflater.from(this);
        //实例化viewpager对象
        mViewPager = (ViewPager) findViewById(R.id.pager);
        // 设置页面切换时的监听器
        mViewPager.addOnPageChangeListener(this);
        //绑定Tabhost和viewpager
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this,getSupportFragmentManager(),R.id.pager);
        //获取fragment的长度

        mTabHost.setOnTabChangedListener(this);
        int count = fragmentArray.length;
        Log.v("kotori",count+"");
        //遍历长度
        for(int i = 0; i < count; i++) {

            TabHost.TabSpec tabspec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabspec,fragmentArray[i],null);
        }

    }


    // 给Tab设置文字与图片的方法
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view,null);
        TextView tv = (TextView)view.findViewById(R.id.tv_tab);
        tv.setText(mTextArray[index]);
//        tv.setTextColor(android.graphics.Color.parseColor("#1296db"));
        ImageView img = (ImageView)view.findViewById(R.id.img_tab);
        img.setImageResource(mImageArray[index]);
        return view;
    }

    //表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //当前选中的页面位置Postion，这事件是在你页面跳转完毕的时候调用
    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        //设置View覆盖子类控件而直接获得焦点
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        //根据位置Postion设置当前的Tab
        mTabHost.setCurrentTab(position);
        //设置取消分割线
        widget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Tab改变的时候调用方法
    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        mViewPager.setCurrentItem(position);
    }
}
