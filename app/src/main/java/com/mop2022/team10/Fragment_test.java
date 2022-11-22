package com.mop2022.team10;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mop2022.team10.Rest.Model.IngredientModel;

import java.util.ArrayList;


public class Fragment_test extends Fragment {
    private View view;
    private Button btn_frag2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_test, container, false);

        ArrayList<String> ingredient_test = new ArrayList<>();
        if(getArguments() != null) {
            ingredient_test = (ArrayList<String>) getArguments().getStringArrayList("ingredient22");
        }

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);

//        for(int i=0;i<4;i++) {
//            Button tmpBtn = new Button(getActivity().getApplicationContext());
//            tmpBtn.setText(""+i);
//            tmpBtn.setOnClickListener(myListener);
//            ll.addView(tmpBtn);
//        }

        for(int i=0;i<ingredient_test.size();i++)
        {
            Button tmpBtn = new Button(getActivity().getApplicationContext());
            tmpBtn.setText(ingredient_test.get(i));
            tmpBtn.setOnClickListener(myListener);
            ll.addView(tmpBtn);
        }


        btn_frag2 = view.findViewById(R.id.btn_icon1);
        btn_frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ListFragment2 fragment2 = new ListFragment2();
                transaction.replace(R.id.frameLayout, fragment2);
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
