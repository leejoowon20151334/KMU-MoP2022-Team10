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

public class TableFragment1 extends Fragment {
    private View view;
    private Button btn_frag2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.table_fragment1, container, false);
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
}
