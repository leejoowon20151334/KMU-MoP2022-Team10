package com.mop2022.team10;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.table_fragment1, container, false);

        ArrayList<IngredientModel> ingredient_test = new ArrayList<>();
        if(getArguments() != null) {
            ingredient_test = (ArrayList<IngredientModel>) getArguments().get("ingredient");
        }

        LinearLayout ll = new LinearLayout(getActivity());

        ll.setOrientation(LinearLayout.VERTICAL);


        for(int i=0;i<ingredient_test.size();i++)
        {
            Button tmpBtn = new Button(getActivity().getApplicationContext());
            tmpBtn.setText(ingredient_test.get(i).name);
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
