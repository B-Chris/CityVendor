package com.application.ncg.cityvendorappsuite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.ncg.cityvendorappsuite.providers.VendorsContentProviderUtils;
import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.util.bean.Util;

public class VendorActivity extends Activity {

    public static final String LOCATION_KEY = "location";

    Button btn_submit;
    TextView tvtName;
    TextView txtSurname;
    TextView txtEmail;
    TextView txtAddress;
    TextView txtLat;
    TextView txtLon;
    ImageView HEAD_image, V_img;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vendor_activity);
        context = this;
        HEAD_image = (ImageView) findViewById(R.id.HEAD_img);
        V_img = (ImageView) findViewById(R.id.V_img);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tvtName = (TextView) findViewById(R.id.editName);
        txtSurname = (TextView) findViewById(R.id.editSurname);
        txtEmail = (TextView) findViewById(R.id.editEmail);
        txtAddress = (TextView) findViewById(R.id.editStreetAddress);

      //  txtLat = (TextView) findViewById(R.id.editLatitude);
      //  txtLon = (TextView) findViewById(R.id.editLongitude);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values and add to CP
                String name = tvtName.getText().toString();
                String surname = txtSurname.getText().toString();
                String email = txtEmail.getText().toString();
                String streetAddress = txtAddress.getText().toString();
           //     String lat = txtLat.getText().toString();
           //     String lon = txtLon.getText().toString();



                //now instantiative the VendorDTO
                VendorDTO vendorDTO = new VendorDTO(/*null,0,*/name,surname/*,Double.parseDouble(lat),Double.parseDouble(lon)*/,streetAddress, email/*, null, null*/);

                if( vendorDTO == null) {
                    Util.showErrorToast(context, "fields are empty, please fill in fields");
                }
                //add this to CP

               Uri uri = VendorsContentProviderUtils.addVendor(getContentResolver(), vendorDTO);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                if (uri != null){
                VendorGCMUtils.registerVendorGCM(context, email, VendorGCMUtils.getMyRegistrationId(context), getResources().getString(R.string.app_name));

            }
            }
        });
    }



    private void saveVendor(){

    }
}
