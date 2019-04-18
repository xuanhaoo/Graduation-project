package com.abc.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("follow_relation")
public class FollowRelation extends Model<FollowRelation> {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @TableField("by_follow")
  private int byFollow;

  private int followers;

  @TableField("create_date")
  private Date createDate;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getByFollow() {
    return byFollow;
  }

  public void setByFollow(int byFollow) {
    this.byFollow = byFollow;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  protected Serializable pkVal() {
    return null;
  }
}
