package com.onisha.oldaid.reminder.model;

import io.realm.RealmObject;

/**
 * Created by onisha on 15-Aug-16.
 */
public class PrefsModelDB extends RealmObject {


    private Float height, weight;

    private String foodPreference, booksPreference, tvProgramPreference, newsPreference;



    public PrefsModelDB() {
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getBooksPreference() {
        return booksPreference;
    }

    public void setBooksPreference(String booksPreference) {
        this.booksPreference = booksPreference;
    }

    public String getTvProgramPreference() {
        return tvProgramPreference;
    }

    public void setTvProgramPreference(String tvProgramPreference) {
        this.tvProgramPreference = tvProgramPreference;
    }

    public String getNewsPreference() {
        return newsPreference;
    }

    public void setNewsPreference(String newsPreference) {
        this.newsPreference = newsPreference;
    }
}
