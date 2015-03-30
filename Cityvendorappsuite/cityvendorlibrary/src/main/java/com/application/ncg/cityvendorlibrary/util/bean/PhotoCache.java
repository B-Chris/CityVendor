package com.application.ncg.cityvendorlibrary.util.bean;

import com.application.ncg.cityvendorlibrary.dto.transfer.PhotoUploadDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2015-03-08.
 */
public class PhotoCache {
    private List<PhotoUploadDTO> photoUploadList = new ArrayList<>();

    public List<PhotoUploadDTO> getPhotoUploadList() {
        return photoUploadList;
    }

    public void setPhotoUploadList(List<PhotoUploadDTO> photoUploadList) {
        this.photoUploadList = photoUploadList;
    }

}
