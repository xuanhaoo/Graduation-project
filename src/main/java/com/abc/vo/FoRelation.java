package com.abc.vo;

import java.io.Serializable;

/**
 * @Author: liangxuanhao
 * @Description: 关注数量与粉丝数量
 * @Date: 2019年04月24 14:09
 */
public class FoRelation implements Serializable {

    int followNumber;

    int guanzhuNumber;

    public int getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    public int getGuanzhuNumber() {
        return guanzhuNumber;
    }

    public void setGuanzhuNumber(int guanzhuNumber) {
        this.guanzhuNumber = guanzhuNumber;
    }
}
