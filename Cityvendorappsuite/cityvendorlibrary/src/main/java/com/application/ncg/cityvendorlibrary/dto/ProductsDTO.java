/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris
 */
public class ProductsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer productsID, vendorID, productImageID, consumerID, productsProductImageID;
    private String name;
    private String price;
    private List<ProductImageDTO> productimageList = new ArrayList<>();
    private List<VendorDTO> vendorList = new ArrayList<>();
    private List<ConsumerDTO> consumerList = new ArrayList<>();
    private List<ProductsProductImageDTO> productsproductimageList = new ArrayList<>();
    

    public ProductsDTO() {
    }


    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getProductImageID() {
        return productImageID;
    }

    public void setProductImageID(Integer productImageID) {
        this.productImageID = productImageID;
    }

    public Integer getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(Integer consumerID) {
        this.consumerID = consumerID;
    }

    public Integer getProductsProductImageID() {
        return productsProductImageID;
    }

    public void setProductsProductImageID(Integer productsProductImageID) {
        this.productsProductImageID = productsProductImageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public List<ProductsProductImageDTO> getProductsproductimageList() {
        return productsproductimageList;
    }

    public void setProductsproductimageList(List<ProductsProductImageDTO> productsproductimageList) {
        this.productsproductimageList = productsproductimageList;
    }

   
    
    

    
    
}
