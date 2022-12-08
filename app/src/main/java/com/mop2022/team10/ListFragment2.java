package com.mop2022.team10;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListFragment2 extends Fragment {
    private View view;
    private Button btn_frag1;
    int userId;

    ArrayList<Bitmap> img_list = new ArrayList<>();
    ArrayList<IngredientModel> ingredient_test = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment2, container, false);
        btn_frag1 = view.findViewById(R.id.btn_icon2);

        SharedPreferences pref = getActivity().getSharedPreferences("userId",0);
        userId = pref.getInt("userId", 1);

        if(getArguments() != null) {
            ingredient_test = (ArrayList<IngredientModel>) getArguments().getSerializable("ingredient");
        }
        LinearLayout LL = (LinearLayout) view.findViewById(R.id.list_ll);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Ingredient ingredient = new Ingredient();

                for (int i = 0; i < ingredient_test.size(); i++) {
                    Bitmap img = ingredient.getImg(ingredient_test.get(i).img);
                    TextView tv = new TextView(getActivity());
                    tv.setText(ingredient_test.get(i).name+"\n");
                    tv.setTextSize(15);
                    String str_name = ingredient_test.get(i).name;
                    img_list.add(img);

                    final int ind = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView testImg = new ImageView(getActivity());
                            testImg.setTag(R.id.name_string, str_name);
                            testImg.setTag(R.id.image_bitmap,img);
                            testImg.setTag(R.id.number, ind);
                            testImg.setOnClickListener(myListener);
                            testImg.setImageBitmap(img);
                            tv.setGravity(Gravity.CENTER);
                            LL.addView(testImg);
                            LL.addView(tv);
                        }
                    });
                }
            }
        });
        t.start();

        btn_frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ListFragment2.this).commit();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String projectId = v.getTag(R.id.name_string).toString();
            final int index = (int) v.getTag(R.id.number);

            Toast.makeText(getActivity().getApplicationContext(),projectId,Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = getActivity().getLayoutInflater();
            View layout_dlg = inflater1.inflate(R.layout.dialog,null);
            ImageView Img2 = (ImageView) layout_dlg.findViewById(R.id.imageView22);
            EditText et1 = (EditText) layout_dlg.findViewById(R.id.editTextDate);
            EditText et2 = (EditText) layout_dlg.findViewById(R.id.editTextNumberSigned);
            Img2.setLayoutParams(new LinearLayout.LayoutParams(400,400));
            Img2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Img2.setImageBitmap(img_list.get(index));
            dlg.setView(layout_dlg);
            dlg.setTitle(projectId + " 추가");

            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String a = et1.getText().toString();
                    String b = et2.getText().toString();

                    LocalDate aa = LocalDate.parse(a);
                    double bb = Double.parseDouble(b);

                    // 추가버튼 눌렀을 때, 지정한 유통기한과 수량정보 전달
                    if (a.length() > 0 && b.length() > 0) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Activity_3_1.class);

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Ingredient ingredient = new Ingredient();
                                ingredient.addUserIngredient(userId, ingredient_test.get(index).id, bb, aa);

                            }
                        });
                        t.start();

                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),"유통기한 정보와 수량정보를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dlg.setNegativeButton("취소",null);

            dlg.show();

        }
    };

}