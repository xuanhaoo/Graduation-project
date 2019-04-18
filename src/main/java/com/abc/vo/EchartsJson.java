package com.abc.vo;

import java.io.Serializable;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年03月07 23:09
 */
public class EchartsJson implements Serializable {

    private Integer value;

    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
