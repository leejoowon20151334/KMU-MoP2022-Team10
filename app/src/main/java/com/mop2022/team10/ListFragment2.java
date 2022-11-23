package com.mop2022.team10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

        LinearLayout LL = (LinearLayout) view.findViewById(R.id.list_ll);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Ingredient ingredient = new Ingredient();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < ingredient_test.size(); i++) {
                            Button btn = new Button(getActivity());
                            btn.setText(ingredient_test.get(i).name);
                            btn.setOnClickListener(myListener);
                            LL.addView(btn);
                        }
                    }
                });
            }
        });
        t.start();

        btn_frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                TableFragment1 fragment1 = new TableFragment1();
                transaction.replace(R.id.frameLayout, fragment1);
                transaction.commit();
            }
        });
        return view;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}