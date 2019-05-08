package com.abc.vo;

import java.io.Serializable;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年04月30 16:37
 */
public class ChineseMapVo implements Serializable {

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
