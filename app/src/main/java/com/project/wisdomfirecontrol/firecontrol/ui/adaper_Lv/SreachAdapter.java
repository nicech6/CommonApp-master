package com.project.wisdomfirecontrol.firecontrol.ui.adaper_Lv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.project.wisdomfirecontrol.R;

import java.util.List;

public class SreachAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<PoiInfo> infos;

    public SreachAdapter(Context context, List<PoiInfo> infos) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sreach_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.addressName.setText(infos.get(position).name);
        holder.addressDetails.setText(infos.get(position).address);
//        MyApp.Location_End = infos.get(position).name;
//        MyApp.Sreach_City = infos.get(position).city;
//        MyApp.Sreach_Latitude = infos.get(position).location.latitude;
//        MyApp.Sreach_Longitude = infos.get(position).location.longitude;
        return convertView;
    }

    static class ViewHolder {
        TextView addressName;
        TextView addressDetails;


        ViewHolder(View view) {
            addressName = view.findViewById(R.id.address_name);
            addressDetails = view.findViewById(R.id.address_details);
        }
    }
}
//jhfghfh