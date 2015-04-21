package com.application.ncg.cityvendorappsuite.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility class that acts like a Content Provider "Client"
 * Created by Chris on 2015-03-07.
 */
public class VendorsContentProviderUtils {

    private static final String TAG = VendorsContentProviderUtils.class.getSimpleName();

    public static final String VENDORS_AUTHORITY = "com.application.ncg.cityvendorappsuite.providers";
    public static final String VENDORS_BASE_PATH = "vendors";
    public static final Uri VENDORS_CONTENT_URI = Uri.parse("content://"+VENDORS_AUTHORITY+"/"+VENDORS_BASE_PATH);

    //These are literal values for Content-Resolver Query Types
    public static final int QUERY_TYPE_BY_COLUMN_ID = 1;
    public static final int QUERY_TYPE_BY_VENDOR_ID = 2;
    public static final int QUERY_TYPE_LIST = 3;

    /**
     * This method returns all the vendors cached in the Content Provider
     * @param contentResolver
     * @return - list of Vendors
     */
    public static List<VendorDTO> getVendors(ContentResolver contentResolver){

        List<VendorDTO> vendors = new ArrayList<VendorDTO>();
        Cursor vendorsListCursor = contentResolver.query(VENDORS_CONTENT_URI, VendorsTable.DEFAULT_VENDORS_PROJECTIONS,null,null,VendorsTable.DEFAULT_SORT_ORDER);
        if(vendorsListCursor != null){
            //we got the data...now iterate
            while(vendorsListCursor.moveToNext()){
                //get the values from the cursor
                VendorDTO vendorDTO = fromCursor(vendorsListCursor);
                vendors.add(vendorDTO);
            }
        }

        return vendors;
    }

    /**
     *
     * @param contentResolver
     * @param vendorDataObject
     * @return
     */
    public static Uri addVendor(ContentResolver contentResolver, VendorDTO vendorDataObject){
        Log.i(TAG, "Adding Vendor:: \n "+vendorDataObject);
        Uri newVendorUri = null;
        ContentValues newVendorValues = fromVendorDTO(vendorDataObject);
        if(newVendorValues != null){
            //use content-resolver to save into CP
            contentResolver.insert(VENDORS_CONTENT_URI,newVendorValues);
        }
        return newVendorUri;
    }

    /**
     * 
     * @param cursor
     * @return
     */
    private static VendorDTO fromCursor(Cursor cursor){
        if(cursor == null){ return null;}

        String name = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_NAME));
        String surname =cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_SURNAME));
        String email = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_EMAIL));
        String address = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_STREET_ADDRESS));
        String lat = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_LATITUDE));
        String lon = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_LONGITUDE));
        String globalVendorId = cursor.getString(cursor.getColumnIndex(VendorsTable.COLUMN_VENDOR_ID));
      //  int id = cursor.getInt(cursor.getColumnIndex(VendorsTable.COLUMN_CP_VENDOR_ID));

        VendorDTO vendor = new VendorDTO(/*id, 0,*/  name,  surname, Double.parseDouble(lat), Double.parseDouble(lon), address,  email/*, null, null*/);

        return vendor;
    }

    /**
     *
     * @param vendorDataObject
     * @return
     */
    public static ContentValues fromVendorDTO(VendorDTO vendorDataObject){
        if(vendorDataObject == null){ return null;}

        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put(VendorsTable.COLUMN_NAME, vendorDataObject.getName());
        valuesToInsert.put(VendorsTable.COLUMN_SURNAME, vendorDataObject.getSurname());
        valuesToInsert.put(VendorsTable.COLUMN_EMAIL, vendorDataObject.getEmail());
        valuesToInsert.put(VendorsTable.COLUMN_LATITUDE, vendorDataObject.getLatitude());
        valuesToInsert.put(VendorsTable.COLUMN_LONGITUDE, vendorDataObject.getLongitude());
        valuesToInsert.put(VendorsTable.COLUMN_STREET_ADDRESS, vendorDataObject.getStreetAddress());

        return valuesToInsert;
    }


}
