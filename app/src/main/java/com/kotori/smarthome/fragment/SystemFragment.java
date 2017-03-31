package com.kotori.smarthome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kotori.smarthome.R;
import com.kotori.smarthome.activity.LinkActivity;
import com.kotori.smarthome.activity.ParameterActivity;
import com.kotori.smarthome.activity.ServiceSwtichActivity;
import com.kotori.smarthome.adapter.MyListAdapter;
import com.kotori.smarthome.util.ActivityUtil;

/**
 * Created by kotori on 2017/2/20.
 * 家庭状态界面
 */
public class SystemFragment extends Fragment{

    private ListView mSystemlv;
    private String [] mData = {"连接测试","服务开关","参数配置","关于我们"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system,container,false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view) {
        mSystemlv = (ListView) view.findViewById(R.id.system_list);
        MyListAdapter adapter = new MyListAdapter(getActivity(),mData);
        mSystemlv.setAdapter(adapter);
        mSystemlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 :
                        ActivityUtil.startActivity(getActivity(), LinkActivity.class,false);
                        break;
                    case 1 :
                        ActivityUtil.startActivity(getActivity(), ServiceSwtichActivity.class,false);
                        break;
                    case 2 :
                        ActivityUtil.startActivity(getActivity(), ParameterActivity.class,false);
                }
            }
        });
    }
}
