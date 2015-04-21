package com.application.ncg.cityvendorlibrary.activities;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.util.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener,
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    private GoogleMap googleMap, mMap; // Might be null if Google Play services APK is not available.
    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    Location location;
    Context ctx;
    List<VendorDTO> mVendorList;
    List<Marker> mark = new ArrayList<Marker>();
    static final String LOG = MapsActivity.class.getSimpleName();
    //unique error dialog
    private static final String DIALOG_ERROR = "dialog_error";
    VendorDTO vendor;
    int index;
    TextView text, textCount, textMap;
    View topLayout;
    ProgressBar progressBar;
    static final int VENDOR_VIEW = 100;
    private List<LatLng> iterateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        try {
            setContentView(R.layout.activity_maps);
        } catch (Exception e) {
            Log.e(LOG, "Unable to setContentView");
        }
        vendor = (VendorDTO) getIntent().getSerializableExtra("vendor");
        index = getIntent().getIntExtra("index", 0);
        int displayType = getIntent().getIntExtra("displayType", VENDOR_VIEW);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        text = (TextView) findViewById(R.id.textMap);
        textCount = (TextView) findViewById(R.id.count);
        textMap = (TextView) findViewById(R.id.textMap);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);
       // Statics.setRobotoFontBold(ctx, text);

        topLayout = findViewById(R.id.top);

        googleMap = mapFragment.getMap();
        if (googleMap == null) {
            Util.showToast(ctx, getString(R.string.map_unavailable));
            finish();
            return;
        }

        if (displayType == VENDOR_VIEW) {
            setVendorMarker();
        }
        setGoogleMap();

       // setUpMapIfNeeded();
    }
    private void setVendorMarker() {
        googleMap.clear();

        if (!iterateList.isEmpty()) {
            int index = 0;
            for (VendorSiteDTO vs : vendor.getVendorsiteList()){
                BitmapDescriptor desc = BitmapDescriptorFactory.fromResource(R.drawable.dot_black);
                Integer addedVendorColor = null;
                for (VendorDTO v : vs.getVendorList()) {
                    addedVendorColor = vs.getVendorList().get(0).getVendorID();
                    switch (addedVendorColor) {
                        case 1:  desc = BitmapDescriptorFactory.fromResource(R.drawable.dot_green);
                        break;
                    }
                    index++;
                    Log.d(LOG, " " + index);
                    Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(v.getLatitude(), v.getLongitude())).icon(desc));
                    m.showInfoWindow();
                    mark.add(m);

                }
            }

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    //Make sure marker is in bounds
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : mark) {
                        builder.include(marker.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 10; //offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    textCount.setText(" " + mark.size());
                    // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 1.0f));
                    googleMap.animateCamera(cu);
                    setTitle(vendor.getName());
                }
            });
        }


    }

    private void setGoogleMap() {
        googleMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                Log.i(LOG, "onMapClickListener");
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                Location loc = new Location(location);
                loc.setLatitude(latLng.latitude);
                loc.setLongitude(latLng.longitude);

                float f = location.distanceTo(loc);
                Log.w(LOG, "distance" + f);
              //  showPopup(latLng.latitude, latLng.longitude, marker.getTitle() + "\n" + marker.getSnippet());
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link /*#mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
