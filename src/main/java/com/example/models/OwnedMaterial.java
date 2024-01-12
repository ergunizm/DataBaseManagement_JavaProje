package com.example.models;

public class OwnedMaterial extends Model {
    private String userSsn;
    private String materialId;

    @Override
    public String getTableName() {
        return "owned_materials";
    }

    @Override
    public String toString() {
        return "\'" + userSsn + "\' , " + materialId;
    }

    // #region Getters and Setters --------------------------------------------
    public String getUserSsn() {
        return userSsn;
    }

    public void setUserSsn(String userSsn) {
        this.userSsn = userSsn;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
    // #endregion Getters and Setters -----------------------------------------
}
