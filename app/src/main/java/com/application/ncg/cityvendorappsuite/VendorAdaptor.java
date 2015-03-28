package com.application.ncg.cityvendorappsuite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

import java.util.List;

/**
 * Created by Chris on 2015-03-28.
 */
public class VendorAdaptor extends BaseAdapter {

    Context mCtx;
    List<String>mList;
    List<VendorDTO> vendorList;

    public VendorAdaptor(Context mCtx, List<VendorDTO> vendorList){
        this.mCtx = mCtx;
        this.vendorList = vendorList;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h;
        //code to recycle the views

        if(convertView == null){
            h = new holder();
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vendor_item, parent, false);
            h.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(h);
        } else {
            h = (holder) convertView.getTag();
        }
        String title = vendorList.get(position).getName();
        h.title.setText(title);
        return convertView;
    }

    class holder{
        TextView title;
    }
}
