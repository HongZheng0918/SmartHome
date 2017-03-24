package com.kotori.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kotori.smarthome.R;

import java.util.List;

/**
 * Created by kotori on 2017/2/21.
 * 管理gridview的适配器
 */
public class GridAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mTextDatas;
    private List<Integer> mImgDatas;

    // 创建构造方法
    public GridAdapter(Context c,List<String> text,List<Integer> img){
        mInflater = LayoutInflater.from(c);
        mContext = c;
        mTextDatas = text;
        mImgDatas = img;
    }
    @Override
    public int getCount() {
        return mTextDatas.size();
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
        // 定义一个ViewHolder容器
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item_view,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextview = (TextView) convertView.findViewById(R.id.tv_grid);
            viewHolder.mImageview = (ImageView) convertView.findViewById(R.id.img_grid);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextview.setText(mTextDatas.get(position));
        viewHolder.mImageview.setImageResource(mImgDatas.get(position));
        return convertView;
    }

    // 定义viewHolder类
    class ViewHolder{
        TextView mTextview;
        ImageView mImageview;

    }

}


