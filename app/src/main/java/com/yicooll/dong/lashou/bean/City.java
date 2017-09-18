package com.yicooll.dong.lashou.bean;

/**
 * Created by 45990 on 2017/9/6.
 */

public class City {
    private String id;
    private String name;
    private String sortkey;

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

    public String getSortkey() {
        return sortkey;
    }

    public void setSortkey(String sortkey) {
        this.sortkey = sortkey;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sortkey='" + sortkey + '\'' +
                '}';
    }
}
