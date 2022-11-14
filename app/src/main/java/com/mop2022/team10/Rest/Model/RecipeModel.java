package com.mop2022.team10.Rest.Model;

import java.util.ArrayList;

public class RecipeModel {
    public int id;
    public String name;
    public int time;
    public int difficulty;
    public String description;
    public ArrayList<RecipeTypeModel> type = new ArrayList<>();
    public ArrayList<IngredientModel> ingredients = new ArrayList<>();
    public ArrayList<String> procedure = new ArrayList<>();
}
