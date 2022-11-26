package com.mop2022.team10;

import android.graphics.Bitmap;

import java.time.LocalDate;

public class food {
    private Bitmap imageResource;
    private String foodName;
    private int dueDate;
    private double count;
    private String unit;
    private LocalDate experationDate;
    public food(Bitmap id, String food, int due,double count,String unit,LocalDate experationDate){
        this.imageResource = id;
        this.foodName = food;
        this.dueDate = due;
        this.count = count;
        this.unit = unit;
        this.experationDate = experationDate;
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
    public String getUnit(){return unit;}
    public LocalDate getExperationDate(){return experationDate;}

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
    public void setUnit(String unit){this.unit = unit;}
    public void setExperationDate(LocalDate experationDate){this.experationDate = experationDate;}
}
