package com.mop2022.team10.Rest.Model;

import java.time.LocalDate;

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
