package com.mop2022.team10;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;

import java.util.ArrayList;

public class Activity_3_2 extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3_2);

        // 뒤로가기 버튼을 눌렀을 때
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3-1로 다시 넘어가기 위해 3-2 종료하기
                finish();
            }
        });




        Bundle bundle = new Bundle();
        ArrayList<String> filterIngredient = new ArrayList<>();
        ArrayList<IngredientModel> ingredientList2 = new ArrayList<IngredientModel>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Ingredient ingredient = new Ingredient();
                ArrayList<IngredientModel> ingredientList = ingredient.searchIngredient("");

                //Log.d("로그", ingredientList.get(0).name);
                for(int i=0;i<ingredientList.size();i++) {
                    ingredientList2.add(ingredientList.get(i));
                    //Log.d("로그2"+i, ingredientList2.get(i).name);
                }
                bundle.putSerializable("ingredient", ingredientList2);
                //Log.d("로그2", ingredientList2.get(0).name);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                TableFragment1 fragment1= new TableFragment1(); // 객체 생성
                fragment1.setArguments(bundle);
                transaction.replace(R.id.frameLayout, fragment1); // layout, 교체될 layout
                transaction.commit(); // commit으로 저장 하지 않으면 화면 전환이 되지 않음
            }
        });
        t.start();


        // ############################################################################ //


        // 검색 기능을 이용할 때
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"sss",Toast.LENGTH_SHORT).show();

                for (int i=0;i<ingredientList2.size();i++) {

                    IngredientModel searchedIngredient = ingredientList2.get(i);

                    if(searchedIngredient.name.toLowerCase().contains(query.toLowerCase())) {
                        filterIngredient.add(searchedIngredient.name);
                    }
                }

                AlertDialog.Builder dlg = new AlertDialog.Builder(Activity_3_2.this);
                LayoutInflater inflater1 = getLayoutInflater();
                dlg.setView(inflater1.inflate(R.layout.dialog,null));
                dlg.setTitle(filterIngredient + " 추가");

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ingredient", filterIngredient);
                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 추가버튼 눌렀을 때, 지정한 유통기한과 수량정보 전달
                    }
                });
                dlg.setNegativeButton("취소",null);

                dlg.show();

//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                Fragment_test fragment_test = new Fragment_test(); // 객체 생성
//                fragment_test.setArguments(bundle);
//                transaction.replace(R.id.frameLayout, fragment_test); // layout, 교체될 layout
//                transaction.commit(); // commit으로 저장 하지 않으면 화면 전환이 되지 않음



                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
