package com.abc.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

@TableName("art_content")
public class ArtContent extends Model<ArtContent> {

    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private String title;

  @TableField("author_id")
  private int authorId;

    /**
     * 是否允许评论 1 允许， 2 不允许
     */
    @TableField("allow_comment")
  private int allowComment;

    @TableField("subject_id")
  private int subjectId;
    @TableField("body_id")
  private int bodyId;

    private String avatar;

    @TableField("comment_count")
  private int commentCount;

    @TableField("area_type_one")
  private int areaTypeOne;

    @TableField("area_type_two")
  private int areaTypeTwo;

    @TableField("area_type_three")
  private int areaTypeThree;

    @TableField("play_date")
  private Date playDate;

    @TableField("play_day")
  private int playDay;


  private double money;

  @TableField("play_type")
  private int playType;

  @TableField("view_count")
  private int viewCount;

  private int weight;
  private int status;

  @TableField("create_date")
  private Date createDate;

  @TableField("update_date")
  private Date updateDate;

  @TableField(exist = false)
  private String authorName;

  @TableField(exist = false)
  private String subjectName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(int allowComment) {
        this.allowComment = allowComment;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    public int getPlayDay() {
        return playDay;
    }

    public void setPlayDay(int playDay) {
        this.playDay = playDay;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
