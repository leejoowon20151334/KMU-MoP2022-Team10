package com.mop2022.team10.Rest;

import android.graphics.Bitmap;
import android.util.Log;

import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Ingredient {

    private RestUtil rest;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Ingredient(){
        rest = new RestUtil();
    }

    /*
    사용예시

    Ingredient ingredient = new Ingredient();
    ArrayList<IngredientModel> ingredientList = ingredient.searchIngredient("계란");
    for(int i=0;i<ingredientList.size();i++){
        IngredientModel data = ingredientList.get(i);
        Log.d("식자재_이름 : ",data.name);
    }

    // IngredientModel 내용물은 /Rest/Model/IngredientModel 참조

     */


    //식자재 검색
    public ArrayList<IngredientModel> searchIngredient(String name){

        ArrayList<IngredientModel> list = new ArrayList<>();

        HashMap<String,String> val = new HashMap<>();
        val.put("name",name);
        JSONObject result = rest.GET("/getIngredient",val);
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Ingredient/searchIngredient",e.toString());
        }

        return list;
    }

    //내 식재료 목록(전체)
    public ArrayList<IngredientModel> userIngredient(int userId){
        ArrayList<IngredientModel> list = new ArrayList<>();

        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        JSONObject result = rest.GET("/userIngredient",val);
        try {
            Log.d("APItest",result.toString());
            JSONArray data = result.getJSONArray("data");
            list = jsonToList(data);
        }catch (Exception e){
            Log.d("Rest/Ingredient/userIngredient",e.toString());
        }

        return list;
    }

    //내 식재료 추가
    public boolean addUserIngredient(int userId, int ingredientId,int count, LocalDate expire){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("ingredientId",Integer.toString(ingredientId));
        val.put("count",Integer.toString(count));
        val.put("expire",expire.toString());
        JSONObject result = rest.GET("/addFavorite",val);
        try {
            Log.d("APItest",result.toString());
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/addFavorite",e.toString());
        }
        return false;
    }

    public Bitmap getImg(String imgId){
        return rest.getImg(imgId);
    }

    public ArrayList<IngredientModel> jsonToList(JSONArray data){
        ArrayList<IngredientModel> list = new ArrayList<>();
        try {
            for(int i=0;i<data.length();i++){
                JSONObject ingredient = data.getJSONObject(i);
                IngredientModel model = new IngredientModel();
                if(ingredient.has("id"))
                    model.id=ingredient.getInt("id");
                if(ingredient.has("name"))
                    model.name=ingredient.getString("name");
                if(ingredient.has("defaultExpiration") && !ingredient.getString("defaultExpiration").equals("null"))
                    model.defaultExpiration = ingredient.getInt("defaultExpiration");
                if(ingredient.has("unit"))
                    model.unit=ingredient.getString("unit");
                if(ingredient.has("count"))
                    model.count=ingredient.getInt("count");
                if(ingredient.has("img"))
                    model.img=ingredient.getString("img");
                if(ingredient.has("expirationDate") && ingredient.getString("expirationDate").length()>0 && !ingredient.getString("expirationDate").equals("null"))
                    model.expirationDate = LocalDate.parse(ingredient.getString("expirationDate"),dateTimeFormatter);
                list.add(model);
            }
        }catch (JSONException e){
            Log.d("Rest/Ingredient/jsonToIngredientList",e.toString());
        }

        return list;
    }
}
