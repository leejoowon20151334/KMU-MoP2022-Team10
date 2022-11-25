package com.mop2022.team10;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;

import java.util.ArrayList;

public class TableFragment1 extends Fragment {
    private View view;
    private Button btn_frag2;

    ArrayList<IngredientModel> ingredient_test = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.table_fragment1, container, false);

        if(getArguments() != null) {
            ingredient_test = (ArrayList<IngredientModel>) getArguments().getSerializable("ingredient");
        }

        TableLayout TL = (TableLayout) view.findViewById(R.id.tl);


        Ingredient ingredient = new Ingredient();

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectId = v.getTag().toString();
                ImageView b = (ImageView) v;

                Toast.makeText(getActivity().getApplicationContext(),projectId,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                dlg.setView(inflater1.inflate(R.layout.dialog,null));
                dlg.setTitle(projectId + " 추가");

                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 추가버튼 눌렀을 때, 지정한 유통기한과 수량정보 전달
                    }
                });
                dlg.setNegativeButton("취소",null);

                dlg.show();
            }
        };

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ingredient_test.size(); i += 3) {

                    TableRow tableRow = new TableRow(getActivity());
                    tableRow.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    tableRow.setGravity(Gravity.CENTER);

                    for (int j = i; j < i + 3; j++) {
                        if (j == ingredient_test.size())
                            break;

                        String str_name = ingredient_test.get(j).name;
                        Bitmap img = ingredient.getImg(ingredient_test.get(j).img);

                        final int ind = j;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 이미지뷰(사진)과 텍스트(이름)을 묶기 위해 레이아웃을 적용하면 화면에 나타나지 않음
//                                LinearLayout ll = new LinearLayout(getActivity());
//                                ll.setLayoutParams(new ViewGroup.LayoutParams(
//                                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                                        ViewGroup.LayoutParams.WRAP_CONTENT));
//                                ll.setOrientation(LinearLayout.VERTICAL);

                                ImageView testImg = new ImageView(getActivity());
                                // 이미지뷰에 사진 크기 조절을 위한 작업을 적용하면 사진이 화면에 나타나지 않음
//                                testImg.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
//                                testImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                testImg.setTag(str_name);
                                testImg.setOnClickListener(myListener);
                                testImg.setImageBitmap(img);
                                TextView name = new TextView(getActivity());
                                name.setText(str_name);
                                name.setTextSize(10);
                                name.setGravity(Gravity.CENTER);

//                                ll.addView(testImg);
//                                ll.addView(name);


                                tableRow.addView(testImg);
                                tableRow.addView(name);
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TL.addView(tableRow);
                        }
                    });
                }
            }
        });
        t.start();

        btn_frag2 = view.findViewById(R.id.btn_icon1);
        btn_frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("ingredient", ingredient_test);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ListFragment2 fragment2 = new ListFragment2();
                fragment2.setArguments(bundle);
                transaction.replace(R.id.frameLayout, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
        return view;
    }
}
