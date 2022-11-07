package com.mop2022.team10.Rest;

public class User {

    //회원가입 : 0일경우 실패, 그외일 경우 userid
    public int signUp(String uuid){
        return 1;
    }

    //회원번호 : 0일경우 실패, 그외일 경우 userid
    public int getUserId(String uuid) {
        return 1;
    }

    //즐겨찾기 등록 : 성공시 true
    public boolean addFavorite(int userId,int recipeId){
        return true;
    }

    //즐겨찾기 삭제 : 성공시 true
    public boolean deleteFavorite(int userId,int recipeId){
        return true;
    }

    //push 등록 및 사용 : 성공시 true
    public boolean pushOn(int userId, String pushId){
        return true;
    }

    //push 중지 : 성공시 true
    public boolean pushOff(int userId){
        return true;
    }
}
