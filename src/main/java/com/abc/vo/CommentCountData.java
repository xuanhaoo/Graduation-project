package com.abc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 20:23
 */

public class CommentCountData implements Serializable {


    // 所有的评论数
    private int countNum;

    private List<EchartsJson> echartsJsonList;

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public List<EchartsJson> getEchartsJsonList() {
        return echartsJsonList;
    }

    public void setEchartsJsonList(List<EchartsJson> echartsJsonList) {
        this.echartsJsonList = echartsJsonList;
    }
}
