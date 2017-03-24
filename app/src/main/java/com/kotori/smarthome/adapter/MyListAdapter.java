package com.kotori.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kotori.smarthome.R;

/**
 * Created by kotori on 2017/2/28.
 * 系统设置界面适配器
 */
public class MyListAdapter extends BaseAdapter{

    private String[] mData;
    private Context mContext;
    public MyListAdapter(Context c, String[] data){
        this.mContext = c;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_mylist_view,parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.system_tv);
        tv.setText(mData[position]);
        return convertView;
    }
}
