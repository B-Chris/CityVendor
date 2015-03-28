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
public class ProductImageDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer productimageID, productsID;
    private String uri;
    private ProductsDTO products;
    private List<ProductsProductImageDTO> prodctsproductimageList = new ArrayList<>();
    
    

    public ProductImageDTO() {
    }



    public Integer getProductimageID() {
        return productimageID;
    }

    public void setProductimageID(Integer productimageID) {
        this.productimageID = productimageID;
    }

    

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ProductsDTO getProducts() {
        return products;
    }

    public void setProducts(ProductsDTO products) {
        this.products = products;
    }

    public List<ProductsProductImageDTO> getProdctsproductimageList() {
        return prodctsproductimageList;
    }

    public void setProdctsproductimageList(List<ProductsProductImageDTO> prodctsproductimageList) {
        this.prodctsproductimageList = prodctsproductimageList;
    }

    
    
    
}
