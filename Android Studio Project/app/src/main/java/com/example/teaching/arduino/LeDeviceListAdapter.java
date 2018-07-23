package com.example.teaching.arduino;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teaching on 7/24/2017.
 */


public class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> deviceArrayList;
    private Context context;
    private LayoutInflater mInflater;

    public LeDeviceListAdapter(Context context, ArrayList<BluetoothDevice> devices) {
        this.context = context;
        this.deviceArrayList = devices;
        mInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return deviceArrayList.size();
    }

    @Override
    public BluetoothDevice getItem(int position){
        return deviceArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_listview, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        BluetoothDevice currentListData = getItem(position);

        if (currentListData.getName()==null) {
            mViewHolder.tvname.setText("No name");
        }
        else{
            mViewHolder.tvname.setText(currentListData.getName());
        }
        mViewHolder.tvmac.setText(currentListData.getAddress());


        return convertView;
    }

    private class MyViewHolder {
        TextView tvname, tvmac;

        public MyViewHolder(View item) {
            tvname = (TextView) item.findViewById(R.id.name);
            tvmac = (TextView) item.findViewById(R.id.mac);

        }
    }

    public void clear() {
        deviceArrayList.clear();
    }

    public void addDevice (BluetoothDevice device) {
        if (!deviceArrayList.contains(device)) {
            deviceArrayList.add(device);
        }
    }
}