package com.mop2022.team10;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView food;
    TextView due;
    ViewHolder(View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView2);
        food = itemView.findViewById(R.id.foodname);
        due = itemView.findViewById(R.id.duedate);
    }

}
