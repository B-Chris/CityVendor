package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.application.ncg.cityvendorlibrary.services.RequestCache;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by Chris on 2015-03-08.
 */
public class CacheUtil {

    public interface CacheUtilListener {
        public void onFileDataDeserialized(ResponseDTO response);
        public void onDataCached();
        public void onError();
    }

    public interface CacheRequestListener {
        public void onDataCached();
        public void onRequestCacheReturned(RequestCache cache);
        public void onError();
    }

    public interface CacheVendorListener {
        public void onVendorReturnedFromCache(VendorDTO vendor);
        public void onDataCached();
        public void onError();
    }

    static CacheUtilListener utilListener;
    static CacheRequestListener cacheListener;
    static CacheVendorListener vendorListener;

    public static final int CACHE_VENDOR_DATA = 1, CACHE_REQUEST = 2;
    static int dataType;
    static Integer vendorID;
    static Context ctx;
    static VendorDTO vendor;
    static ResponseDTO response;
    static SessionPhoto sessionPhoto;
    static RequestCache requestCache;
    static  Double latitude, longitude;

    static final String JSON_DATA = "data.json", JSON_VENDOR_DATA = "vendor_data",
                        JSON_REQUEST = "requestCache.json", JSON_VENDOR_SITE = ".site";

    public static void cacheRequest(Context context, RequestCache cache, CacheRequestListener listener) {
        requestCache = cache;
        dataType = CACHE_REQUEST;
        cacheListener = listener;
        ctx = context;
        new CacheTask().execute();
    }

    public static void cacheVendorData(Context context, ResponseDTO r, Integer vID, CacheUtilListener cacheUtilListener) {
        dataType = CACHE_VENDOR_DATA;
        response = r;
        response.setLastCacheDate(new Date());
        utilListener = cacheUtilListener;
        vendorID = vID;
        ctx = context;
        new CacheTask().execute();
    }

    public static void getCachedData(Context context, int type, CacheUtilListener cacheUtilListener) {
        dataType = type;
        utilListener = cacheUtilListener;
        ctx = context;
        new CacheRetrieveTask().execute();
        }

    public static void getCachedRequests(Context context, CacheRequestListener listener) {
        dataType = CACHE_REQUEST;
        cacheListener = listener;
        ctx = context;
        new CacheRetrieveRequestTask().execute();
    }

    public static void getCachedVendorData(Context context, Integer id, CacheUtilListener cacheUtilListener) {
        Log.d(LOG, "getting cached vendor data");
        dataType = CACHE_VENDOR_DATA;
        utilListener = cacheUtilListener;
        ctx = context;
        vendorID = id;
        new CacheRetrieveTask().execute();
    }

    static class CacheTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            String json = null;
            File file = null;
            FileOutputStream outputStream;
            try {
                switch (dataType) {
                    case CACHE_REQUEST:
                        Log.e(LOG, "requesting file before initial cache, list of requests:" + requestCache.getRequestCacheEntryList().size());
                        json = gson.toJson(requestCache);
                        outputStream = ctx.openFileOutput(JSON_REQUEST, Context.MODE_PRIVATE);
                        write(outputStream, json);
                        file = ctx.getFileStreamPath(JSON_REQUEST);;
                        if (file != null) {
                            Log.e(LOG, "Request cache has been written, this is the path:" +
                                    file.getAbsolutePath() + "length:" + file.length());
                        }
                        break;
                    case CACHE_VENDOR_DATA:
                        json = gson.toJson(response);
                        outputStream = ctx.openFileOutput(JSON_VENDOR_DATA + vendorID + ".json", Context.MODE_PRIVATE);
                        write(outputStream, json);
                        file = ctx.getFileStreamPath(JSON_VENDOR_DATA + vendorID + ".json");
                        if (file != null) {
                            Log.e(LOG, "written cache for VENDOR with path:" + vendorID + ".json"
                            + "length:" + file.length());
                        }
                    default:
                        Log.e(LOG, "DataType wasn't found, therefore nothing was done");
                        break;
                }
            } catch (Exception e) {
                Log.e(LOG, "Failed to cache data", e);
            }
            return 0;
        }
        private void write(FileOutputStream outputStream, String json) throws IOException{
            outputStream.write(json.getBytes());
            outputStream.close();
        }
        @Override
        protected void onPostExecute(Integer v) {
            if (utilListener != null) {
                if (v > 0) {
                    utilListener.onError();
                } else {
                    utilListener.onDataCached();
                }
            }
            if (cacheListener  != null) {
                if (v > 0) {
                    cacheListener.onError();
                } else {
                    cacheListener.onDataCached();
                }
            }
            if (vendorListener != null) {
                if (v > 0) {
                    vendorListener.onError();
                } else {
                    vendorListener.onDataCached();
                }
            }
        }

    }

    static class CacheRetrieveTask extends AsyncTask<Void, Void, ResponseDTO> {

        private ResponseDTO getData(FileInputStream stream) throws IOException {
            String json = getStringFromInputStream(stream);
            ResponseDTO response = gson.fromJson(json, ResponseDTO.class);
            return response;
        }
        @Override
        protected ResponseDTO doInBackground(Void... voids) {
            ResponseDTO response = new ResponseDTO();
            FileInputStream stream;
            try {
                switch (dataType) {
                    case CACHE_VENDOR_DATA:
                        stream = ctx.openFileInput(JSON_VENDOR_DATA + vendorID + ".json");
                        response = getData(stream);
                        Log.i(LOG, "vendor Cache has been Retrieved");
                        break;
                    case CACHE_REQUEST:
                        stream = ctx.openFileInput(JSON_REQUEST);
                        response = getData(stream);
                        Log.i(LOG, "request Cache has been retrieved");
                        break;
                }
            } catch (FileNotFoundException e) {
                Log.d(LOG,"cache file has not been found, now returning a new response object, type: " + dataType);
            } catch (IOException e) {
                Log.v(LOG, "Failed to retrieve cache", e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(ResponseDTO response) {
            if (utilListener == null) {
                utilListener.onFileDataDeserialized(response);
                return;
            }
        }
    }

    static class CacheRetrieveVendor extends AsyncTask<Void, Void, VendorDTO> {

        private VendorDTO getData(FileInputStream stream) throws IOException{
            String json = getStringFromInputStream(stream);
            VendorDTO vendor = gson.fromJson(json, VendorDTO.class);
            return vendor;
        }
        @Override
        protected VendorDTO doInBackground(Void... voids) {
            VendorDTO vend = null;
            FileInputStream stream;
            try {
                stream = ctx.openFileInput(JSON_VENDOR_SITE + latitude +  longitude + ".json");
                vend = getData(stream);
                Log.i(LOG, "vendor cache has been retrieved");
            } catch (FileNotFoundException e) {
                Log.d(LOG, "the vendor cache file has not been found, not yet initialised");
                 } catch (IOException e) {
                Log.v(LOG, "unable to retrieve cache", e);
            }
            return vend;
        }

        @Override
        protected void onPostExecute(VendorDTO result) {
            if (vendorListener == null){
                if (result != null) {
                    vendorListener.onVendorReturnedFromCache(result);
                } else {
                    Log.e(LOG, "Cache was not found");
                    vendorListener.onError();
                }
                return;
            }
        }

    }

    static class CacheRetrieveRequestTask extends AsyncTask<Void, Void, RequestCache> {

        private RequestCache getData(FileInputStream stream) throws IOException{
            String json = getStringFromInputStream(stream);
            RequestCache cache = gson.fromJson(json, RequestCache.class);
            return cache;
        }
        @Override
        protected RequestCache doInBackground(Void... voids) {
            RequestCache cache = null;
            FileInputStream stream;
            try{
                stream = ctx.openFileInput(JSON_REQUEST);
                cache = getData(stream);
                Log.i(LOG, "request cache has been received");
            } catch (FileNotFoundException e){
                Log.d(LOG, "cache file has not been found. cache file has not been initialised yet");
                cache = new RequestCache();
            } catch(IOException e) {
                Log.v(LOG, "Failed to retrieve cache", e);
            }

            return cache;
        }
        @Override
        protected void onPostExecute(RequestCache v){
            if (cacheListener == null) {
                if (v != null) {
                    cacheListener.onRequestCacheReturned(v);
                } else {
                    Log.e(LOG, "There is no cache, null object response returned from util");
                    cacheListener.onError();
                }
            }
        }
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        String json = sb.toString();
        return json;

    }


    static final  String LOG = CacheUtil.class.getSimpleName();
    static final Gson gson = new Gson();

}
