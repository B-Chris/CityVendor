package com.application.ncg.cityvendorappsuite;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ncg.cityvendorappsuite.providers.VendorsContentProviderUtils;
import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.activities.GPSScanActivity;
import com.application.ncg.cityvendorlibrary.activities.MapsActivity;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.services.RequestSyncService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class VendorActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener{

    public static final String LOCATION_KEY = "http://10.50.75.35:8080/cvp/";
    VendorDTO vendor;
    VendorSiteDTO vendorSite;
    Button btn_submit, btn_location, btn_Vlist;
    TextView tvtName;
    TextView txtSurname;
    TextView txtEmail;
    TextView txtAddress;
    TextView txtLat;
    TextView txtLon;
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
        txtLat = (TextView) findViewById(R.id.editLatitude);
        txtLon = (TextView) findViewById(R.id.editLongitude);

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
                //  startGPSScanFragment();
                //startGPSDialog();

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
                if (txtLat.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "Latitude field is empty, please fill in Vendor Latitude");
                    return;
                }
                if (txtLon.getText() == null) {
                    ToastUtil.toast(VendorActivity.this, "Longitude field is empty, please fill in Vendor Longitude");
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
                String lat = txtLat.getText().toString();
                String lon = txtLon.getText().toString();


                //now instantiate the VendorDTO
                VendorDTO vendorDTO = new VendorDTO(/*null,0,*/name,surname,Double.parseDouble(lat),Double.parseDouble(lon),streetAddress, email/*, null, null*/);

                if( vendorDTO == null) {
                    Toast.makeText(context, "fields are empty, please fill in fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                //add this to CP

               Uri uri = VendorsContentProviderUtils.addVendor(getContentResolver(), vendorDTO);
                if (uri != null){
             //   VendorGCMUtils.registerVendorGCM(context, email, VendorGCMUtils.getMyRegistrationId(context), getResources().getString(R.string.app_name));

            }

            }
        });

        cleanPage();
    }

    private void cleanPage() {
        tvtName.setText(" ");
        txtSurname.setText(" ");
        txtEmail.setText(" ");
        txtAddress.setText(" ");
//        txtLon.setText(0);
//        txtLat.setText(0);
   //     vendorSite = new VendorSiteDTO();
    }






    public void onStartScanRequested() {
    getGPSCoordinates();
    }
    private Integer vendorID;


    public void onLocationConfirmed(VendorSiteDTO vs) {
        Log.w(LOG, "GPSScannerDialog will now try to confirm location for vendorSite");
        vendorSite = new VendorSiteDTO();
        vendorSite.setLocationConfirmed(1);
        vendorSite.setLongitude(vs.getLongitude());
        vendorSite.setLatitude(vs.getLatitude());
        vendorSite.setAccuracy(vs.getAccuracy());
        vendorSite.setVendorID(vendorID);
        Log.w(LOG, "Vendor site has been created");
        //gpsScanActivity.dismiss();
        stopPeriodUpdates();

    }


    public void onEndScanRequested() {
    Log.w(LOG, "onEndScanRequested");
        getGPSCoordinates();
        stopPeriodUpdates();
    }

    static final int MAP_REQUESTED = 9008;

    public void onMapRequested(VendorSiteDTO vendorSite) {
     if (vendorSite.getLatitude() != 0) {
         Intent i = new Intent(context, MapsActivity.class);
         i.putExtra("vendorSite", vendorSite);
         startActivityForResult(i, MAP_REQUESTED);
     }
    }

    private void getGPSCoordinates() {

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(1000);

        try {
           LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        } catch (IllegalStateException e) {
            Log.e(LOG, "mGoogleClient. requestLocationUpdates ILLEGAL STATE", e);
        }
    }

    private void stopPeriodUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        Log.e(LOG, "stopPeriodicUpdates - removingLocationUpdates");
    }

    @Override
    public void onStart() {
    super.onStart();
    Log.i(LOG, "onStart, binding RequestSyncService");
        Intent requestIntent = new Intent(this, RequestSyncService.class);
        bindService(requestIntent, mConnection, Context.BIND_AUTO_CREATE);
    if (mGoogleApiClient != null) {
        mGoogleApiClient.connect();
        Log.i(LOG, "onStart - GoogleApiClient is connecting");
    }

}
    @Override
    public void onStop() {
        Log.d(LOG, "FIRED >>> onStop");
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopPeriodUpdates();
            }
            //disconnecting client
            mGoogleApiClient.disconnect();
            Log.e(LOG, "onStop - GoogleApiClient isConnected" + mGoogleApiClient.isConnected());

        }
        try {
            if (mBound) {
                unbindService(mConnection);
                mBound = false;
            }
        } catch (Exception e) {
            Log.e(LOG, "unable to unbind", e);
        }
        super.onStop();
    }
    RequestSyncService mService;

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
