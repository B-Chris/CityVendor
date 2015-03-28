package com.application.ncg.cityvendorappsuite.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This is a utility class for creating and upgrading database tables for the underlying SQLite
 * Created by Chris on 2015-03-07.
 */
public class VendorsDatabaseHelper extends SQLiteOpenHelper {
 private static final int version = 1;
    private static final String DATABASE_FILE = "vendors.db";
    public VendorsDatabaseHelper(Context context){
        super(context,DATABASE_FILE, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //here we call the table oncreate
        VendorsTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //here we call table onupgrade
        VendorsTable.onUpgrade(sqLiteDatabase);
    }
}
