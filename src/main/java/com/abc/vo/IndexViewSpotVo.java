package com.abc.vo;

import java.io.Serializable;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年05月04 12:11
 */
public class IndexViewSpotVo implements Serializable {

    private int addressId;

    private String spotName;

    private String pic;

    private String addressName;
    private String addressNamePinyin;

    private Double weight;

    public String getPic() {
        return pic;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAddressNamePinyin() {
        return addressNamePinyin;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public void setAddressNamePinyin(String addressNamePinyin) {
        this.addressNamePinyin = addressNamePinyin;
    }
}
