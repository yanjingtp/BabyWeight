package com.yanjing.babyweight.bean;

import java.util.List;

/**
 * Created by YanJing on 2017/08/20 0020.
 */

public class WeightBean {

    private String week_num;    //周数
    private String BDP;     //双顶径
    private String AC;      //腹围
    private String FL;      //股骨长
    private List<WeightBean> weight;

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getAC() {
        return this.AC;
    }

    public void setBDP(String BDP) {
        this.BDP = BDP;
    }

    public String getBDP() {
        return this.BDP;
    }

    public void setFL(String FL) {
        this.FL = FL;
    }

    public String getFL() {
        return this.FL;
    }

    public void setWeek_num(String week_num) {
        this.week_num = week_num;
    }

    public String getWeek_num() {
        return this.week_num;
    }


    public void setWeight(List<WeightBean> weight) {
        this.weight = weight;
    }

    public List<WeightBean> getWeight() {
        return this.weight;
    }


}
