package com.application.ncg.cityvendorlibrary.services;

import com.application.ncg.cityvendorlibrary.dto.transfer.RequestDTO;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Chris on 2015-03-07.
 */
public class RequestCacheEntry implements Serializable, Comparable<RequestCacheEntry> {

    private Date dateRequested, dateUploaded;
    private RequestDTO request;
    private int attemptCount;


    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    @Override
    public int compareTo(RequestCacheEntry another) {
        return dateRequested.compareTo(another.dateRequested);
    }

}
