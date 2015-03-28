package com.application.ncg.cityvendorappsuite;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.application.ncg.cityvendorappsuite.providers.VendorsContentProviderUtils;
import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

public class VendorActivity extends Activity {

    public static final String LOCATION_KEY = "location";

    Button btn_submit;
    TextView tvtName;
    TextView txtSurname;
    TextView txtEmail;
    TextView txtAddress;
    TextView txtLat;
    TextView txtLon;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vendor_activity);
        context = this;
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tvtName = (TextView) findViewById(R.id.editName);
        txtSurname = (TextView) findViewById(R.id.editSurname);
        txtEmail = (TextView) findViewById(R.id.editEmail);
        txtAddress = (TextView) findViewById(R.id.editStreetAddress);
        txtLat = (TextView) findViewById(R.id.editLatitude);
        txtLon = (TextView) findViewById(R.id.editLongitude);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values and add to CP
                String name = tvtName.getText().toString();
                String surname = txtSurname.getText().toString();
                String email = txtEmail.getText().toString();
                String pAddress = txtAddress.getText().toString();
                String lat = txtLat.getText().toString();
                String lon = txtLon.getText().toString();

                //now instantiative the VendorDTO
                VendorDTO vendorDTO = new VendorDTO(null,0,name,surname,Double.parseDouble(lat),Double.parseDouble(lon),pAddress, email, null, null);
                //add this to CP

               Uri uri = VendorsContentProviderUtils.addVendor(getContentResolver(), vendorDTO);
            if (uri != null){
                VendorGCMUtils.registerVendorGCM(context, email, VendorGCMUtils.getMyRegistrationId(context), getResources().getString(R.string.app_name));

            }
            }
        });
    }



    private void saveVendor(){

    }
}
