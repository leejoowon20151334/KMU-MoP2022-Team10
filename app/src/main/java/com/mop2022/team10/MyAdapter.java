package com.mop2022.team10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<food> myDataList = null;
    MyAdapter(ArrayList<food> dataList){
        myDataList = dataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview3_1,parent,false);//???
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    //아이템 뷰를 관리하는 ViewHolder 객체 생성

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(myDataList.get(position).getImageResourceID());
        holder.food.setText(myDataList.get(position).getFoodName());
        holder.due.setText(myDataList.get(position).getDueDate());
    }
    //position에 해당하는 데이터를 ViewHolder가 관리하는 View에 바인딩(Binding)

    @Override
    public int getItemCount() {
        return myDataList.size();
    }
    //Adapter가 관리하고 있는 데이터 집합의 전체 개수를 리턴
}
