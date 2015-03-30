/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto.transfer;


import com.application.ncg.cityvendorlibrary.dto.ConsumerDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductsProductImageDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chris
 */
public class PhotoUploadDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer photouploadID, consumerID, vendorID, productsProductImageID;
    public static final int VENDOR_PICTURE = 1, CONSUMER_PICTURE = 2, PRODUCTS_PRODUCT_IMAGE = 3;
    private List<String> tags;
    private Float accuracy;
    private Double latitude;
    private Double longitude;
    private Integer thumbFlag;
    private Date dateThumbUploaded, dateUploaded, dateTaken;
    private boolean isFullPicture, isVendorPicture;
    private Integer pictureType;
    private String uri, name, surname, streetAddress, thumbFilePath;
    private ConsumerDTO consumer;
    private VendorDTO vendor;
    private ProductsProductImageDTO productsProductImage;

    public interface PhotoUploadedListener {
        public void onPhotoUploaded();
        public void onPhotoUploadFailed();
    }

    public PhotoUploadDTO() {
    }


    public String getThumbFilePath() {
        return thumbFilePath;
    }

    public void setThumbFilePath(String thumbFilePath) {
        this.thumbFilePath = thumbFilePath;
    }

    public Date getDateThumbUploaded() {
        return dateThumbUploaded;
    }

    public void setDateThumbUploaded(Date dateThumbUploaded) {
        this.dateThumbUploaded = dateThumbUploaded;
    }

    public Integer getPhotouploadID() {
        return photouploadID;
    }

    public void setPhotouploadID(Integer photouploadID) {
        this.photouploadID = photouploadID;
    }

    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
        this.consumerID = consumerID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getProductsProductImageID() {
        return productsProductImageID;
    }

    public void setProductsProductImageID(Integer productsProductImageID) {
        this.productsProductImageID = productsProductImageID;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getThumbFlag() {
        return thumbFlag;
    }

    public void setThumbFlag(Integer thumbFlag) {
        this.thumbFlag = thumbFlag;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Integer getPictureType() {
        return pictureType;
    }

    public void setPictureType(Integer pictureType) {
        this.pictureType = pictureType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public static int getVendorPicture() {
        return VENDOR_PICTURE;
    }

    public static int getConsumerPicture() {
        return CONSUMER_PICTURE;
    }

    public static int getProductsProductImage() {
        return PRODUCTS_PRODUCT_IMAGE;
    }

    public void setProductsProductImage(ProductsProductImageDTO productsProductImage) {
        this.productsProductImage = productsProductImage;
    }

    public ConsumerDTO getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerDTO consumer) {
        this.consumer = consumer;
    }

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public boolean isFullPicture() {
        return isFullPicture;
    }

    public void setFullPicture(boolean isFullPicture) {
        this.isFullPicture = isFullPicture;
    }

    public boolean isVendorPicture() {
        return isVendorPicture;
    }

    public void setVendorPicture(boolean isVendorPicture) {
        this.isVendorPicture = isVendorPicture;
    }
}

