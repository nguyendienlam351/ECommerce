package com.example.ecommerce.models;

public class AddressModel {
    String userAddress;
    Boolean isSelected;

    public AddressModel() {
    }

    public AddressModel(String userAddress, Boolean isSelected) {
        this.userAddress = userAddress;
        this.isSelected = isSelected;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
