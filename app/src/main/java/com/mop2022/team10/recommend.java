package com.mop2022.team10;

import android.graphics.Bitmap;

public class recommend {
    private Bitmap imageResource;
    private String foodName;
    private float rate;
    private int time;
    private float evaluate;
    public recommend(Bitmap id, String food,float rate,int time,float evaluate){
        this.imageResource = id;
        this.foodName = food;
        this.rate = rate;
        this.time = time;
        this.evaluate = evaluate;
    }
    public Bitmap getImageResource(){
        return imageResource;
    }
    public String getFoodName(){
        return foodName;
    }
    public Float getRate(){return rate;}
    public int getTime(){return time;}
    public Float getEvaluate(){return evaluate;}
    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }
    public void setRate(float rate){this.rate = rate;}
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public void setTime(){this.time = time;}
    public void setEvaluate(float evaluate) {this.evaluate = evaluate;}
}
