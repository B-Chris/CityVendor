package com.application.ncg.cityvendorappsuite.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Chris on 2015-03-07.
 */
public class VendorsContentProvider extends ContentProvider {
    private static final String TAG = VendorsContentProvider.class.getSimpleName();
    private VendorsDatabaseHelper database;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //content://com.application.ncg.cityvendorappsuite.providers/vendors
        uriMatcher.addURI(VendorsContentProviderUtils.VENDORS_AUTHORITY, VendorsContentProviderUtils.VENDORS_BASE_PATH, VendorsContentProviderUtils.QUERY_TYPE_LIST);
        uriMatcher.addURI(VendorsContentProviderUtils.VENDORS_AUTHORITY, VendorsContentProviderUtils.VENDORS_BASE_PATH+"/#", VendorsContentProviderUtils.QUERY_TYPE_BY_COLUMN_ID);
        uriMatcher.addURI(VendorsContentProviderUtils.VENDORS_AUTHORITY, VendorsContentProviderUtils.VENDORS_BASE_PATH+"/vendorID/*", VendorsContentProviderUtils.QUERY_TYPE_BY_VENDOR_ID);
    }

    @Override
    public boolean onCreate() {
        //we instantiate our DB
        database = new VendorsDatabaseHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
    //CRUD
    @Override
    public Cursor query(Uri uri, String[] columnsToReturn, String selection, String[] arguments, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(VendorsTable.VENDORS_TABLE_NAME);
        //is this a query-by ID or by Global-Vendor-ID?
        int queryType = uriMatcher.match(uri);
        switch(queryType){
            case VendorsContentProviderUtils.QUERY_TYPE_LIST:
                //here the user is retrieving all - i.e select * from vendors
                break;
            case VendorsContentProviderUtils.QUERY_TYPE_BY_COLUMN_ID:
                //here user is retrieving specific vendor - select * from vendors where ID=x
                queryBuilder.appendWhere(VendorsTable.COLUMN_CP_VENDOR_ID+"="+uri.getLastPathSegment());
                break;
            case VendorsContentProviderUtils.QUERY_TYPE_BY_VENDOR_ID:
                //here the user is retrieving a vendor with specific global ID
                queryBuilder.appendWhere(VendorsTable.COLUMN_VENDOR_ID+"="+uri.getLastPathSegment());
                break;
            default:
                break;
        }
        Cursor cursor = null;
        SQLiteDatabase db = database.getWritableDatabase();
        try{
            cursor = queryBuilder.query(db,columnsToReturn,selection,arguments,null,null,VendorsTable.DEFAULT_SORT_ORDER);
            if(cursor != null){ cursor.setNotificationUri(getContext().getContentResolver(), uri);}

        }catch(Exception e){
            Log.e(TAG, "Error while retrieving Vendor(s) ", e);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int queryType = uriMatcher.match(uri);
        if(queryType != VendorsContentProviderUtils.QUERY_TYPE_LIST){
            throw new IllegalArgumentException("Invalid URI pattern for Insert Operation.");
        }
        SQLiteDatabase db = database.getWritableDatabase();
        long newVendorLocalID = db.insert(VendorsTable.VENDORS_TABLE_NAME, null, contentValues);

        getContext().getContentResolver().notifyChange(uri, null);
        Uri newVendorURI = Uri.parse(VendorsContentProviderUtils.VENDORS_CONTENT_URI.toString()+"/"+newVendorLocalID);
        Toast.makeText(getContext(), "Added Vendor URI:: "+newVendorURI.toString(), Toast.LENGTH_LONG).show();
        return newVendorURI;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
