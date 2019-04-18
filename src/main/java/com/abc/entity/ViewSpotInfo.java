package com.abc.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("view_spot_info")
public class ViewSpotInfo extends Model<ViewSpotInfo> {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private String name;
  private String info;
  private String phone;

  @TableField("view_web")
  private String viewWeb;

  @TableField("spend_time")
  private String spendTime;

  private String picture;

  private String transport;

  @TableField("open_time")
  private String openTime;

  private String ticket;

  @TableField("area_type_one")
  private int areaTypeOne;

  @TableField("area_type_two")
  private int areaTypeTwo;

  @TableField("area_type_three")
  private int areaTypeThree;

  @TableField("create_date")
  private Date createDate;

  @TableField("update_time")
  private Date updateTime;

  @TableField(exist = false)
  private String areaOneName;

  @TableField(exist = false)
  private String areaTwoName;

  @TableField(exist = false)
  private String areaThreeName;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getViewWeb() {
    return viewWeb;
  }

  public void setViewWeb(String viewWeb) {
    this.viewWeb = viewWeb;
  }

  public String getSpendTime() {
    return spendTime;
  }

  public void setSpendTime(String spendTime) {
    this.spendTime = spendTime;
  }

  public String getTransport() {
    return transport;
  }

  public void setTransport(String transport) {
    this.transport = transport;
  }

  public String getOpenTime() {
    return openTime;
  }

  public void setOpenTime(String openTime) {
    this.openTime = openTime;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public int getAreaTypeOne() {
    return areaTypeOne;
  }

  public void setAreaTypeOne(int areaTypeOne) {
    this.areaTypeOne = areaTypeOne;
  }

  public int getAreaTypeTwo() {
    return areaTypeTwo;
  }

  public void setAreaTypeTwo(int areaTypeTwo) {
    this.areaTypeTwo = areaTypeTwo;
  }

  public int getAreaTypeThree() {
    return areaTypeThree;
  }

  public void setAreaTypeThree(int areaTypeThree) {
    this.areaTypeThree = areaTypeThree;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  protected Serializable pkVal() {
    return null;
  }

  public String getAreaOneName() {
    return areaOneName;
  }

  public void setAreaOneName(String areaOneName) {
    this.areaOneName = areaOneName;
  }

  public String getAreaTwoName() {
    return areaTwoName;
  }

  public void setAreaTwoName(String areaTwoName) {
    this.areaTwoName = areaTwoName;
  }

  public String getAreaThreeName() {
    return areaThreeName;
  }

  public void setAreaThreeName(String areaThreeName) {
    this.areaThreeName = areaThreeName;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }
}
