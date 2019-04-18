package com.abc.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


@TableName("art_body")
public class ArtBody extends Model<ArtBody> {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // markdown内容
    @TableField("content")
    private String content;
    // 转换html格式
    @TableField("content_html")
    private String contentHtml;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
