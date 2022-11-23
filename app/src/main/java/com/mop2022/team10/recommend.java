package com.mop2022.team10;

public class recommend {
    private int imageResourceID;
    private String foodName;
    public recommend(int id, String food){
        this.imageResourceID = id;
        this.foodName = food;
    }
    public int getImageResourceID(){
        return imageResourceID;
    }
    public String getFoodName(){
        return foodName;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
