package com.example.models;

public class Company extends Model {
    private String companyId;
    private String companyName;

    @Override
    public String getTableName() {
        return "companies";
    }

    @Override
    public String toString() {
        return "\'" + companyId + "," + companyName + "\'";
    }

    // #region Getters and Setters --------------------------------------------
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    // #endregion Getters and Setters -----------------------------------------
}