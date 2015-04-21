package com.application.ncg.cityvendorappsuite;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Chris on 2015-04-12.
 */
public class MapActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 60000; /* 60 seconds*/
    private long FASTEST_INTERVAL = 5000; /*5 seconds*/

    /*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    loadMap(googleMap);
                }
            });
        } else{
            Toast.makeText(this, "Error - Map Fragment was null", Toast.LENGTH_SHORT).show();
        }

    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            //Map is ready
            Toast.makeText(this, "Map Fragment been correctly loaded", Toast.LENGTH_SHORT).show();
            map.setMyLocationEnabled(true);

            //map has now loaded.. now getting our location
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build();

            connectClient();
        } else {
            Toast.makeText(this, "Error - Map was null", Toast.LENGTH_SHORT).show();

        }
    }

    protected void connectClient() {
        //Connect the client.
        if (isGooglePlayServicesAvailable() && mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /*
     * Called when the Activity becomes visible.
    */
    @Override
    protected void onStart() {
        super.onStart();
        connectClient();
    }

    /*
	 * Called when the Activity is no longer visible.
	 */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /*Handling returned results */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                mGoogleApiClient.reconnect();
                break;
            case Activity.RESULT_OK:
                mGoogleApiClient.connect();
                break;
            case Activity.RESULT_CANCELED:
                mGoogleApiClient.disconnect();
                break;
        }
    }

    private boolean isGooglePlayServicesAvailable(){
        //CHECK that google play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        //IF Google play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            //logging status for google play services
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            //Get the error dialog from Google Play Services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            //if Google Play Services can provide an error dialog
            if (errorDialog != null) {
                //Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                //   errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }
            return false;
        }
    }

    /*Called by Location Services when the request to connect the client
    * finishes successfully. At this point, you can request the current
    * location or start periodic updates
    * */

    @Override
    public void onConnected(Bundle bundle) {
        //Display the connection status
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Toast.makeText(this, "GPS location found", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
            startLocationUpdates();
        } else {
            Toast.makeText(this, "Current location was null, enable GPS", Toast.LENGTH_SHORT).show();
        }
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
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + " , "
                + Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*Called by location services if the connection to the location client drop due to an error*/

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    /*Called by Location Services if the attempt to Location Services fails*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
/*  Google play services can resolve some errors it detects. If the error
*  has a resolution, try sending an Intent to start a Google Play services
*  activity that can resolve error.
* */

        if (connectionResult.hasResolution()) {
            try {
                //starting activity that resolves error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*Thrown if Google Play Services cancels the original PendingIntent*/

            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Location is currently not available", Toast.LENGTH_SHORT).show();

        }

    }

    //DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
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
}
