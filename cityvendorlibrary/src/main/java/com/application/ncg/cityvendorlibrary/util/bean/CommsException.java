package com.application.ncg.cityvendorlibrary.util.bean;

/**
 * Created by Chris on 2015-03-06.
 */
public class CommsException extends Exception{

    private static final long serialVersionUID = 1L;
    int type;
    public CommsException(int type) {
        this.type = type;
    }

    public  static final int CONNECTION_ERROR = 1;
}
