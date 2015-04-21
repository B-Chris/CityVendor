/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto.transfer;

import com.application.ncg.cityvendorlibrary.dto.ConsumerDTO;
import com.application.ncg.cityvendorlibrary.dto.ConsumerVendorDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductImageDTO;
import com.application.ncg.cityvendorlibrary.dto.ProductsProductImageDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.dto.VendorSiteDTO;
import com.application.ncg.cityvendorlibrary.util.PhotoCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chris
 */
public class ResponseDTO implements Serializable{
    
    private Integer statusCode;
    private String message;
    private Integer statusCountInPeriod, goodCount, badCount;
    private String sessionID, GCMRegistrationID, fileString;
    private double elapsedRequestTimeInSeconds;
    private PhotoCache photoCache;
    private Date lastCacheDate, startDate, endDate;

    private List<ConsumerVendorDTO> consumervendorList = new ArrayList<>();
    private List<ProductsProductImageDTO> prodctsproductimageList = new ArrayList<>();
    private List<ProductImageDTO> productimageList = new ArrayList<>();
    private List<VendorDTO> vendorList = new ArrayList<>();
    private List<ConsumerDTO> consumerList = new ArrayList<>();
    private List<PhotoUploadDTO> photouploadList = new ArrayList<>();
    private List<VendorSiteDTO> vendorsiteList = new ArrayList<>();
    private VendorDTO vendor;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCountInPeriod() {
        return statusCountInPeriod;
    }

    public void setStatusCountInPeriod(Integer statusCountInPeriod) {
        this.statusCountInPeriod = statusCountInPeriod;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getGCMRegistrationID() {
        return GCMRegistrationID;
    }

    public void setGCMRegistrationID(String GCMRegistrationID) {
        this.GCMRegistrationID = GCMRegistrationID;
    }

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public double getElapsedRequestTimeInSeconds() {
        return elapsedRequestTimeInSeconds;
    }

    public void setElapsedRequestTimeInSeconds(double elapsedRequestTimeInSeconds) {
        this.elapsedRequestTimeInSeconds = elapsedRequestTimeInSeconds;
    }

    public List<ConsumerVendorDTO> getConsumervendorList() {
        return consumervendorList;
    }

    public void setConsumervendorList(List<ConsumerVendorDTO> consumervendorList) {
        this.consumervendorList = consumervendorList;
    }

    public List<ProductsProductImageDTO> getProdctsproductimageList() {
        return prodctsproductimageList;
    }

    public void setProdctsproductimageList(List<ProductsProductImageDTO> prodctsproductimageList) {
        this.prodctsproductimageList = prodctsproductimageList;
    }

    public List<ProductImageDTO> getProductimageList() {
        return productimageList;
    }

    public void setProductimageList(List<ProductImageDTO> productimageList) {
        this.productimageList = productimageList;
    }

    public List<VendorDTO> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorDTO> vendorList) {
        this.vendorList = vendorList;
    }

    public List<ConsumerDTO> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<ConsumerDTO> consumerList) {
        this.consumerList = consumerList;
    }

    public List<PhotoUploadDTO> getPhotouploadList() {
        return photouploadList;
    }

    public void setPhotouploadList(List<PhotoUploadDTO> photouploadList) {
        this.photouploadList = photouploadList;
    }

    public PhotoCache getPhotoCache() {
        return photoCache;
    }

    public void setPhotoCache(PhotoCache photoCache) {
        this.photoCache = photoCache;
    }

    public Date getLastCacheDate() {
        return lastCacheDate;
    }

    public void setLastCacheDate(Date lastCacheDate) {
        this.lastCacheDate = lastCacheDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }
}
