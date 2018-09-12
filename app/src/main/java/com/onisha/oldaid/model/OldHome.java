package com.onisha.oldaid.model;

public class OldHome {

    private String name;
    private String location;
    private Integer expense;

    public OldHome(String name, String location, Integer expense) {
        this.name = name;
        this.location = location;
        this.expense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }
}
