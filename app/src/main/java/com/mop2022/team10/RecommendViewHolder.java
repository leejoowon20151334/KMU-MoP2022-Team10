package com.mop2022.team10;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendimageView;
        TextView recommendfood;
        RecommendViewHolder(View itemView){
            super(itemView);
            recommendimageView = itemView.findViewById(R.id.recomendimageView);
            recommendfood = itemView.findViewById(R.id.recomendfoodname);
        }
}


