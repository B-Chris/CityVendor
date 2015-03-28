/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.ncg.cityvendorlibrary.dto;

import java.io.Serializable;

/**
 *
 * @author Chris
 */
public class ProductsProductImageDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer productsproductImageID, productImageID, productsID;
    private ProductImageDTO productImage;
    private ProductsDTO products;

    public ProductsProductImageDTO() {
    }



    public Integer getProductsproductImageID() {
        return productsproductImageID;
    }

    public void setProductsproductImageID(Integer productsproductImageID) {
        this.productsproductImageID = productsproductImageID;
    }

    public Integer getProductImageID() {
        return productImageID;
    }

    public void setProductImageID(Integer productImageID) {
        this.productImageID = productImageID;
    }

    public Integer getProductsID() {
        return productsID;
    }

    public void setProductsID(Integer productsID) {
        this.productsID = productsID;
    }

    public ProductImageDTO getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImageDTO productImage) {
        this.productImage = productImage;
    }

    public ProductsDTO getProducts() {
        return products;
    }

    public void setProducts(ProductsDTO products) {
        this.products = products;
    }
}
