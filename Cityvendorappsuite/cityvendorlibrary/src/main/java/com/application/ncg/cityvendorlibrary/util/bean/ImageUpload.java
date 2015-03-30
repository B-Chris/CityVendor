package com.application.ncg.cityvendorlibrary.util.bean;

/**
 * Created by Chris on 2015-03-11.
 */
public class ImageUpload {
/*
    public interface ImageUploadListener {
        public void onImageUploaded(ResponseDTO response);
        public void onUploadError();
        }

    static final String LOGTAG = "ImageUpload";
    static PhotoUploadDTO photoUpload;
    static List<File> mFiles;
    static ImageUploadListener imageUploadListener;
    static ResponseDTO response;

    public static void upload(PhotoUploadDTO dto, List<File> files, Context ctx,
                              ImageUploadListener listener) {
        photoUpload = dto;
        mFiles = files;
        imageUploadListener = listener;
        if (!BaseVolley.checkNetworkOnDevice(ctx)) {
            return;
        }
        Log.i(LOGTAG, "starting image upload");
        new ImageUploadTask().execute();
    }
    static class ImageUploadTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            InputStream is = null;
            String responseJSON = null;
            try {
                response = new ResponseDTO();
                MultipartEntity reqEntity = null;
                try {
                    reqEntity = new MultipartEntity();
                } catch (Exception e){
                    Log.e(LOGTAG, "MultiPartEntity Error, pointing somewhere it should not be", e);
                    throw new Exception();
                }
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Statics.URL + "photo");
                Log.d(LOGTAG, "sending image upload to:" + Statics.URL + "photo");

                Gson gson = new Gson();
                String json = gson.toJson(photoUpload);
                Log.e(LOGTAG, "json to be sent: " + gson.toJson(photoUpload));
                reqEntity.addPart("JSON", new StringBody(json));

                int idx = 1;
                for (File file: mFiles) {
                    FileBody fileBody = new FileBody(file);
                    reqEntity.addPart("ImageFile" + idx, fileBody);
                    idx++;
                }
                httpPost.setEntity(reqEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity resEntity = httpResponse.getEntity();

                is = resEntity.getContent();
                int size = 0;
                ByteArrayBuffer bab = new ByteArrayBuffer(9000);
                byte[] buffer = new byte[9000];
                while ((size = is.read(buffer ,0, buffer.length)) != -1) {
                    bab.append(buffer, 0, size);
                }
                responseJSON = new String(bab.toByteArray());
                if (responseJSON != null) {
                    Log.w(LOGTAG, "Response from upload:\n" + responseJSON);
                    response = gson.fromJson(responseJSON, ResponseDTO.class);
                }
            } catch (Exception e){
                Log.e(LOGTAG, "upload failed", e);
                return 808;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 0;
        }
        @Override
        protected void onPostExecute(Integer result) {
            Log.i(LOGTAG, "onPostExecute, image upload is now ending...");
            if (result > 0) {
                imageUploadListener.onUploadError();
                return;
            }
            if (response.getStatusCode() == null) {
                response.setStatusCode(0);
            }
            imageUploadListener.onImageUploaded(response);
        }
    }
*/


}
