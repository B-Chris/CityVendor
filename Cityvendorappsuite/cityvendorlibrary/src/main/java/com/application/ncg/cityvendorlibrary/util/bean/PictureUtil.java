package com.application.ncg.cityvendorlibrary.util.bean;

/**
 * Created by Chris on 2015-03-11.
 */
public class PictureUtil {
/*
    static final String LOG = PictureUtil.class.getSimpleName();
    private static void uploadImage(PhotoUploadDTO dto, boolean isFullPicture,
                                    Context ctx, final PhotoUploadDTO.PhotoUploadedListener listener){

        if (dto.getDateUploaded() != null) return;
        if (dto.getThumbFilePath() == null) return;
        File imageFile = new File(dto.getThumbFilePath());
        if (isFullPicture) {
            imageFile = new File(dto.getThumbFilePath());
        }
        Log.w(LOG, "file is about to be uploaded with length: " + imageFile.length() +
                " - " + imageFile.getAbsolutePath());
        List<File> files = new ArrayList<File>();
        if (imageFile.exists()) {
            files.add(imageFile);
            //set up
            ImageUpload.upload(dto, files, ctx,
                    new ImageUpload.ImageUploadListener(){
                        @Override
                    public void onUploadError() {
                            listener.onPhotoUploadFailed();
                            Log.e(LOG, "Error uploading - onUploadError");
                        }
                        @Override
                    public void onImageUploaded(ResponseDTO response) {
                            if (response.getStatusCode() == 0) {
                                listener.onPhotoUploaded();
                            } else {
                                Log.e(LOG, "there's an error uploading" + response.getMessage());
                            }
                        }
                    });
        }
    }
*/
}
