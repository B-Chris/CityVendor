package com.application.ncg.cityvendorappsuite;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VendorMultiPartRequest extends Request<String> {


    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity multiPartEntity;


    private static final String EMAIL_PART_NAME = "email";
    private static final String REG_ID_PART_NAME = "reg_id";
    private static final String APP_NAME_PART_NAME = "app_name";
    //this is the part that contains the JSON-string description of the test (actual payload)
    private static final String TEST_DESCRIPTION_PART_NAME = "description";

    private Response.Listener<String> mListener;
    Response.ErrorListener errorListener;
    private String regid, email, app_name;

    public VendorMultiPartRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
    }

    public VendorMultiPartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, String email, String regid, String app_name) {
        super(Method.POST, url, errorListener);
        this.errorListener = errorListener;
        this.mListener = listener;
        this.regid = regid;
        this.email = email;
        this.app_name = app_name;

        buildMultipartEntity();
    }

    private void buildMultipartEntity() {
        try {
            entity.addPart(EMAIL_PART_NAME, new StringBody(email, ContentType.TEXT_PLAIN));
            entity.addPart(APP_NAME_PART_NAME, new StringBody(app_name, ContentType.TEXT_PLAIN));
            entity.addPart(REG_ID_PART_NAME, new StringBody(regid, ContentType.TEXT_PLAIN));
            entity.setBoundary("-------------------");
            multiPartEntity = entity.build();
        } catch (Exception e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        String entityString = multiPartEntity.getContentType().getValue();
        VolleyLog.v("Entity String", entityString);

        return entityString;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            multiPartEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
            VolleyLog.e("IOException writing to ByteArrayOutputStream", e);
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String serverResponse = new String(response.data);
        return Response.success(serverResponse, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }


    @Override
    public void deliverError(VolleyError error) {
        errorListener.onErrorResponse(error);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e("Vendors", "Volley Error", volleyError);
        return super.parseNetworkError(volleyError);
    }

}
