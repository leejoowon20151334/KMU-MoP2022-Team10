package com.mop2022.team10;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        //Log.d("로그", ingredient_test.get(0).name);

        TableLayout TL = (TableLayout) view.findViewById(R.id.tl);


        Ingredient ingredient = new Ingredient();
//                for (int k = 0; k < ingredient_test.size(); k++) {
//                    img_list.add(ingredient.getImg(ingredient_test.get(k).img));
//
//                }
        Log.d("로그", "s");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ingredient_test.size(); i += 3) {
                    if(i == 9)
                        break;
                    TableRow tableRow = new TableRow(getActivity());
                    tableRow.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    tableRow.setGravity(Gravity.CENTER);
                    Log.d("로그3", ingredient_test.get(i).name);


                    for (int j = i; j < i + 3; j++) {
                        if (j == ingredient_test.size())
                            break;
                        //Log.d("로그" + j, ingredient_test.get(j).name);
//
//                        Button btn = new Button(getActivity());
//                        btn.setText(ingredient_test.get(j).name);
//                        btn.setOnClickListener(myListener);

                        //Bitmap img = ingredient.getImg("1tqbkNL0fsW75CB10XsU6_7rsWjMp00Dl");
                        //Bitmap img = ingredient.getImg(ingredient_test.get(ind).img);
                        Log.d("로그", "sssss");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("로그", "aaaaa");

                                LinearLayout ll = new LinearLayout(getActivity());
                                ll.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                ll.setOrientation(LinearLayout.VERTICAL);

                                ImageView testImg = new ImageView(getActivity());
                                //testImg.setImageDrawable(R.drawable.img_milk);
                                //testImg.setImageBitmap(img);


                                ll.addView(testImg);
                                tableRow.addView(ll);
                            }
                        });
                    }
                    TL.addView(tableRow);
                }
            }
        });
        t.start();

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectId = v.getTag().toString();

            }
        };

//        for(int i=0;i<ingredient_test.size();i++)
//        {
//            Button tmpBtn = new Button(getActivity().getApplicationContext());
//            tmpBtn.setText(ingredient_test.get(i).name);
//            tmpBtn.setOnClickListener(myListener);
//            TL.addView(tmpBtn);
//        }




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
                transaction.commit();
            }

        });
        return view;
    }
}
