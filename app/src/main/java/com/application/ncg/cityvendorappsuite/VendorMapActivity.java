package com.application.ncg.cityvendorappsuite;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.dto.ProductsDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.RequestDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.application.ncg.cityvendorlibrary.util.CacheUtil;
import com.application.ncg.cityvendorlibrary.util.ErrorUtil;
import com.application.ncg.cityvendorlibrary.util.Statics;
import com.application.ncg.cityvendorlibrary.util.Util;
import com.application.ncg.cityvendorlibrary.util.WebCheck;
import com.application.ncg.cityvendorlibrary.util.WebCheckResult;
import com.application.ncg.cityvendorlibrary.util.WebSocketUtil;
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
import java.util.Random;


public class VendorMapActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 60000; /* 60 seconds*/
    private long FASTEST_INTERVAL = 5000; /* 5 seconds*/
    LocationRequest locationRequest;
    Location location;
    Context ctx;
    List<Marker> markers = new ArrayList<Marker>();
    boolean mResolvingError;
    final static String LOG = VendorMapActivity.class.getSimpleName();
    VendorDTO vendor;
    int index;
    GoogleMap googleMap;
    TextView text, textCount;
    ProductsDTO products;
    boolean toastHasBeenShown;
    VendorSiteDTO vendorSite;
    View topLayout;
    ProgressBar progressBar;



    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   ctx = getApplicationContext();
        try{
            setContentView(R.layout.activity_vendor_map);
        } catch(Exception e) {
            Log.e(LOG, "unable to setContentView", e);
        }
        vendorSite = (VendorSiteDTO) getIntent().getSerializableExtra("vendorSite");
        vendor = (VendorDTO) getIntent().getSerializableExtra("vendor");
        index = getIntent().getIntExtra("index", 0);

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        text = (TextView) findViewById(R.id.text);
        textCount = (TextView) findViewById(R.id.count);
        textCount.setText("0");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Statics.setDroidFontBold(ctx, text);

        topLayout = findViewById(R.id.top);

        if (vendorSite != null) {
            textCount.setText("1");
            text.setText(getString(R.string.site_map));
        }
        if (vendor != null) {
            text.setText(getString(R.string.vendor_map));
        }

        googleMap = mapFrag.getMap();
        if (googleMap == null) {
            Util.showToast(ctx, getString(R.string.map_unavailable));
            finish();
            return;
        }

     setGoogleMap();
    }

    private void setGoogleMap() {
        googleMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);
        //remove after test
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                Log.w(LOG, "onMapClick --->>FIRED<<---");
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                Location loc = new Location(location);
                loc.setLatitude(latLng.latitude);
                loc.setLongitude(latLng.longitude);
                if (vendor != null) {
                    for (VendorSiteDTO vs : vendor.getVendorsiteList()) {
                        if (vs.getVendorSiteName().equalsIgnoreCase(marker.getTitle())) {
                            vendorSite =  vs;
                        }
                    }
                }
                float mf = location.distanceTo(loc);
                Log.w(LOG, "distance, again: " + mf);
                showPopup(latLng.latitude, latLng.longitude, marker.getTitle() + "\n" + marker.getSnippet());

                return true;
            }
        });
        if (vendorSite != null) {
            if (vendorSite.getLatitude() == 0) {
                Util.showToast(ctx, getString(R.string.no_coordinates));
                finish();
            } else{
                setOneMarker();
                if (vendorSite.getLocationConfirmed() == null && !toastHasBeenShown) {
                    Util.showToast(ctx, getString(R.string.tap_vendor_site));
                    toastHasBeenShown = true;
                }
            }
        }

        if (vendor != null) {
            if (vendor.getVendorsiteList() == null || vendor.getVendorsiteList().isEmpty()) {
                getCachedData();
            } else {
                setVendorMarkers();
                text.setText(vendor.getName() + vendor.getSurname() + "-Sites( " + vendor.getVendorsiteList().size() + ")");

            }
        }
    }

    private void getCachedData() {
        CacheUtil.getCachedVendorData(ctx, vendor.getVendorID(), new CacheUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {
                if (response != null) {
                    if (response.getVendorList() != null && !response.getVendorList().isEmpty()) {
                        vendor = response.getVendorList().get(0);
                        setVendorMarkers();
                        text.setText(vendor.getName());
                    }
                }
                WebCheckResult wcr = WebCheck.checkNetworkAvailability(ctx);
                if (wcr.isWifiConnected()) {
                    refreshVendorData();
                }
            }

            @Override
            public void onDataCached() {

            }

            @Override
            public void onError() {
                WebCheckResult wcr = WebCheck.checkNetworkAvailability(ctx);
                if (wcr.isWifiConnected()) {
                    refreshVendorData();
                }
            }
        });
    }


    private void refreshVendorData() {
        RequestDTO w = new RequestDTO(/*RequestDTO.GET_VENDOR*/);
        w.setVendorID(vendor.getVendorID());
        WebSocketUtil.sendRequest(ctx, Statics.VENDOR_ENDPOINT, w, new WebSocketUtil.WebSocketListener() {
            @Override
            public void onMessage(final ResponseDTO response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!ErrorUtil.checkServerError(ctx, response)) {
                            return;
                        }
                        if (response.getVendorList().isEmpty()) {
                            return;
                        }
                        vendor = response.getVendorList().get(0);
                        setVendorMarkers();
                        text.setText(vendor.getName());
                    }
                });

            }

            @Override
            public void onClose() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshVendorData();
                    }
                });
            }

            @Override
            public void onError(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Util.showErrorToast(ctx, message);
                    }
                });
            }
        });
    }

    Random random = new Random(System.currentTimeMillis());

    private void setVendorMarkers() {
        googleMap.clear();
        LatLng point = null;
        int index = 0, count = 0, randomIndex = 0;
        randomIndex = random.nextInt(vendor.getVendorsiteList().size() -1);
        if(randomIndex == -1) randomIndex = 0;

        if (vendor.getVendorsiteList().get(randomIndex).getLatitude() == 0) {
            for (VendorSiteDTO vs : vendor.getVendorsiteList()) {
                if (vs.getLatitude() != 0) {
                    point = new LatLng(vs.getLatitude(), vs.getLongitude());
                    break;
                }
            }
        } else {
            point = new LatLng(vendor.getVendorsiteList().get(randomIndex).getLatitude(),
            vendor.getVendorsiteList().get(randomIndex).getLongitude());
        }
        for (VendorSiteDTO site : vendor.getVendorsiteList()) {
            if (site.getLatitude() == 0) continue;
            LatLng pnt = new LatLng(site.getLatitude(), site.getLongitude());
            point = pnt;
            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.dot_black);

            Marker m = googleMap.addMarker(new MarkerOptions()
                    .title(site.getVendorSiteName())
                    .icon(descriptor)
                    .snippet(site.getVendorSiteName())
                    .position(pnt));
            markers.add(m);
            index++;
            count++;
        }
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                //make sure all markers in bounds
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 60; //offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,padding);

                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 1.0f));
                googleMap.animateCamera(cu);
                setTitle(vendor.getName());
            }
        });

    }

    private void setOneMarker() {
        if (vendorSite.getLatitude() == 0) {
            return;
        }
        LatLng pnt = new LatLng(vendorSite.getLatitude(), vendorSite.getLongitude());
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.number_1);
        Marker m = googleMap.addMarker(new MarkerOptions()
        .title(vendorSite.getVendorSiteName())
        .icon(descriptor)
        .snippet(vendorSite.getVendorSiteName())
        .position(pnt));
    markers.add(m);
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 1.0f));
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        setTitle(vendorSite.getVendorSiteName());
        getSupportActionBar().setSubtitle(vendorSite.getVendorSiteName());
    }

    List<String> list;
    private void showPopup(final double lat, final double lng, String title) {
        list = new ArrayList<>();
        list.add(getString(R.string.directions));

        Util.showPopupBasicWithHeroImage(ctx, this, list, topLayout, ctx.getString(
                R.string.site_colon) + vendorSite.getVendorSiteName(),
                new Util.UtilPopupListener() {
                    @Override
                    public void onItemSelected(int index) {
                        if (list.get(index).equalsIgnoreCase(ctx.getString(R.string.directions))) {
                            startDirectionsMap(lat, lng);
                        }
                    }
                });
    }

    private void startDirectionsMap(double lat, double lng) {
        Log.i(LOG, "startDirectionMap");
        String url = "http://maps.google.com/maps?saddr="
                + location.getLatitude() + " , " + location.getLongitude()
                + "&daddr=" + lat + " , " + lng + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }




    /*Called by Location Services when the request to connect the client
    * finishes successfully. At this point, you can request the current
    * location or start periodic updates
    * */

    @Override
    public void onConnected(Bundle bundle) {
        //Display the connection status
       /* Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Toast.makeText(this, "GPS location found", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
            startLocationUpdates();
        } else {
            Toast.makeText(this, "Current location was null, enable GPS", Toast.LENGTH_SHORT).show();
        }
        */
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

    }



    @Override
    public void onLocationChanged(Location location) {
        //letting the UI know the location was updated
      /*  String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + " , "
                + Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        */
    }

    /*Called by location services if the connection to the location client drop due to an error*/

    @Override
    public void onConnectionSuspended(int i) {
        /*if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
        */
    }

    /*Called by Location Services if the attempt to Location Services fails*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
/*  Google play services can resolve some errors it detects. If the error
*  has a resolution, try sending an Intent to start a Google Play services
*  activity that can resolve error.
* */

  //      if (connectionResult.hasResolution()) {
        //    try {
                //starting activity that resolves error
            //    connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*Thrown if Google Play Services cancels the original PendingIntent*/

          //  } catch (IntentSender.SendIntentException e) {
        //        e.printStackTrace();
      //      }
    //    } else {
//            Toast.makeText(getApplicationContext(), "Location is currently not available", Toast.LENGTH_SHORT).show();

  //      }

     }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    //DialogFragment that displays the error dialog
   /* public static class ErrorDialogFragment extends DialogFragment{
        //Global field
        private Dialog mDialog;
        //null constructor
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        //Set dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        //return Dialog to fragment
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
          return mDialog;
        }
    }
    */
}
