package com.abc.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月08 14:59
 */
public class PublicEchartsJson implements Serializable {

    // 总计
    private Integer totalSum;

    // 时间段
    private String dateStage;


    private List<String> dateString;

    private List<Integer> valueArr;

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public String getDateStage() {
        return dateStage;
    }

    public void setDateStage(String dateStage) {
        this.dateStage = dateStage;
    }

    public List<String> getDateString() {
        return dateString;
    }

    public void setDateString(List<String> dateString) {
        this.dateString = dateString;
    }

    public List<Integer> getValueArr() {
        return valueArr;
    }

    public void setValueArr(List<Integer> valueArr) {
        this.valueArr = valueArr;
    }
}
