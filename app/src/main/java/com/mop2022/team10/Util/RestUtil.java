package com.mop2022.team10.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class RestUtil {
    private final String host = "http://dev.pinkbean.kr:8000";
    //private final String host = "http://10.0.2.2:8081";
    private final String imgHost = "https://drive.google.com/uc?export=view&id=";

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
            //conn.setConnectTimeout(5000);
            //conn.setReadTimeout(5000);
            conn.setDoInput(true);
            Charset charset = StandardCharsets.UTF_8;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            response = sb.toString();
            conn.disconnect();
            return parse(response);
        }catch (Exception e){
            Log.d("Rest error : ",e.toString());
        }
        return null;
    }

    public Bitmap getImg(String imgId){
        try {
            URL url = new URL(imgHost + imgId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setConnectTimeout(5000);
            //conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream(); //inputStream 값 가져오기
            Bitmap img = BitmapFactory.decodeStream(is);
            conn.disconnect();
            return img;
        }catch (Exception e){
            Log.d("Rest error : ",e.toString());
        }
        return null;
    }

    public JSONObject PostImg(String src, String img){
        String response = "";
        try {
            URL url = new URL(host+src);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            //bw.write("testtest");
            bw.write(img);
            bw.flush();
            bw.close();

            Charset charset = StandardCharsets.UTF_8;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            response = sb.toString();
            conn.disconnect();
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
