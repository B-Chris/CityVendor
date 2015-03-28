package com.application.ncg.cityvendorlibrary.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Chris on 2015-03-11.
 */
public class PhotoUploadService extends IntentService {
    public PhotoUploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
    /*
    UploadListener uploadListener;
    int count, index, retryCount;
    List<PhotoUploadDTO> uploadedList = new ArrayList<>();
    static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    static List<PhotoUploadDTO> list;
    WebCheckResult webCheckResult;
    static final String LOG = PhotoUploadService.class.getSimpleName();
    static final int MAX_RETIRES = 3;
    private final IBinder mBinder = new LocalBinder();
    List<PhotoUploadDTO> failedUploads = new ArrayList<>();

    public PhotoUploadService() {
        super("PhotoUploadService");
    }

    public void uploadCachedPhotos(UploadListener listener) {
        uploadListener = listener;
        Log.d(LOG, "uploadCachedPhotos, uploading will start once wifi is connected");
        PhotoCacheUtil.getCachedPhotos(getApplicationContext(), new PhotoCacheUtil.PhotoCacheListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {
                Log.e(LOG, "returning cached photo" + response.getPhotouploadList().size());
                list = response.getPhotouploadList();
                if (list.isEmpty()) {
                    Log.w(LOG, "there is no cached photo ready for download");
                    if (uploadListener != null) {
                        uploadListener.onUploadsComplete(0);
                    }
                    return;
                }
                getLog(response);
                webCheckResult = WebCheck.checkNetworkAvailability(getApplicationContext());
                if (!webCheckResult.isWifiConnected()) {
                    Log.e(LOG, "uploadCachedPhotos, not connected to wifi");
                    return;
                }
                onHandleIntent(null);
            }

            @Override
            public void onDataCached() {

            }

            @Override
            public void onError() {

            }
        });
    }

    private static void getLog(ResponseDTO cache) {
        StringBuilder sb = new StringBuilder();
        sb.append("Photos currently in the cache")
                .append(cache.getPhotouploadList().size()).append("\n");
        for (PhotoUploadDTO pu : cache.getPhotouploadList()) {
            sb.append("++").append(pu.getDateTaken().toString()).append("latitude:").append(pu.getLatitude());
            sb.append("longitude: ").append(pu.getLongitude()).append("acc: ").append(pu.getAccuracy());
            if (pu.getDateThumbUploaded() != null) {
                sb.append(" ").append(sdf.format(pu.getDateThumbUploaded())).append("\n");
            } else {
                sb.append("there has not been an upload\n");
            }
        }
        Log.w(LOG, sb.toString());
    }


    public interface UploadListener {
        public void onUploadsComplete(int count);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
    Log.w(LOG, "onHandleIntent FIRED");
        if (list == null){
            uploadCachedPhotos(uploadListener);
            return;
        }
        retryCount = 0;
        webCheckResult = WebCheck.checkNetworkAvailability(getApplicationContext());
        if (webCheckResult.isWifiConnected()) {
            controlThumbUploads();
        }
    }

    private void controlThumbUploads() {
        if (index < list.size()) {
            if (list.get(index).getDateThumbUploaded() == null) {
                executeThumbUploaded(list.get(index));
            } else {
                index++;
                controlThumbUploads();
                return;
            }
        }
        if (index == list.size()) {
            if (failedUploads.isEmpty()) {
                Log.w(LOG, "cleaning cache");
                PhotoCacheUtil.clearCache(getApplicationContext(), uploadedList);
                if (uploadListener != null) {
                    uploadListener.onUploadsComplete(uploadedList.size());
                } else {
                    attemptFailedUploads();
                }
            }
        }
    }

    private void executeThumbUploaded(final PhotoUploadDTO dto) {
        Log.d(LOG, "executeThumbUpload, vendorID:" + dto.getVendorID());
        dto.setFullPicture(false);
        if (dto.getPictureType() == 0) dto.setPictureType(PhotoUploadDTO.VENDOR_PICTURE);
        final long start = System.currentTimeMillis();
        PictureUtil.uploadImage(dto, false, getApplicationContext(), new PhotoUploadDTO.PhotoUploadedListener() {
            @Override
            public void onPhotoUploaded() {
                long end = System.currentTimeMillis();
                Log.i(LOG, "thumbnail has uploaded, elapsed: " + Util.getElapsed(start, end) + "seconds");
                dto.setDateThumbUploaded(new Date());
                uploadedList.add(dto);
                index++;
                controlThumbUploads();
            }

            @Override
            public void onPhotoUploadFailed() {
                Log.e(LOG, "onPhotoUploadFailed, photos failed to upload");
                failedUploads.add(dto);
                index++;
                controlThumbUploads();
            }
        });

    }

    public int getIndex() {return index; }

    private void attemptFailedUploads() {
        retryCount++;
        if (retryCount > MAX_RETIRES) {
            if (uploadListener != null) {
                uploadListener.onUploadsComplete(uploadedList.size());
            }
            return;
        }
        index = 0;
        list = failedUploads;
        failedUploads.clear();
        controlThumbUploads();
    }
    public class LocalBinder extends Binder {
        public PhotoUploadService getService() {
            return PhotoUploadService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    */
}
