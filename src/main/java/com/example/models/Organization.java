package com.example.models;

import java.util.Date;

public class Organization extends Model {
    private int orgId;
    private String companyId;
    private String orgType;
    private int guestLimit;
    private String season;
    private boolean availability;
    private int orgDate;
    private float price;

    @Override
    public String getTableName() {
        return "organizations";
    }

    @Override
    public String toString() {
        return "\'" + companyId + "\' , \'" + orgType + "\' ," + guestLimit + " , \'" + season + "\'," + price
                + "," + orgDate + "," + availability;
    }

    // #region Getters and Setters --------------------------------------------
    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public int getGuestLimit() {
        return guestLimit;
    }

    public void setGuestLimit(int guestLimit) {
        this.guestLimit = guestLimit;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getOrgDate() {
        return orgDate;
    }

    public void setOrgDate(int orgDate) {
        this.orgDate = orgDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    // #endregion -------------------------------------------------------------
}