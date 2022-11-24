package com.mop2022.team10;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;

public class ListFragment2 extends Fragment {
    private View view;
    private Button btn_frag1;
    ArrayList<IngredientModel> ingredient_test = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment2, container, false);
        btn_frag1 = view.findViewById(R.id.btn_icon2);

        if(getArguments() != null) {
            ingredient_test = (ArrayList<IngredientModel>) getArguments().getSerializable("ingredient");
        }


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

        LinearLayout LL = (LinearLayout) view.findViewById(R.id.list_ll);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Ingredient ingredient = new Ingredient();

                for (int i = 0; i < ingredient_test.size(); i++) {
                    Bitmap img = ingredient.getImg(ingredient_test.get(i).img);
                    TextView tv = new TextView(getActivity());
                    tv.setText(ingredient_test.get(i).name);
                    String str_name = ingredient_test.get(i).name;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView testImg = new ImageView(getActivity());
                            testImg.setTag(str_name);
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
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                TableFragment1 fragment1 = new TableFragment1();
//                transaction.replace(R.id.frameLayout, fragment1);
//                transaction.addToBackStack(null);
//                transaction.commit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ListFragment2.this).commit();
                fragmentManager.popBackStack();


            }
        });
        return view;
    }


}