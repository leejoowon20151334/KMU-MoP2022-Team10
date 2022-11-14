package com.mop2022.team10.Util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RestUtil {
    private final String host = "http://dev.pinkbean.kr:8000";
    //private final String host = "http://10.0.2.2:8081";

    public JSONObject GET(String src, HashMap<String,String> param){
        StringBuilder urlStr = new StringBuilder(host + src + "?");
        for(String k : param.keySet()){
            urlStr.append(k).append("=").append(param.get(k)).append("&");
        }
        String response = "";
        try {
            URL url = new URL(urlStr.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            System.out.println("getContentType():" + conn.getContentType()); // 응답 콘텐츠 유형 구하기
            System.out.println("getResponseCode():"    + conn.getResponseCode()); // 응답 코드 구하기
            System.out.println("getResponseMessage():" + conn.getResponseMessage()); // 응답 메시지 구하기
            Charset charset = StandardCharsets.UTF_8;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            response = sb.toString();
            return parse(response);
        }catch (Exception e){
            Log.d("Rest error : ",e.toString());
        }
        return null;
    }

    private JSONObject parse(String apiResult) throws JSONException {
        return new JSONObject(apiResult);
    }
}
