package com.mop2022.team10;

public class food {
    private int imageResourceID;
    private String foodName;
    private int dueDate;
    public food(int id, String food, int due){
        this.imageResourceID = id;
        this.foodName = food;
        this.dueDate = due;
    }
    public int getImageResourceID(){
        return imageResourceID;
    }
    public String getFoodName(){
        return foodName;
    }
    public int getDueDate(){
        return dueDate;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }
}
