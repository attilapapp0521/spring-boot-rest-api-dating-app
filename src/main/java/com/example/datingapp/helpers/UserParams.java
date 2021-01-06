package com.example.datingapp.helpers;


public class UserParams {
    private String currentUserName;
    private String gender;
    private int minAge = 18;
    private int maxAga = 99;
    private String orderBy;



    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAga() {
        return maxAga;
    }

    public void setMaxAga(int maxAga) {
        this.maxAga = maxAga;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}