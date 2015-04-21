package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.transfer.PhotoUploadDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2015-03-12.
 */
public class PhotoCacheUtil {
    static PhotoCacheListener photoCacheListener;
    static ResponseDTO response = new ResponseDTO();
    static PhotoUploadDTO photoUpload;
    static Context ctx;
    static final String JSON_PHOTO = "photos.json";

    public interface PhotoCacheListener {
        public void onFileDataDeserialized(ResponseDTO response);
        public void onDataCached();
        public void onError();
    }

    public interface PhotoCacheRetrieveListener {
        public void onFileDataDeserialized(ResponseDTO response);
        public void onDataCached();
        public void onError();
    }

    static PhotoCacheRetrieveListener photoCacheRetrieveListener =
            new PhotoCacheRetrieveListener() {
                @Override
                public void onFileDataDeserialized(ResponseDTO response) {

                }

                @Override
                public void onDataCached() {

                }

                @Override
                public void onError() {

                }
            };

    public static void cachePhoto(Context context, final PhotoUploadDTO photo, PhotoCacheListener listener) {
    photoUpload = photo;
    response.setLastCacheDate(new Date());
    photoCacheListener = listener;
        ctx = context;
        new CacheRetrieveForUpdateTask().execute();
    }

    public static void getCachedPhotos(Context context, PhotoCacheListener listener) {
        photoCacheListener = listener;
        ctx = context;
        new CacheRetrieveTask().execute();
    }

    public static void clearCache(Context context, final List<PhotoUploadDTO> uploadedList) {
        ctx = context;
        getCachedPhotos(context, new PhotoCacheListener() {

            @Override
            public void onFileDataDeserialized(ResponseDTO r) {
                List<PhotoUploadDTO> pending = new ArrayList<>();
                for (PhotoUploadDTO pu : r.getPhotouploadList()) {
                    for (PhotoUploadDTO p : uploadedList) {
                        if (p.getThumbFilePath().equalsIgnoreCase(pu.getThumbFilePath())) {
                            pu.setDateUploaded(new Date());
                            File f = new File(p.getThumbFilePath());
                            if (f.exists()) {
                                boolean del = f.delete();
                                Log.w(LOG, "deleted image file:" + p.getThumbFilePath() + " " + del);

                            }
                        }
                    }
                }
                for (PhotoUploadDTO p : r.getPhotouploadList()) {
                    if (p.getDateUploaded() == null) {
                        pending.add(p);
                    }
                }
                r.setPhotouploadList(pending);
                response = r;
                Log.i(LOG, "after clearing cache, pending photos:" + pending.size() + "writing new cache");
            }

            @Override
            public void onDataCached() {

            }

            @Override
            public void onError() {

            }
        });
    }

    static class CacheTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            String json;
            File file;
            FileOutputStream outputStream;
            try{
                json = gson.toJson(response);
                outputStream = ctx.openFileOutput(JSON_PHOTO, Context.MODE_PRIVATE);
                write(outputStream, json);
                file = ctx.getFileStreamPath(JSON_PHOTO);
                if (file != null) {
                    Log.e(LOG, "Photo in cache, path: "+ file.getAbsolutePath() +
                    "- length: " + file.length() + "photos: " + response.getPhotouploadList().size());
                    }
                StringBuilder sb = new StringBuilder();
                sb.append("photo in cache");
                for (PhotoUploadDTO pu : response.getPhotouploadList()) {
                    sb.append("++").append(pu.getThumbFilePath())
                            .append("vendorID: " + pu.getVendorID())
                            .append("_");
                }
                sb.append("####");
                Log.w(LOG, sb.toString());
            } catch (IOException e){
                Log.e(LOG, "Failed to catch data", e);
                return 9;
            }
            return 0;
        }
        private void write(FileOutputStream outputStream, String json) throws IOException {
            outputStream.write(json.getBytes());
            outputStream.close();
        }

        @Override
        protected void onPostExecute(Integer v) {
            if (photoCacheListener != null){
                if (v > 0) {
                    photoCacheListener.onError();
                } else {
                    photoCacheListener.onDataCached();
                }
            }
        }

    }
    static class CacheRetrieveTask extends AsyncTask<Void, Void, ResponseDTO> {

        private ResponseDTO getData(FileInputStream stream) throws IOException{
            String json = getStringFromInputStream(stream);
            ResponseDTO response = gson.fromJson(json, ResponseDTO.class);
            return response;
        }

        @Override
        protected ResponseDTO doInBackground(Void... voids) {
            ResponseDTO response = new ResponseDTO();
            response.setPhotouploadList(new ArrayList<PhotoUploadDTO>());
            FileInputStream stream;
            try{
                stream = ctx.openFileInput(JSON_PHOTO);
                response = getData(stream);
                Log.i(LOG, "photo cache retrieved, photo:" + response.getPhotouploadList().size());
                 } catch (FileNotFoundException e) {
                Log.w(LOG, "cache file has not been found, not initialised yet");
                return response;
            } catch (IOException e) {
                Log.e(LOG, "doInBackground - returning a new response object, type = Photo");
                 }
            return response;
        }

        @Override
        protected void onPostExecute(ResponseDTO r) {
            if (photoCacheListener == null)
                return;
            else
                photoCacheListener.onFileDataDeserialized(r);
        }
    }

    private static String getStringFromInputStream(InputStream is) throws IOException{

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine()) != null) {
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

    static class CacheRetrieveForUpdateTask extends AsyncTask<Void, Void, ResponseDTO> {

        private ResponseDTO getData(FileInputStream stream) throws IOException {
            String json = getStringFromInputStream(stream);
            ResponseDTO response = gson.fromJson(json, ResponseDTO.class);
            return response;
        }

        @Override
        protected ResponseDTO doInBackground(Void... voids) {
            ResponseDTO response = new ResponseDTO();
            response.setPhotouploadList(new ArrayList<PhotoUploadDTO>());
            FileInputStream stream;
            try {
                stream = ctx.openFileInput(JSON_PHOTO);
                response = getData(stream);
                } catch (FileNotFoundException e) {
                Log.w(LOG, "cache file not found, has not been initialized yet");
                return response;
            } catch (IOException e) {
                Log.d(LOG, "doInBackground - returning a new response object");
            }
            return response;
        }

        @Override
        protected void onPostExecute(ResponseDTO r) {
            if (r.getPhotouploadList() == null) {
                r.setPhotouploadList(new ArrayList<PhotoUploadDTO>());
            }
            r.getPhotouploadList().add(photoUpload);
            response = r;
            new CacheTask().execute();
        }
    }

    static final String LOG = PhotoCacheUtil.class.getSimpleName();
    static final Gson gson = new Gson();
}
