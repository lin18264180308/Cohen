package com.mrong.lineview.common.entity;

/**
 * 产品规格类
 * 
 * @author 张裕宝
 */
public class Products {

    private Integer id;
    // 产品名称
    private String productName;
    // 详细信息
    private String productDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

}
