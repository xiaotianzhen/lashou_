package com.yicooll.dong.lashou.bean;

import java.io.Serializable;

/**
 * Created by 45990 on 2017/9/7.
 */

public class Shop implements Serializable {

    private String id;
    private String name;
    private String address;
    private String area;
    private String lon;
    private String lat;
    private String tel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
