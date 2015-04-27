package com.application.ncg.cityvendorappsuite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.activities.GPSScanActivity;
import com.application.ncg.cityvendorlibrary.dto.GcmDeviceDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.services.RequestSyncService;
import com.application.ncg.cityvendorlibrary.util.GCMUtil;
import com.application.ncg.cityvendorlibrary.util.SharedUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class VendorActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{

    public static final String LOCATION_KEY = "http://10.50.75.35:8080/cvp/";
    VendorDTO vendor;
    GcmDeviceDTO gcmDevice;
    VendorSiteDTO vendorSite;
    Button btn_submit, btn_location, btn_Vlist;
    TextView tvtName;
    TextView txtSurname;
    TextView txtEmail;
    TextView txtAddress;
    ImageView HEAD_image, V_img;
    Context context;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    int selectedVendorIndex, currentPageIndex;
    Location mCurrentLocation;
    boolean mBound;
    static final int ACCURACY_THRESHOLD = 10;
    Location location;
    GPSScanActivity gpsScanActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vendor_activity);
        context = this;
        HEAD_image = (ImageView) findViewById(R.id.HEAD_img);
        V_img = (ImageView) findViewById(R.id.V_img);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_Vlist = (Button) findViewById(R.id.btn_Vlist);
        tvtName = (TextView) findViewById(R.id.editName);
        txtSurname = (TextView) findViewById(R.id.editSurname);
        txtEmail = (TextView) findViewById(R.id.editEmail);
        txtAddress = (TextView) findViewById(R.id.editStreetAddress);

        btn_Vlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VendorActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent i = new Intent(VendorActivity.this, GPSScanActivity.class);
                 startActivity(i);


            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  /*              if (vendor == null) {
                    ToastUtil.toast(VendorActivity.this, "vendor details have not been filled");

                }
                if (txtSurname.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "Surname field is empty, please fill in Vendor Surname");
                    return;
                }
                if (txtEmail.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "Email field is empty, please fill in Vendor Email");
                    return;
                }
                if (txtAddress.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "StreetAddress field is empty, please fill in Vendor StreetAddress");
                    return;
                }

                if (tvtName.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "Name field is empty, please fill in Vendor name");
                    return;
                } else {
                    ToastUtil.toast(VendorActivity.this, "Vendor details have not been correctly filled, please fill in fields");
                }
*/

                //get values and add to CP
                String name = tvtName.getText().toString();
                String surname = txtSurname.getText().toString();
                String email = txtEmail.getText().toString();
                String streetAddress = txtAddress.getText().toString();


                //now instantiate the VendorDTO
                VendorDTO vendorDTO = new VendorDTO(/*null,*/name,surname,streetAddress, email);

                if( vendorDTO == null) {
                    Toast.makeText(context, "fields are empty, please fill in fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                //add this to CP

              // Uri uri = VendorsContentProviderUtils.addVendor(getContentResolver(), vendorDTO);
             //   if (uri != null){
           //     VendorGCMUtils.registerVendorGCM(context, email, VendorGCMUtils.getMyRegistrationId(context), getResources().getString(R.string.app_name));

        //    }

            }
        });
        registerGCMDevice();
        cleanPage();
    }

    private void cleanPage() {
        tvtName.setText(" ");
        txtSurname.setText(" ");
        txtEmail.setText(" ");
        txtAddress.setText(" ");

   //     vendorSite = new VendorSiteDTO();
    }

    private Integer vendorID;

    static final int MAP_REQUESTED = 9008;

    @Override
    public void onStart() {
    super.onStart();
    Log.i(LOG, "onStart, binding RequestSyncService");
        Intent requestIntent = new Intent(this, RequestSyncService.class);
      //  bindService(requestIntent, mConnection, Context.BIND_AUTO_CREATE);
    if (mGoogleApiClient != null) {
        mGoogleApiClient.connect();
        Log.i(LOG, "onStart - GoogleApiClient is connecting");
    }

}

    private void registerGCMDevice() {
        boolean ok = checkPlayServices();

        if (ok) {
            Log.e(LOG, "############# Starting Google Cloud Messaging registration");
            GCMUtil.startGCMRegistration(getApplicationContext(), new GCMUtil.GCMUtilListener() {
                @Override
                public void onDeviceRegistered(String id) {
                    Log.e(LOG, "############# GCM - we cool, cool.....: " + id);
                    SharedUtil.storeRegistrationId(context, id);
                    gcmDevice = new GcmDeviceDTO();
                    gcmDevice.setManufacturer(Build.MANUFACTURER);
                    gcmDevice.setModel(Build.MODEL);
                    gcmDevice.setSerialNumber(Build.SERIAL);
                    gcmDevice.setProduct(Build.PRODUCT);
                    gcmDevice.setAndroidVersion(Build.VERSION.RELEASE);
                    //gcmDevice.setRegistrationID(id);


                }

                @Override
                public void onGCMError() {
                    Log.e(LOG, "############# onGCMError --- we got GCM problems");

                }
            });
        }
    }

    public boolean checkPlayServices() {
        Log.w(LOG, "checking GooglePlayServices .................");
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                //         PLAY_SERVICES_RESOLUTION_REQUEST).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms")));
                return false;
            } else {
                Log.i(LOG, "This device is not supported.");
                throw new UnsupportedOperationException("GooglePlayServicesUtil resultCode: " + resultCode);
            }
        }
        return true;
    }
    @Override
    public void onStop() {
        Log.d(LOG, "FIRED >>> onStop");
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {

            }
            //disconnecting client
            mGoogleApiClient.disconnect();
            Log.e(LOG, "onStop - GoogleApiClient isConnected" + mGoogleApiClient.isConnected());

        }
        try {
            if (mBound) {
            //    unbindService(mConnection);
                mBound = false;
            }
        } catch (Exception e) {
            Log.e(LOG, "unable to unbind", e);
        }
        super.onStop();
    }
    RequestSyncService mService;

    /*
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.w(LOG, "RequestSyncService ServiceConnection onServiceConnected");
            RequestSyncService.LocalBinder binder = (RequestSyncService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            Log.w(LOG, "starting RequestSyncAService");
            mService.startSyncCachedRequests(new RequestSyncService.RequestSyncListener() {
                @Override
                public void onVendorSynced(int goodResponses, int badResponses) {
                    Log.e(LOG, "onVendorSynced, good: " + goodResponses + "Bad: " +
                    badResponses);
                }

                @Override
                public void onError(String message) {

                }
            });
        }


        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.w(LOG, "FIRED onServiceDisconnected");
            mBound = false;
        }
        };
*/


public void onLocationChanged(Location loc) {
    Log.w(LOG, "Location changed, lat: " + loc.getLatitude() + "lng: " + loc.getLongitude()
    + "Accuracy: " + loc.getAccuracy());
    mCurrentLocation = loc;
    if (gpsScanActivity != null) {
        gpsScanActivity.setLocation(loc);
    }
    if (loc.getAccuracy() <= ACCURACY_THRESHOLD) {
        location = loc;
        Log.w(LOG, "passing Location");
        gpsScanActivity.setLocation(loc);
        gpsScanActivity.stopScan();
        //gpsScannerDialog.dismiss();
       LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        Log.e(LOG, "best accuracy found: " + location.getAccuracy());
    }
}



static final String LOG = VendorActivity.class.getSimpleName();

    @Override
    public void onConnected(Bundle bundle) {
    Log.i(LOG, "PlayServices onConnected");
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG, "PlayServices onConnectionFailed: " + connectionResult.toString());
    }
}
