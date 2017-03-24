package com.kotori.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.kotori.smarthome.R;
import com.kotori.smarthome.activity.AirActivity;
import com.kotori.smarthome.activity.WarningActivity;
import com.kotori.smarthome.activity.SafeActivity;
import com.kotori.smarthome.activity.TempActivity;
import com.kotori.smarthome.adapter.GridAdapter;
import com.kotori.smarthome.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by kotori on 2017/2/20.
 * 家庭状态界面
 */
public class HomeFragment extends Fragment{

    private GridView mHomeGridView;
    private ArrayList<String> mTextDatas;
    private ArrayList<Integer> mImgDatas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initData();
        initView(view);
        return view;
    }



    // 实例化数据
    private void initData() {
        mTextDatas = new ArrayList<String>();
        mTextDatas.add("温度环境");
        mTextDatas.add("空气质量");
        mTextDatas.add("灾害预警");
        mTextDatas.add("安防预警");
        mImgDatas = new ArrayList<Integer>();
        mImgDatas.add(R.mipmap.temp);
        mImgDatas.add(R.mipmap.air);
        mImgDatas.add(R.mipmap.warning);
        mImgDatas.add(R.mipmap.safe);
    }

    private void initView(View view) {
        mHomeGridView = (GridView) view.findViewById(R.id.gv_home);
        GridAdapter adapter = new GridAdapter(view.getContext(),mTextDatas,mImgDatas);
        mHomeGridView.setAdapter(adapter);
        mHomeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int)id) {
                    case 0 :
                        ActivityUtil.startActivity(getActivity(), TempActivity.class,false);
                        break;
                    case 1 :
                        ActivityUtil.startActivity(getActivity(), AirActivity.class,false);
                        break;
                    case 2 :
                        ActivityUtil.startActivity(getActivity(), WarningActivity.class,false);
                        break;
                    case 3 :
                        ActivityUtil.startActivity(getActivity(), SafeActivity.class,false);
                        break;
                }

            }
        });
    }
}
