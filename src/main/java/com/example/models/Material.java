package com.example.models;

public class Material extends Model {
    private String materialId;
    private String companyId;
    private String materialType;
    private String barcode;
    private float price;

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String getTableName() {
        return "materials";
    }

    @Override
    public String toString() {
        return "\'" + barcode + "," + companyId + "," + materialId + "," + materialType + "," + price + "\'";
    }
}