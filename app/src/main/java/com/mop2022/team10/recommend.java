package com.mop2022.team10;

import android.graphics.Bitmap;

public class recommend {
    private Bitmap imageResource;
    private String foodName;
    private float rate;
    private int time;
    private double evaluate;
    private int recipeId;
    public recommend(Bitmap id, String food,float rate,int time,double evaluate,int recipeId){
        this.imageResource = id;
        this.foodName = food;
        this.rate = rate;
        this.time = time;
        this.evaluate = evaluate;
        this.recipeId = recipeId;
    }
    public Bitmap getImageResource(){
        return imageResource;
    }
    public String getFoodName(){
        return foodName;
    }
    public Float getRate(){return rate;}
    public int getTime(){return time;}
    public Double getEvaluate(){return evaluate;}
    public int getRecipeId(){return recipeId;}
    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }
    public void setRate(float rate){this.rate = rate;}
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public void setTime(){this.time = time;}
    public void setEvaluate(float evaluate) {this.evaluate = evaluate;}
    public void setRecipeId(int recipeId){this.recipeId = recipeId;}
}
