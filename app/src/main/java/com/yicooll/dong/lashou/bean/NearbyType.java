package com.yicooll.dong.lashou.bean;

/**
 * Created by 45990 on 2017/9/11.
 */

public class NearbyType {

    private  int categoryId;
    private String typeName;
    private int typeIcon;
    private int nameColor;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(int typeIcon) {
        this.typeIcon = typeIcon;
    }

    public int getNameColor() {
        return nameColor;
    }

    public void setNameColor(int nameColor) {
        this.nameColor = nameColor;
    }


    @Override
    public String toString() {
        return "NearbyType{" +
                "categoryId=" + categoryId +
                ", typeName='" + typeName + '\'' +
                ", typeIcon=" + typeIcon +
                ", nameColor=" + nameColor +
                '}';
    }
}
