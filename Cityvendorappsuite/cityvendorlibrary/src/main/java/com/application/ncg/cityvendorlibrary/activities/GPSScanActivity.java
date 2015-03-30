package com.application.ncg.cityvendorlibrary.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

public class GPSScanActivity extends ActionBarActivity {



    public interface GPSScanActivityListener {
        public void onStartScanRequested();
        public void onLocationConfirmed(VendorDTO vendor);
        public void onEndScanRequested();
        public void onMapRequested(VendorDTO vendor);
    }

    private GPSScanActivityListener listener;

    /**
     * creating new instance of the activity using the provided parameters
     * using factory method to create new instance
     *
     * @return A new instance will be returned of Activity GPSScanActivity
     */

    public static GPSScanActivity newInstance() {
        GPSScanActivity act = new GPSScanActivity();
        Bundle args = new Bundle();
       // act.setArguments(args);
        return act;
    }

    public GPSScanActivity() {
        //empty constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView txt_lat, txt_lng, txtAccuracy, desiredAccuracy, txtSite;
    Button btn_location;
    SeekBar seekBar;
    boolean isScanning;
    long start, end;
    Context ctx;
    Chronometer chronometer;
    VendorDTO vendor;
    ImageView GPS_image, HEAD_img;
    View view;

  //  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(LOG, "onCreateView");
        view = inflater.inflate(R.layout.activity_gpsscan, container, false);
        ctx = getApplicationContext();
        setFields();
        return view;
    }

    private void setFields(){

        txt_lat = (TextView) view.findViewById(R.id.GPS_latitude);
        txt_lng = (TextView) view.findViewById(R.id.GPS_latitude);

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

    static final String LOG = GPSScanActivity.class.getSimpleName();
}
