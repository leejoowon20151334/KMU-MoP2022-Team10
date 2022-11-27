package com.mop2022.team10;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    ArrayList<Bitmap> img_list = new ArrayList<>();
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
                        img_list.add(img);

                        final int ind = j;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ImageView testImg = new ImageView(getActivity());
                                testImg.setLayoutParams(new TableRow.LayoutParams(200,200));
                                testImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                testImg.setTag(R.id.name_string, str_name);
                                testImg.setTag(R.id.image_bitmap,img);
                                testImg.setTag(R.id.number, ind);
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
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String projectId = v.getTag(R.id.name_string).toString();
            int index = (int) v.getTag(R.id.number);

            Toast.makeText(getActivity().getApplicationContext(),projectId,Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater1 = getActivity().getLayoutInflater();
            View layout_dlg = inflater1.inflate(R.layout.dialog,null);
            ImageView Img2 = (ImageView) layout_dlg.findViewById(R.id.imageView22);
            Img2.setLayoutParams(new LinearLayout.LayoutParams(400,400));
            Img2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Img2.setImageBitmap(img_list.get(index));
            dlg.setView(layout_dlg);
            dlg.setTitle(projectId + " 추가");

            dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LayoutInflater inflater1 = getActivity().getLayoutInflater();
                    View layout_dlg = inflater1.inflate(R.layout.dialog,null);
                    EditText et1 = (EditText) layout_dlg.findViewById(R.id.editTextDate);
                    EditText et2 = (EditText) layout_dlg.findViewById(R.id.editTextNumberSigned);
                    String a = "";
                    if (et1.getText() != null)
                        a = et1.getText().toString();
                    String b = "";
                    if (et2.getText() != null)
                        b = et2.getText().toString();

                    // 추가버튼 눌렀을 때, 지정한 유통기한과 수량정보 전달
                    if (a.length() > 0 && b.length() > 0) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Activity_3_1.class);
                        intent.putExtra("유통기한정보", a);
                        intent.putExtra("수량정보", b);
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
