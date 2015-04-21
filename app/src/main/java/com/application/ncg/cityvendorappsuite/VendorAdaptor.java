package com.application.ncg.cityvendorappsuite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    static int count;
    private Integer vendorID;

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
            convertView = inflater.inflate(R.layout.vendor_card, parent, false);
            h.title = (TextView) convertView.findViewById(R.id.title);
            h.VTN_image = (ImageView) convertView.findViewById(R.id.VTN_image);
            h.VTN_textName = (TextView) convertView.findViewById(R.id.VTN_textName);
            h.VTN_textCounter = (TextView) convertView.findViewById(R.id.VTN_textCounter);
            h.VTN_textCounterLabel = (TextView) convertView.findViewById(R.id.VTN_textCounterLabel);
            convertView.setTag(h);
        } else {
            h = (holder) convertView.getTag();
        }
        //String title = vendorList.get(position).getName();
       String name = vendorList.get(position).getName();
       String address = vendorList.get(position).getStreetAddress();


        //String image = vendorList.get(position).get


       //setting
      //  h.title.setText(title);
        h.VTN_textName.setText(name);
        h.VTN_textCounterLabel.setText(address);

        return convertView;
    }

    class holder{
        TextView title, VTN_textName, VTN_textCounterLabel, VTN_textCounter;
        ImageView VTN_image;
    }
}
