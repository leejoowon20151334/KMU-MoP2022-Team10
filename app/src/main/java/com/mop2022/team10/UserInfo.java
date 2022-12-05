package com.mop2022.team10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;

import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {

    private TextView test;
    private ImageView testImg;
    private LinearLayout Lay;
    private LinearLayout Lay1;
    private LinearLayout Lay2;
    String name;
    int userId;

    public void userInfoOnClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        switch(v.getId()) {
//            case R.id.user_info_ChangeName:
//
//                builder.setTitle("이름변경").setMessage("");
//                final EditText input = new EditText(this);
//                builder.setView(input);
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int id)
//                    {
//                        Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
////                        input.getText();
//                        Thread t = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                User user = new User();
//                                userId = user.getUserId(name);
////                                user.changeName(userId, String.valueOf(input.getText()));
//                            }
//                        });
//                        t.start();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int id)
//                    {
//                        Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                alertDialog = builder.create();
//                alertDialog.show();
//                break;
            case R.id.user_info_Fush:
                // 푸쉬 알림 관련 설정

                builder.setTitle("푸쉬 알림").setMessage("활성화 하시겠습니까?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {
                                            System.out.println( "Fetching FCM registration token failed");
                                            return;
                                        }

                                        // Get new FCM registration token
                                        String token = task.getResult();
                                        // Log and toast
                                        Log.d("Token", token);
                                        Toast.makeText(getApplicationContext(), "푸쉬알림을 활성화 했습니다", Toast.LENGTH_SHORT).show();
                                        Thread t = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                User user = new User();
                                                user.pushOn(userId, token);
                                            }
                                        });
                                        t.start();

                                    }
                                });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "푸쉬알림을 비활성화 했습니다.", Toast.LENGTH_SHORT).show();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User user = new User();
                                user.pushOff(userId);
                            }
                        });
                        t.start();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.user_info_Withdrawal:
                //회원탈퇴 버튼
                builder.setTitle("회원 탈퇴").setMessage("탈퇴시 앱이 종료됩니다. \n정말 탈퇴 하시겠습니까?.");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User user = new User();
                                user.deleteUser(userId);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                                        moveTaskToBack(true);
                                        finishAndRemoveTask();
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }});
                            }
                        });
                        t.start();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        TextView Title = findViewById(R.id.user_info_UserName);
        SharedPreferences pref = getSharedPreferences("userId", 0);
        name = pref.getString("userName", "");
//        Title.setText(name);
        Lay = findViewById(R.id.user_info_FirScrollView);
        Lay1 = findViewById(R.id.user_info_SecScrollView);
        Lay2 = findViewById(R.id.user_info_ThrScrollView);



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                userId = user.getUserId(name);
                Recipe recipe = new Recipe();
                ArrayList<RecipeModel> result = recipe.getFavorite(userId);
//                Bitmap img = recipe.getImg(result.img);userSearchLog
                Recipe recipe1 = new Recipe();
                ArrayList<RecipeModel> result1 = recipe.userSearchLog(userId);

                Recipe recipe2 = new Recipe();
                ArrayList<RecipeModel> result2 = recipe.getMyEvaluation(userId);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        LinearLayout la = new LinearLayout(getBaseContext());
//                        la.setLayoutParams(params);

                        for(int i = 0;i < result.size(); i++) {
                            LinearLayout la = new LinearLayout(getBaseContext());
                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layout_params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            layout_params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            la.setLayoutParams(layout_params);
                            la.setOrientation(LinearLayout.VERTICAL);
                            Lay.addView(la);

                            Bitmap img_ = recipe.getImg(result.get(i).img);
                            ImageView img = new ImageView(getBaseContext());
                            img.setImageBitmap(img_);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()),
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
                            la.addView(img);

                            TextView view = new TextView(getBaseContext());
                            view.setText(result.get(i).name);
                            view.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            view.setTextColor(Color.rgb(0,0,0));
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            la.addView(view);
                        }

                        for(int i = 0;i < result1.size(); i++) {
                            LinearLayout la = new LinearLayout(getBaseContext());
                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layout_params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            layout_params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            la.setLayoutParams(layout_params);
                            la.setOrientation(LinearLayout.VERTICAL);
                            Lay1.addView(la);

                            Bitmap img_ = recipe1.getImg(result1.get(i).img);
                            ImageView img = new ImageView(getBaseContext());
                            img.setImageBitmap(img_);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()),
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
                            la.addView(img);

                            TextView view = new TextView(getBaseContext());
                            view.setText(result1.get(i).name);
                            view.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            view.setTextColor(Color.rgb(0,0,0));
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            la.addView(view);
                        }

                        for(int i = 0;i < result2.size(); i++) {
                            LinearLayout la = new LinearLayout(getBaseContext());
                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layout_params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            layout_params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            la.setLayoutParams(layout_params);
                            la.setOrientation(LinearLayout.VERTICAL);
                            Lay2.addView(la);

                            Bitmap img_ = recipe1.getImg(result2.get(i).img);
                            ImageView img = new ImageView(getBaseContext());
                            img.setImageBitmap(img_);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()),
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
                            la.addView(img);

                            TextView view = new TextView(getBaseContext());
                            view.setText(result2.get(i).name);
                            view.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            view.setTextColor(Color.rgb(0,0,0));
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            la.addView(view);
                        }
                    }
                });
            }
        });
        t.start();
    }
}