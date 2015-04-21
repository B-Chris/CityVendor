package com.application.ncg.cityvendorappsuite;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.ncg.cityvendorappsuite.adapters.NotificationAdapter;
import com.application.ncg.cityvendorappsuite.providers.VendorsContentProviderUtils;
import com.application.ncg.cityvendorlibrary.activities.MapsActivity;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends ActionBarActivity {

    Context ctx;
    private ListView VENDOR_LIST_list;
    private NotificationAdapter adapter;
    List<VendorDTO> mVendorList;
    VendorAdaptor vendorAdaptor;

    GoogleCloudMessaging gcm;
    String gcmRegistrationId;
    AtomicInteger messageId = new AtomicInteger();
    private Integer vendorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ctx = getApplicationContext();
        VENDOR_LIST_list = (ListView) findViewById(R.id.VENDOR_LIST_list);

        if (mVendorList == null){
            mVendorList = VendorsContentProviderUtils.getVendors(getContentResolver());
            Log.i(TAG, mVendorList +"");
        }

        //adapter = new NotificationAdapter(ctx, mVendorList);
        ctx = getApplicationContext();
        vendorAdaptor = new VendorAdaptor(ctx, mVendorList);
        VENDOR_LIST_list.setAdapter(vendorAdaptor);

        VENDOR_LIST_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VendorDTO ven = (VendorDTO) vendorAdaptor.getItem(position);
                Intent i = new Intent(getApplicationContext(), VendorAdaptor.class);
            }
        });
    }

    /**
     *Method checking if gcm registration is done and if it isn't start registration
     */

    private void registerIfNotRegistered(){
        //checking if google play services exists
      //  Log.i(TAG, "register if there is no previous registration");
       // if (!=VendorGCMUtils.hasPlayServices(this) || currentUser ==null){
       //     return;
       // }

        gcm = GoogleCloudMessaging.getInstance(this);
        gcmRegistrationId = VendorGCMUtils.getMyRegistrationId(this);
        if (gcmRegistrationId == null) {
            doInAsyncGCMRegistration();
        }

    }

    /**
     * Helper method for performing gcm registration
     */

    //toast the registrationID just to make sure u get the registrationID from google
    private void doInAsyncGCMRegistration() {
     //   Log.i(LOG, "doInAsyncGCMRegistration");
        new AsyncTask<Void, Object, Object>(){

            @Override
            protected Object doInBackground(Void... params) {
                Log.i(TAG, "doAsyncGCMRegistration:doInBackground");
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(ctx);
                }
                try {
                    gcmRegistrationId = gcm.register(VendorGCMUtils.SENDER_ID);
                    Log.i(TAG, "AFTER-Register: " + gcmRegistrationId);
                    if (gcmRegistrationId != null) {
                        //caching registration
                        VendorGCMUtils.saveGCMRegistrationId(gcmRegistrationId, ctx);
                    }


                } catch (IOException e){
                    Log.e(TAG, "regId = " + gcmRegistrationId);
                }

                return gcmRegistrationId;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                //here we are done - we should register on server
          //      if (VendorGCMUtils.getMyRegistrationId(ctx) != null) {
          //          VendorGCMUtils.registerVendorGCM(ctx, "chris@gmail.com", VendorGCMUtils.getMyRegistrationId(ctx), "CityVendor");
          //      }
            }


        }.execute(null, null, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if(id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, VendorActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_map) {
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static final String TAG = MainActivity.class.getSimpleName();

}
