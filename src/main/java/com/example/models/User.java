package com.example.models;

public class User extends Model {
    private String ssn;
    private String firstName;
    private String lastName;
    private String orgId;
    private String password;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ===============================================================================
    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public String toString() {
        return "\'" + ssn + "\', \'" + firstName + "\', \'" + lastName + "\', \'" + orgId + "\', \'" + password + "\'";
    }
}