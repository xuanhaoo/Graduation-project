package com.abc.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("public_praise_view")
public class PublicPraiseView extends Model<PublicPraiseView> {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private int type;

  @TableField("spot_id")
  private int spotId;
  private float facilities;
  private float service;
  private float scenery;
  private float manage;
  private float passengerFlow;
  private float diet;
  private float transport;
  private String comment;

  @TableField("create_by")
  private int createBy;

  @TableField("create_date")
  private Date createDate;

  @TableField(exist = false)
  private String spotName;


  @TableField(exist = false)
  private String authorName;

  @TableField(exist = false)
  private String authorAvatar;


  @TableField(exist = false)
  private float avarageRate;

  @TableField(exist = false)
  private int addressId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getSpotId() {
    return spotId;
  }

  public void setSpotId(int spotId) {
    this.spotId = spotId;
  }

  public float getFacilities() {
    return facilities;
  }

  public void setFacilities(float facilities) {
    this.facilities = facilities;
  }

  public float getService() {
    return service;
  }

  public void setService(float service) {
    this.service = service;
  }

  public float getScenery() {
    return scenery;
  }

  public void setScenery(float scenery) {
    this.scenery = scenery;
  }

  public float getManage() {
    return manage;
  }

  public void setManage(float manage) {
    this.manage = manage;
  }

  public float getPassengerFlow() {
    return passengerFlow;
  }

  public void setPassengerFlow(float passengerFlow) {
    this.passengerFlow = passengerFlow;
  }

  public float getDiet() {
    return diet;
  }

  public void setDiet(float diet) {
    this.diet = diet;
  }

  public float getTransport() {
    return transport;
  }

  public void setTransport(float transport) {
    this.transport = transport;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getCreateBy() {
    return createBy;
  }

  public void setCreateBy(int createBy) {
    this.createBy = createBy;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getSpotName() {
    return spotName;
  }

  public void setSpotName(String spotName) {
    this.spotName = spotName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public float getAvarageRate() {
    return avarageRate;
  }

  public void setAvarageRate(float avarageRate) {
    this.avarageRate = avarageRate;
  }

  public String getAuthorAvatar() {
    return authorAvatar;
  }

  public void setAuthorAvatar(String authorAvatar) {
    this.authorAvatar = authorAvatar;
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  @Override
  protected Serializable pkVal() {
    return null;
  }
}
