package com.mop2022.team10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Activity_3_1 extends AppCompatActivity {
    private ArrayList<food> dataList;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3_1);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton1);
//------------------유저 아이디 받아오기----------------------------------------
        SharedPreferences pref = getSharedPreferences("userId",0);
        userId = pref.getInt("userId",1);
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
                sendTakePhotoIntent();

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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    RestUtil rs = new RestUtil();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                    byte[] bytes = baos.toByteArray();
                    String imgStr = Base64.encodeToString(bytes, Base64.DEFAULT);
                    JSONObject result = rs.PostImg("/imageRecognition",imgStr);
                    if(result != null) {
                        try {
                            String data = result.getString("data");
                            if(data.equals("success"))
                                Log.d("APItest", "success");
                        }catch (Exception e){
                            Log.d("APItest","fail");
                        }
                    }else
                        Log.d("APItest","fail");
                }
            });
            t.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Activity_2.class);
        startActivity(intent);
        finish();
    }
}
