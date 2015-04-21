package com.application.ncg.cityvendorlibrary.activities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.application.ncg.cityvendorlibrary.util.Statics;
import com.application.ncg.cityvendorlibrary.util.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Chris on 2015-04-20.
 */
public class GPSScanActivity extends ActionBarActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    Location location, mCurrentLocation;
    GoogleApiClient mGoogleApiClient;
    ResponseDTO response;
    LocationRequest mLocationRequest;
    static final int GPS_DATA = 1003;
    TextView desiredAccuracy, txt_lat, txt_lng, txtAccuracy, txtSite;
    Button btn_save, btn_scan;
    View view;
    SeekBar seekBar;
    boolean isScanning;
    VendorDTO vendor;
    VendorSiteDTO vendorSite;
    ImageView HEAD_img;
    Context ctx;
    ObjectAnimator logoAnimator;
    long start, end;
    Chronometer chronometer;
    boolean isStop = false;
    boolean isFirst = true;

    static final String LOG = GPSScanActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsscanner);
        ctx = getApplicationContext();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        setFields();

    }

    private void setFields() {
        desiredAccuracy = (TextView) findViewById(R.id.GPS_desiredAccuracy);
        txtAccuracy = (TextView) findViewById(R.id.GPS_accuracy);
        txt_lng = (TextView) findViewById(R.id.GPS_longitude);
        txt_lat = (TextView) findViewById(R.id.GPS_latitude);
        seekBar = (SeekBar) findViewById(R.id.GPS_seekBar);
        HEAD_img = (ImageView) findViewById(R.id.GPS_HEAD_img);
        chronometer = (Chronometer) findViewById(R.id.GPS_chronometer);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_scan = (Button) findViewById(R.id.btn_scan);

        Statics.setRobotoFontBold(ctx, txt_lat);
        Statics.setDroidFontBold(ctx, txt_lng);

        txtAccuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.flashOnce(txtAccuracy, 100, new Util.UtilAnimationListener() {
                    @Override
                    public void onAnimationEnd() {

                    }
                });
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                desiredAccuracy.setText(" " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void startScan() {
        getGPSCoordinates();
        txtAccuracy.setText("0.00");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setBase(SystemClock.elapsedRealtime());
        isScanning = true;
        btn_scan.setText(ctx.getString(R.string.stop_scan));
    }

    public void setLocation(Location location) {
        if (vendorSite == null) {
            vendorSite = new VendorSiteDTO();
        }
        this.location = location;
        txt_lat.setText(" " + location.getLatitude());
        txt_lng.setText(" " + location.getLongitude());
        txtAccuracy.setText(" " + location.getAccuracy());

        vendorSite.setLatitude(location.getLatitude());
        vendorSite.setLongitude(location.getLongitude());
        vendorSite.setAccuracy(location.getAccuracy());
        if (location.getAccuracy() == seekBar.getProgress() ||
                location.getAccuracy() < seekBar.getProgress())  {
            isScanning = false;
            chronometer.stop();
            vendorSite.setLatitude(location.getLatitude());
            vendorSite.setLongitude(location.getLongitude());
            vendorSite.setAccuracy(location.getAccuracy());
            stopScan();
            return;
        }
        Util.flashSeveralTimes(HEAD_img, 200, 2, null);
    }
/*
@Override
    public void onBackPressed() {
    Intent ves = new Intent(GPSScanActivity.this, VendorActivity.class);
    ves.putExtra("vendorSiteData", vendorSite);
    setResult(GPS_DATA, ves);
    finish();
    super.onBackPressed();
}
*/

    private void getGPSCoordinates() {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(1000);

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        } catch (IllegalStateException e) {
            Log.e(LOG, "mGoogleClient.requestLocationUpdates ILLEGAL STATE", e);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void stopPeriodicUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.e(LOG, "stopPeriodicUpdates - removeLocationUpdates");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG, "onStart, binding RequestSyncService and PhotoUploadService");
       // Intent intent = new Intent(this, CachedSyncService.class);
       // bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            Log.i(LOG, "onStart, googleApiClient is now connecting");
        }
        getGPSCoordinates();
        startScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsscan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        Log.d(LOG, "FIRED -->>> onStop");
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopScan();
            }
            //disconnecting GoogleApiClient
            mGoogleApiClient.disconnect();
            Log.e(LOG, "onStop - GoogleApiClient isConnected: " + mGoogleApiClient.isConnected());
        }

        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOG, "FIRED ---> PlayServices onConnected()");
        startScan();
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location loc) {
        Log.w(LOG, "Location has changed, latitude: " + loc.getLatitude() +
        " longitude: " + loc.getLongitude() + " accuracy: " + loc.getAccuracy());
        mCurrentLocation = loc;
        setLocation(loc);

        if (loc.getAccuracy() <= ACCURACY_THRESHOLD) {
            location = loc;
            Log.w(LOG, "Passing 2nd location");
            setLocation(loc);
            stopScan();

            finish();
            Log.e(LOG, "best accuracy found @: " + location.getAccuracy());
        }
    }

    public void stopScan() {
        stopPeriodicUpdates();
       // listener.onLocationConfirmed(vendorSite);
        isScanning = false;
        chronometer.stop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    static final int ACCURACY_THRESHOLD = 5;
}
