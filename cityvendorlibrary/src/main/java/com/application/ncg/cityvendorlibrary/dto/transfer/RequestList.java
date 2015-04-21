/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto.transfer;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Chris
 */
public class RequestList implements Serializable{
    private List<RequestDTO> requests;

    public List<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDTO> requests) {
        this.requests = requests;
    }
    
}
