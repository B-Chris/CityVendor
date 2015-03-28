package com.application.ncg.cityvendorappsuite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.ncg.cityvendorappsuite.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

import java.util.List;

/**
 * Created by Chris on 2015-03-15.
 */
public class NotificationAdapter extends BaseAdapter{
    Context mCtx;
    List<VendorDTO> vendorList;

    public NotificationAdapter(Context mCtx, List<VendorDTO> vendor) {
        this.mCtx = mCtx;
        this.vendorList = vendor;
    }

    @Override
    public int getCount() {
        return vendorList.size();
    }

    @Override
    public Object getItem(int position) {
        return vendorList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if(convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vendor_item,viewGroup,false);
            holder.VI_txt = (TextView) convertView.findViewById(R.id.VI_txt);
            convertView.setTag(holder);
        } else{
            holder =(Holder) convertView.getTag();
        }
        VendorDTO message = vendorList.get(position);
        holder.VI_txt.setText((CharSequence) message);
        return convertView;
    }
    class Holder{
        TextView VI_txt;
    }
}
