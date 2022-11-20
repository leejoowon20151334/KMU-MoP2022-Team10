package com.mop2022.team10;

public class food {
    private int imageResourceID;
    private String foodName;
    private String dueDate;
    public food(int id, String food, String due){
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
    public String getDueDate(){
        return dueDate;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
