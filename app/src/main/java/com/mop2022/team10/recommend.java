package com.mop2022.team10;

public class recommend {
    private int imageResourceID;
    private String foodName;
    private float rate;
    public recommend(int id, String food,float rate){
        this.imageResourceID = id;
        this.foodName = food;
        this.rate = rate;
    }
    public int getImageResourceID(){
        return imageResourceID;
    }
    public String getFoodName(){
        return foodName;
    }
    public Float getRate(){return rate;}
    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }
    public void setRate(float rate){this.rate = rate;}
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
