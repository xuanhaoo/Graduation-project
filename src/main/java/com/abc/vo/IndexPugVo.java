package com.abc.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年05月04 11:35
 */
public class IndexPugVo implements Serializable {

    private int contentId;
    private String title;
    private String picSrc;
    private String cityName;
    private String cityNamePinyin;
    private Date time;

    private Double weight;


    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityNamePinyin() {
        return cityNamePinyin;
    }

    public void setCityNamePinyin(String cityNamePinyin) {
        this.cityNamePinyin = cityNamePinyin;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
