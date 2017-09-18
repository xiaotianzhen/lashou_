package com.yicooll.dong.lashou.bean;

import java.io.Serializable;

/**
 * Created by 45990 on 2017/9/7.
 */

public class Goods implements Serializable {

    private String id;
    private String categoryId;
    private String shopId;
    private String subCategoryId;
    private String cityId;
    private String title;
    private String sortTitle;
    private String imgUrl;
    private String startTime;
    private String value;
    private String price;
    private String ribat;
    private String bought;
    private String maxQuota;
    private String post;
    private String soldOut;
    private String tip;
    private String endTime;
    private String detail;
    private boolean isRefund;
    private boolean isOverTime;
    private String minquota;
    private Shop shop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRibat() {
        return ribat;
    }

    public void setRibat(String ribat) {
        this.ribat = ribat;
    }

    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public String getMaxQuota() {
        return maxQuota;
    }

    public void setMaxQuota(String maxQuota) {
        this.maxQuota = maxQuota;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(String soldOut) {
        this.soldOut = soldOut;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }

    public boolean isOverTime() {
        return isOverTime;
    }

    public void setOverTime(boolean overTime) {
        isOverTime = overTime;
    }

    public String getMinquota() {
        return minquota;
    }

    public void setMinquota(String minquota) {
        this.minquota = minquota;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        shop = shop;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", subCategoryId='" + subCategoryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", title='" + title + '\'' +
                ", sortTitle='" + sortTitle + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", value='" + value + '\'' +
                ", price='" + price + '\'' +
                ", ribat='" + ribat + '\'' +
                ", bought='" + bought + '\'' +
                ", maxQuota='" + maxQuota + '\'' +
                ", post='" + post + '\'' +
                ", soldOut='" + soldOut + '\'' +
                ", tip='" + tip + '\'' +
                ", endTime='" + endTime + '\'' +
                ", detail='" + detail + '\'' +
                ", isRefund=" + isRefund +
                ", isOverTime=" + isOverTime +
                ", minquota='" + minquota + '\'' +
                ", mShop=" + shop +
                '}';
    }
}
