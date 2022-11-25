package com.mop2022.team10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<food> myDataList = null;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView food;
        TextView due;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            food = itemView.findViewById(R.id.foodname);
            due = itemView.findViewById(R.id.duedate);
            relativeLayout = itemView.findViewById(R.id.layoutrelative);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    food fooddata;
                    fooddata = myDataList.get(pos);
                    Intent intent = new Intent(context, Popup_3_1.class);
                    intent.putExtra("data",fooddata.getFoodName());
                    intent.putExtra("image",fooddata.getImageResource());
                    intent.putExtra("count",fooddata.getCount());
                    context.startActivity(intent);
                }
            });
        }
    }
    //생성자를 통해 데이터 전달
    public MyAdapter(ArrayList<food> dataList){
        myDataList = dataList;
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview3_1,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    //아이템 뷰를 관리하는 ViewHolder 객체 생성

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(myDataList.get(position).getImageResource());
        holder.food.setText(myDataList.get(position).getFoodName());
        if(myDataList.get(position).getDueDate()<4){
            holder.due.setText(Integer.toString(myDataList.get(position).getDueDate())+"일 유통기한이 얼마 남지 않았어요!!");
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FA8072"));
        }
        else{
            holder.due.setText(Integer.toString(myDataList.get(position).getDueDate())+"일");
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#90EE90"));
        }
        // 아래는 남은 일자에 따라서 배경 색깔을 바꾸어주는 코드 입니다.
//        int color;
//        color = myDataList.get(position).getDueDate();
//        if(color<4){holder.relativeLayout.setBackgroundColor(Color.parseColor("#FA8072"));}
//        else if(color>3 && color<8){holder.relativeLayout.setBackgroundColor(Color.parseColor("#AFEEEE"));}
//        else{holder.relativeLayout.setBackgroundColor(Color.parseColor("#90EE90"));}
    }    //position에 해당하는 데이터를 ViewHolder가 관리하는 View에 바인딩(Binding)
    @Override
    public int getItemCount() {
        return myDataList.size();
    }
    //Adapter가 관리하고 있는 데이터 집합의 전체 개수를 리턴
}
