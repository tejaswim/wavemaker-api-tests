/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.project;

/**
 * Created by ankitk on 17/8/15.
 */
public class Plan {

    private String planCode;
    private String planName;
    private String description;
    private int planPeriod;
    private int unitAmount;
    private int setUpFee;
    private String trialPeriod;
    private int weight;
    private UnitType unitType;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(int planPeriod) {
        this.planPeriod = planPeriod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(int unitAmount) {
        this.unitAmount = unitAmount;
    }

    public int getSetUpFee() {
        return setUpFee;
    }

    public void setSetUpFee(int setUpFee) {
        this.setUpFee = setUpFee;
    }

    public String getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(String trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
}
