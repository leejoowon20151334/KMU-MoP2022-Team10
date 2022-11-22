package com.mop2022.team10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView test;
    private Button testBtn,testCamera;
    private ImageView testImg;
    private String imageFilePath;
    private Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        test = (TextView) findViewById(R.id.recipeDetail_test);
        testBtn = (Button) findViewById(R.id.recipeDetail_testBtn);
        testImg = (ImageView) findViewById(R.id.recipeDetail_testImg);
        testCamera = (Button) findViewById(R.id.recipeDetail_testCamera);

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, 1);

        testCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendTakePhotoIntent();
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recipe recipe = new Recipe();
                        RecipeModel result = recipe.getRecipeDetail(1);
                        Bitmap img = recipe.getImg(result.img);

                        Ingredient ingredient = new Ingredient();
                        ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(1);
                        Bitmap img2 = ingredient.getImg(ingredientList.get(0).img);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder txt = new StringBuilder(result.description);
                                txt.append("\n");
                                for(int i=0;i<result.procedure.size();i++)
                                    txt.append(Integer.toString(i)).append(". ").append("\n").append(result.procedure.get(i));
                                test.setText(txt.toString());
                                testImg.setImageBitmap(img);

                                String txt2 = "";
                                TextView text2 = (TextView) findViewById(R.id.recipeDetail_test2);
                                ImageView testImg2 = (ImageView) findViewById(R.id.recipeDetail_testImg2);
                                for(int i=0;i<ingredientList.size();i++)
                                    txt2 += ingredientList.get(i).name + ", ";
                                text2.setText(txt2);
                                testImg2.setImageBitmap(img2);

                            }
                        });
                    }
                });
                t.start();
            }
        });
    }
    private void sendTakePhotoIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, 101);
        }catch (Exception e){
            Log.d("imageRecognition",e.toString());
        }
    }
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
                    if(result != null)
                        Log.d("APItest",result.toString());
                    else
                        Log.d("APItest","fail");
                }
            });
            t.start();
            testImg.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}