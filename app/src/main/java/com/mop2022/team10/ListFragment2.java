package com.mop2022.team10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ListFragment2 extends Fragment {
    private View view;
    private Button btn_frag1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment2, container, false);
        btn_frag1 = view.findViewById(R.id.btn_icon2);

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
}