package com.mop2022.team10.Rest;

import android.util.Log;

import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Model.RecipeTypeModel;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private RestUtil rest;

    public Recipe(){
        rest = new RestUtil();
    }

    //레시피 검색
    public ArrayList<RecipeModel> searchRecipe(String name){

        ArrayList<RecipeModel> list = new ArrayList<>();

        HashMap<String,String> val = new HashMap<>();
        val.put("name",name);
        JSONObject result = rest.GET("/getRecipe",val);
        Log.d("APItest",result.toString());
        try {
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Recipe/searchRecipe",e.toString());
        }

        return list;
    }







    private ArrayList<RecipeModel> jsonToList(JSONArray data){
        ArrayList<RecipeModel> list = new ArrayList<>();
        try {
            for(int i=0;i<data.length();i++){
                JSONObject recipe = data.getJSONObject(i);
                RecipeModel model = new RecipeModel();
                model.id=recipe.getInt("id");
                model.name=recipe.getString("name");
                model.time = recipe.getInt("time");
                model.difficulty=recipe.getInt("difficulty");
                model.description=recipe.getString("description");

                JSONArray typeData = recipe.getJSONArray("type");
                for(int j=0;j<typeData.length();j++){
                    JSONObject type = typeData.getJSONObject(j);
                    RecipeTypeModel typeModel = new RecipeTypeModel();
                    typeModel.typeId = type.getInt("typeId");
                    typeModel.typeName = type.getString("typeName");
                    model.type.add(typeModel);
                }

                JSONArray ingredientData = recipe.getJSONArray("ingredients");
                for(int j=0;j<ingredientData.length();j++){
                    JSONObject ingredient = ingredientData.getJSONObject(j);
                    IngredientModel ingredientModel = new IngredientModel();
                    ingredientModel.id = ingredient.getInt("id");
                    ingredientModel.name = ingredient.getString("name");
                    ingredientModel.defaultExpiration = ingredient.getInt("defaultExpiration");
                    ingredientModel.unit = ingredient.getString("unit");
                    model.ingredients.add(ingredientModel);
                }

                JSONArray procedure = recipe.getJSONArray("procedure");
                for(int j=0;j<procedure.length();j++)
                    model.procedure.add(procedure.getString(j));

                list.add(model);
            }
        }catch (JSONException e){
            Log.d("Rest/Ingredient/jsonToList",e.toString());
        }

        return list;
    }
}
