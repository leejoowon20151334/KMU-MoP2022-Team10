package com.mop2022.team10.Rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Model.RecipeTypeModel;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Recipe/searchRecipe",e.toString());
        }

        return list;
    }

    //레시피 검색
    public RecipeModel getRecipeDetail(int recipeId){

        ArrayList<RecipeModel> list = new ArrayList<>();
        RecipeModel recipe = new RecipeModel();

        HashMap<String,String> val = new HashMap<>();
        val.put("recipeId",Integer.toString(recipeId));
        JSONObject result = rest.GET("/getRecipeDetail",val);
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
            if(list.size()>0)
                recipe = list.get(0);
        }catch (Exception e){
            Log.d("Rest/Recipe/getRecipeDetail",e.toString());
        }

        return recipe;
    }

    public ArrayList<RecipeModel> getFavorite(int userId) {
        ArrayList<RecipeModel> list = new ArrayList<>();
        RecipeModel recipe = new RecipeModel();

        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        JSONObject result = rest.GET("/getFavorite",val);
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Recipe/getFavorite",e.toString());
        }

        return list;
    }

    //사용자 레시피 검색내역
    public ArrayList<RecipeModel> userSearchLog(int userId){

        ArrayList<RecipeModel> list = new ArrayList<>();
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        JSONObject result = rest.GET("/userSearchLog",val);
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Recipe/userSearchLog",e.toString());
        }

        return list;
    }

    public Bitmap getImg(String img){
        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private ArrayList<RecipeModel> jsonToList(JSONArray data){
        ArrayList<RecipeModel> list = new ArrayList<>();
        try {
            for(int i=0;i<data.length();i++){
                JSONObject recipe = data.getJSONObject(i);
                RecipeModel model = new RecipeModel();
                if(recipe.has("id"))
                    model.id=recipe.getInt("id");
                if(recipe.has("name"))
                    model.name=recipe.getString("name");
                if(recipe.has("time"))
                    model.time = recipe.getInt("time");
                if(recipe.has("difficulty"))
                    model.difficulty=recipe.getInt("difficulty");
                if(recipe.has("description"))
                    model.description=recipe.getString("description");
                if(recipe.has("img"))
                    model.img=recipe.getString("img");

                if(recipe.has("type")) {
                    JSONArray typeData = recipe.getJSONArray("type");
                    for (int j = 0; j < typeData.length(); j++) {
                        JSONObject type = typeData.getJSONObject(j);
                        RecipeTypeModel typeModel = new RecipeTypeModel();
                        typeModel.typeId = type.getInt("typeId");
                        typeModel.typeName = type.getString("typeName");
                        model.type.add(typeModel);
                    }
                }

                if(recipe.has("ingredients")) {
                    JSONArray ingredientData = recipe.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientData.length(); j++) {
                        JSONObject ingredient = ingredientData.getJSONObject(j);
                        IngredientModel ingredientModel = new IngredientModel();
                        ingredientModel.id = ingredient.getInt("id");
                        ingredientModel.name = ingredient.getString("name");
                        ingredientModel.defaultExpiration = ingredient.getInt("defaultExpiration");
                        ingredientModel.unit = ingredient.getString("unit");
                        ingredientModel.img = ingredient.getString("img");
                        ingredientModel.count = ingredient.getDouble("count");
                        model.ingredients.add(ingredientModel);
                    }
                }

                if(recipe.has("procedure")) {
                    JSONArray procedure = recipe.getJSONArray("procedure");
                    for (int j = 0; j < procedure.length(); j++)
                        model.procedure.add(procedure.getString(j));
                }

                list.add(model);
            }
        }catch (JSONException e){
            Log.d("Rest/Ingredient/jsonToList",e.toString());
        }

        return list;
    }
}
