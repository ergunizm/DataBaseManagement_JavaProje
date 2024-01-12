package com.example.models;

public class MatNumberOfUser {
    private String ssn;
    private Integer numberOfMats;

    public MatNumberOfUser(String ssn, Integer numberOfMats) {
        this.ssn = ssn;
        this.numberOfMats = numberOfMats;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Integer getNumberOfMats() {
        return numberOfMats;
    }

    public void setNumberOfMats(Integer numberOfMats) {
        this.numberOfMats = numberOfMats;
    }
}
