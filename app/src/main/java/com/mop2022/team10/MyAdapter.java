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
        TextView explain;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            food = itemView.findViewById(R.id.foodname);
            due = itemView.findViewById(R.id.duedate);
            explain = itemView.findViewById(R.id.explain);
            relativeLayout = itemView.findViewById(R.id.layoutrelative);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    food fooddata;
                    fooddata = myDataList.get(pos);
                    Intent intent = new Intent(context, ingredientPopup.class);
//                    Intent intent = new Intent(context, ingredientPopup.class);
                    intent.putExtra("name",fooddata.getFoodName());
//                    intent.putExtra("image",fooddata.getImageResource());

                    intent.putExtra("position",pos);
                    intent.putExtra("count",fooddata.getCount());
                    intent.putExtra("unit",fooddata.getUnit());
                    intent.putExtra("year",fooddata.getExperationDate().getYear());
                    intent.putExtra("month",fooddata.getExperationDate().getMonthValue());
                    intent.putExtra("day",fooddata.getExperationDate().getDayOfMonth());
                    context.startActivity(intent);
//                    Intent intent = new Intent(context,MainActivity.class);
//                    context.startActivity(intent);
                }
            });
        }
    }
    //???????????? ?????? ????????? ??????
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
    //????????? ?????? ???????????? ViewHolder ?????? ??????

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(myDataList.get(position).getImageResource());
        holder.food.setText(myDataList.get(position).getFoodName());
        if(myDataList.get(position).getDueDate()<4){
            holder.due.setText(Integer.toString(myDataList.get(position).getDueDate())+"???");
            holder.explain.setText("?????? ????????? ??????!");
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFDDDD"));
        }
        else{
            holder.due.setText(Integer.toString(myDataList.get(position).getDueDate())+"???");
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#CCFFCC"));//Color.parseColor("#90EE90"
        }
        // ??? ????????? ?????? ????????? ????????? ?????? ????????? ??????????????? ?????? ?????????.
    }    //position??? ???????????? ???????????? ViewHolder??? ???????????? View??? ?????????(Binding)
    @Override
    public int getItemCount() {
        return myDataList.size();
    }
    //Adapter??? ???????????? ?????? ????????? ????????? ?????? ????????? ??????
}
