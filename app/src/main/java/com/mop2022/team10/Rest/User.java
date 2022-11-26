package com.mop2022.team10.Rest;


import android.util.Log;
import com.mop2022.team10.Util.RestUtil;
import org.json.JSONObject;
import java.util.HashMap;

public class User {

    private RestUtil rest;

    public User(){
        rest = new RestUtil();
    }

    /*
    사용예시

    User user = new User();
    int userId = user.getUserId(ssaid);


    SSAID : Settings.Secure.getString(getApplicationContext().getContentResolver(),
    							Settings.Secure.ANDROID_ID);
    		https://helloit.tistory.com/293 참조

     */


    //회원가입 : 0일경우 실패, 그외일 경우 userid
    public int signUp(String ssaid){
        HashMap<String,String> val = new HashMap<>();
        val.put("name",ssaid);
        JSONObject result = rest.GET("/signUp",val);
        Log.d("APItest",result.toString());
        try {
            return result.getInt("data");
        }catch (Exception e){
            Log.d("Rest/User/signUp",e.toString());
        }
        return 0;
    }

    //회원번호 : 0일경우 실패(없는 회원), 그외일 경우 userid(int)
    public int getUserId(String ssaid) {
        HashMap<String,String> val = new HashMap<>();
        val.put("name",ssaid);
        JSONObject result = rest.GET("/getUserId",val);
        Log.d("APItest",result.toString());
        try {
            return result.getInt("data");
        }catch (Exception e){
            Log.d("Rest/User/getUserId",e.toString());
        }
        return 0;
    }

    //즐겨찾기 등록 : 성공시 true
    public boolean addFavorite(int userId,int recipeId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("recipeId",Integer.toString(recipeId));
        JSONObject result = rest.GET("/addFavorite",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/addFavorite",e.toString());
        }
        return false;
    }

    //즐겨찾기 삭제 : 성공시 true
    public boolean deleteFavorite(int userId,int recipeId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("recipeId",Integer.toString(recipeId));
        JSONObject result = rest.GET("/deleteFavorite",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/deleteFavorite",e.toString());
        }
        return false;
    }

    //즐겨찾기 확인 : 있으면 true
    public boolean isFavorite(int userId,int recipeId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("recipeId",Integer.toString(recipeId));
        JSONObject result = rest.GET("/isFavorite",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/deleteFavorite",e.toString());
        }
        return false;
    }

    public boolean changeName(int userId, String name){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("name",name);
        JSONObject result = rest.POST("/setUsername",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/setUsername",e.toString());
        }
        return false;
    }

    //사용자 검색 기록 등록 : 성공시 true
    public boolean addUserSearchLog(int userId,int recipeId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("recipeId",Integer.toString(recipeId));
        JSONObject result = rest.GET("/addUserSearchLog",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/addFavorite",e.toString());
        }
        return false;
    }

    //push 등록 및 사용 : 성공시 true
    public boolean pushOn(int userId, String pushId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        val.put("pushId",pushId);
        JSONObject result = rest.GET("/startPush",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/pushOn",e.toString());
        }
        return false;
    }

    //push 중지 : 성공시 true
    public boolean pushOff(int userId){
        HashMap<String,String> val = new HashMap<>();
        val.put("userId",Integer.toString(userId));
        JSONObject result = rest.GET("/stopPush",val);
        Log.d("APItest",result.toString());
        try {
            String data = result.getString("data");
            if(data.equals("success"))
                return true;
        }catch (Exception e){
            Log.d("Rest/User/pushOff",e.toString());
        }
        return false;
    }
}
