package com.mop2022.team10;

import android.graphics.Bitmap;

public class food {
    private Bitmap imageResource;
    private String foodName;
    private int dueDate;
    private double count;
    public food(Bitmap id, String food, int due,double count){
        this.imageResource = id;
        this.foodName = food;
        this.dueDate = due;
        this.count = count;
    }
    public Bitmap getImageResource(){
        return imageResource;
    }
    public String getFoodName(){
        return foodName;
    }
    public int getDueDate(){
        return dueDate;
    }
    public double getCount(){return count;}

    public void setImageResource(Bitmap imageResource) {
        this.imageResource = imageResource;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }
    public void setCount(int count){this.count = count;}
}
