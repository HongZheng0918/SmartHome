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
import com.kotori.smarthome.activity.DoorActivity;
import com.kotori.smarthome.activity.FanActivity;
import com.kotori.smarthome.activity.KeepActivity;
import com.kotori.smarthome.activity.LightActivity;
import com.kotori.smarthome.adapter.GridAdapter;
import com.kotori.smarthome.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by kotori on 2017/2/20.
 * 控制平台界面
 */
public class ControlFragment extends Fragment{

    private GridView mControlGridView;
    private ArrayList<String> mTextDatas;
    private ArrayList<Integer> mImgDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control,container,false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        mTextDatas = new ArrayList<String>();
        mTextDatas.add("灯光控制");
        mTextDatas.add("风扇控制");
        mTextDatas.add("门禁控制");
        mTextDatas.add("视频监控");
        mImgDatas = new ArrayList<Integer>();
        mImgDatas.add(R.mipmap.light);
        mImgDatas.add(R.mipmap.fan);
        mImgDatas.add(R.mipmap.door);
        mImgDatas.add(R.mipmap.keep);
    }

    private void initView(View view){
        mControlGridView = (GridView) view.findViewById(R.id.gv_control);
        GridAdapter adapter = new GridAdapter(view.getContext(),mTextDatas,mImgDatas);
        mControlGridView.setAdapter(adapter);
        mControlGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch ((int)id) {
                    case 0 :
                        ActivityUtil.startActivity(getActivity(), LightActivity.class,false);
                        break;
                    case 1 :
                        ActivityUtil.startActivity(getActivity(), FanActivity.class,false);
                        break;
                    case 2 :
                        ActivityUtil.startActivity(getActivity(), DoorActivity.class,false);
                        break;
                    case 3 :
                        ActivityUtil.startActivity(getActivity(), KeepActivity.class,false);
                        break;
                }

            }
        });
    }
}
