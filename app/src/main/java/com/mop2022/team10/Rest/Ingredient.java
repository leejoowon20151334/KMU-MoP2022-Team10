package com.mop2022.team10.Rest;

import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ingredient {

    /*
    사용예시

    Ingredient ingredient = new Ingredient();
    ArrayList<IngredientModel> ingredientList = ingredient.searchIngredient("계란");
    for(int i=0;i<ingredientList.size();i++){
        IngredientModel data = ingredientList.get(i);
        Log.d("식자재_이름 : ",data.name);
    }

    // IngredientModel 내용물은 해당 파일의 최하단 IngredientModel class 참조

     */


    //식자재 검색
    public ArrayList<IngredientModel> searchIngredient(String name){
        //임시 고정값
        ArrayList<IngredientModel> list = new ArrayList<>();

        IngredientModel model1 = new IngredientModel();
        model1.id=1;
        model1.name="계란";
        model1.defaultExpiration = 27;
        model1.unit="개";
        model1.count=0;
        model1.expirationDate = null;

        IngredientModel model2 = new IngredientModel();
        model2.id=2;
        model2.name="우유";
        model2.defaultExpiration = 10;
        model2.unit="ml";
        model2.count=0;
        model2.expirationDate = null;

        list.add(model1);
        list.add(model2);

        return list;
    }

    public ArrayList<IngredientModel> userIngredient(int userId){
        //임시 고정값
        ArrayList<IngredientModel> list = new ArrayList<>();

        IngredientModel model1 = new IngredientModel();
        model1.id=1;
        model1.name="계란";
        model1.defaultExpiration = 27;
        model1.unit="개";
        model1.count=5;
        model1.expirationDate = LocalDate.parse("2022-11-15");

        IngredientModel model2 = new IngredientModel();
        model2.id=2;
        model2.name="우유";
        model2.defaultExpiration = 10;
        model2.unit="ml";
        model2.count=500;
        model2.expirationDate = LocalDate.parse("2022-11-15");

        IngredientModel model3 = new IngredientModel();
        model3.id=3;
        model3.name="땅콩";
        model3.defaultExpiration = 365;
        model3.unit="g";
        model3.count=100;
        model3.expirationDate = LocalDate.parse("2023-11-11");

        IngredientModel model4 = new IngredientModel();
        model4.id=4;
        model4.name="식용유";
        model4.defaultExpiration = 545;
        model4.unit="ml";
        model4.count=500;
        model4.expirationDate = LocalDate.parse("2024-11-11");

        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);

        return list;
    }

    public boolean addUserIngredient(int userId, int ingredientId,int count, LocalDate expire){
        return true;
    }

    public class IngredientModel {
        //기본정보
        public int id;
        public String name;
        public int defaultExpiration;
        public String unit;

        //사용자별 정보
        public int count;
        public LocalDate expirationDate;
    }
}
