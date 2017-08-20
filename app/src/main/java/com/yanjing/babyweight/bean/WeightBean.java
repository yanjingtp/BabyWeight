package com.yanjing.babyweight.bean;

/**
 * Created by YanJing on 2017/08/20 0020.
 */

public class WeightBean {

    public String getBDP() {
        return BDP;
    }

    public void setBDP(String BDP) {
        this.BDP = BDP;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getFL() {
        return FL;
    }

    public void setFL(String FL) {
        this.FL = FL;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    private String week;    //周数
    private String BDP;     //双顶径
    private String AC;      //腹围
    private String FL;      //股骨长
}
