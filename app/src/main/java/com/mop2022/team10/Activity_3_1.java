package com.mop2022.team10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Activity_3_1 extends AppCompatActivity {
    private ArrayList<food> dataList;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3_1);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton1);
//------------------유저 아이디 인텐트로 받아오기----------------------------------------

        if(getIntent().hasExtra("userId"))
            userId = (int) getIntent().getExtras().get("userId");
        else
            userId = 1;
//--------------------------------------------------------------
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3으로 이동
//                Intent intent = new Intent(getApplicationContext(), Activity_3.class);
//                startActivity(intent);
                finish();
            }
        });
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3으로 이동
                Intent intent = new Intent(getApplicationContext(), Activity_3_2.class);
                startActivity(intent);
                finish();
            }
        });

        Context context = this;
        dataList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        MyAdapter myAdapter = new MyAdapter(dataList);
        recyclerView.setAdapter(myAdapter);
        //---------------------camera------------------------------
        //기타 로직



        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //카메라 호출 버튼
        Button cameraBtn = (Button) findViewById(R.id.camera);

        //촬영한 사진 관련 permission check
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, 1);

        //카메라 호출 버튼 클릭시 작동
        cameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getGall();

                myAdapter.notifyDataSetChanged();
            }
        });
//-------------------------------카메라 끝-------------------------------------------------------
//        Context context = this;
//        dataList = new ArrayList<>();
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(manager);
//        MyAdapter myAdapter = new MyAdapter(dataList);
//        recyclerView.setAdapter(myAdapter);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //API호출
                Ingredient ingredient = new Ingredient();
                //---------------유저 호출
                ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(userId);
                //---------------------------
                for(int i=0;i<ingredientList.size();i++){
                    IngredientModel data = ingredientList.get(i);
                    Bitmap img2 = ingredient.getImg(ingredientList.get(i).img);
                    LocalDate currentdate = LocalDate.now();
                    LocalDate expirationdate = data.expirationDate;
                    Period period = Period.between(currentdate,expirationdate);
                    int duedateint = period.getDays();
                    dataList.add(new food(img2,data.name, duedateint,data.count,data.unit,expirationdate));
                }
                //해당 사용자의 식자재 목록 반환
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Comparator<food> noAsc = new Comparator<food>() {
                            @Override
                            public int compare(food food1, food food2) {
                                int ret;

                                if(food1.getDueDate()<food2.getDueDate()){
                                    ret = -1;
                                }
                                else if(food1.getDueDate()==food2.getDueDate()){
                                    ret = 0;
                                }
                                else{
                                    ret = 1;
                                }
                                return ret;
                            }
                        };
                        Collections.sort(dataList,noAsc);
                        myAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
        t.start();
    }
    //갤러리 실행
    private void getGall() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }
    //기본 카메라앱 실행
    private void sendTakePhotoIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, 101);
        }catch (Exception e){
            Log.d("imageRecognition",e.toString());
        }
    }

    //기본 카메라앱에서 촬영한 사진 받아온 후 동작
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101 && resultCode == RESULT_OK) {
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
//            Bundle extras = data.getExtras();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(data.getData());
            Bitmap imageBitmap = BitmapFactory.decodeStream(in);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , blob);
            byte[] bytes= blob.toByteArray();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    RestUtil util = new RestUtil();
                    String str = util.getImageInfo(bytes);
                    str = str.replaceAll("\\{", "");
                    str = str.replaceAll("\\}", "");
                    str = str.replaceAll(" ", "");
                    str = str.replaceAll("\n", "");
                    String[] strs = str.split(",");
                    Ingredient ingredient = new Ingredient();
                    LocalDate expire = LocalDate.now().with(temporal -> temporal.plus(15, ChronoUnit.DAYS));
                    if(str.length() > 0)
                    for(int i = 0; i < strs.length; i++) {
                        Log.d(expire.toString(), Integer.toString(userId) );
                        ingredient.addUserIngredient(userId,
                                Integer.parseInt(strs[i].split(":")[0]),
                                (double)Integer.parseInt(strs[i].split(":")[1]),
                                expire);
                    }
                    Log.d("onActivityResult", str);

                }
            });
            t.start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Activity_2.class);
        startActivity(intent);
        finish();
    }
}
