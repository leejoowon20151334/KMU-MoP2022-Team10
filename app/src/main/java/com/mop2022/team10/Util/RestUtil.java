package com.mop2022.team10.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    private final String deeplearningHost = "http://dev.pinkbean.kr:8004";
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

    public String getImageInfo(byte[] bytes){
        final String boundary = "*****";
        final String crlf = "\r\n";
        final String twoHyphens = "--";
        String response = "";
        try {
            URL url = new URL("http://dev.pinkbean.kr:8004/");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


            OutputStream httpConnOutputStream = httpConn.getOutputStream();
            DataOutputStream request = new DataOutputStream(httpConnOutputStream);



            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"Alice_Photo.jpeg\"" + crlf);
            request.writeBytes("Content-Type: image/jpeg" + crlf);
            request.writeBytes(crlf);
            request.write(bytes);

            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary +
                    twoHyphens + crlf);
            request.flush();
            request.close();

            InputStream responseStream = new BufferedInputStream(httpConn.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();

//            Log.d("response", stringBuilder.toString());
            response = stringBuilder.toString();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
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

    public JSONObject POST(String src, HashMap<String,String> param) {
        StringBuilder urlStr = new StringBuilder(host + src + "?");
        for(String k : param.keySet()){
            urlStr.append(k).append("=").append(param.get(k)).append("&");
        }
        String response = "";
        try {
//            URL url = new URL(host+src);urlStr
            URL url = new URL(urlStr.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");

            conn.setDoInput(true);
            conn.setDoOutput(true);

//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            //bw.write("testtest");
//            bw.write(img);
//            bw.flush();
//            bw.close();

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
