package com.application.ncg.cityvendorappsuite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

/**
 * Created by Chris on 2015-04-03.
 */
public class VendorCardView extends ActionBarActivity {
    TextView VTN_textName, VTN_textCounter, VTN_textCounterLabel, VTN_textNumber;
    ImageView VTN_image;
    VendorDTO vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_card);

        VTN_image = (ImageView) findViewById(R.id.VTN_image);
        VTN_textName = (TextView) findViewById(R.id.VTN_textName);
        VTN_textCounter = (TextView) findViewById(R.id.VTN_textCounter);
        VTN_textCounterLabel = (TextView) findViewById(R.id.VTN_textCounterLabel);
       // VTN_textNumber = (TextView) findViewById(R.id.VTN_textNumber);

        //receive Bundle
      //Bundle b = this.getIntent().getBundleExtra("VendorBundle");
    //    String Name = b.
  //      String Counter = b.getString("counter");
  //      String CounterLabel = b.getString("counterLabel");
   //     String Number = b.getString("number");
    //    String Image = b.getString("Image");

        //setting Bundle items
      //  VTN_textName.setText(Name);
      //  VTN_textCounter.setText(Counter);
      //  VTN_textCounterLabel.setText(CounterLabel);
      //  VTN_textNumber.setText(Number);
       //VTN_image.setImageDrawable(getDrawable(R.drawable.ic_action_new_picture));


    }
}
