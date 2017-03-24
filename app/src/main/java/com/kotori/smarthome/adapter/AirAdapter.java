package com.kotori.smarthome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotori.smarthome.R;
import com.kotori.smarthome.bean.Pm25City;
import com.kotori.smarthome.holder.Pm25ViewHolder;

import java.util.List;

/**
 * Created by kotori on 2017/3/15.
 * 空气质量模块适配器
 */
public class AirAdapter extends RecyclerView.Adapter<Pm25ViewHolder>{

    private Context mContext;
    private List<Pm25City> mDataList;
    private LayoutInflater mLayoutInflater;

    public AirAdapter(Context context,List<Pm25City> datalist){
        this.mContext = context;
        this.mDataList = datalist;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Pm25ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recy_item_air_view,parent,false);
        Pm25ViewHolder viewHolder = new Pm25ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Pm25ViewHolder holder, int position) {
        Pm25City pm = mDataList.get(position);
        holder.tv_air_local.setText(pm.getPosition_name());
        if(pm.getPosition_name()!=null) {
            if(pm.getPosition_name().equals("")) {
                holder.tv_local_rubbish.setText("无");
            }
            else{
                holder.tv_air_local.setText(pm.getPosition_name());
            }
        }
        if(pm.getQuality()!=null) {
            if(pm.getQuality().equals("优")) {
                holder.tv_local_test.setTextColor(Color.GREEN);
                holder.tv_local_test.setText(pm.getQuality());
            }
            else if(pm.getQuality().equals("良")) {
                holder.tv_local_test.setTextColor(Color.YELLOW);
                holder.tv_local_test.setText(pm.getQuality());
            }
            else if(pm.getQuality().equals("")) {
                holder.tv_local_test.setText("无");
            }
            else{
                holder.tv_local_test.setTextColor(Color.RED);
                holder.tv_local_test.setText(pm.getQuality());
            }
        }
        else{
            holder.tv_local_test.setText(pm.getQuality());
        }

        holder.tv_local_aqi.setText(pm.getAqi());
        holder.tv_local_pm25.setText(pm.getPm2_5());
        holder.tv_local_rubbish.setText(pm.getPrimary_pollutant());
        if(pm.getPrimary_pollutant()!=null) {
            if(pm.getPrimary_pollutant().equals("")) {
                holder.tv_local_rubbish.setText("无");
            }
            else{
                holder.tv_local_rubbish.setText(pm.getPrimary_pollutant());
            }
        }
        else{
            holder.tv_local_rubbish.setText("无");
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
