package com.application.ncg.cityvendorlibrary.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2015-03-07.
 */
public class RequestCache implements Serializable{
    private List<RequestCacheEntry> requestCacheEntryList = new ArrayList<>();

    public List<RequestCacheEntry> getRequestCacheEntryList() {
        return requestCacheEntryList;
    }

    public void setRequestCacheEntryList(List<RequestCacheEntry> requestCacheEntryList){
        this.requestCacheEntryList = requestCacheEntryList;
    }
}
