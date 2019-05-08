package com.abc.vo;

import java.io.Serializable;

/**
 * @Author: liangxuanhao
 * @Description:
 * @Date: 2019年05月03 21:17
 */
public class PraiseAvarage implements Serializable {

    private float facilities;
    private float service;
    private float scenery;
    private float manage;
    private float passengerFlow;
    private float diet;
    private float transport;

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
}
