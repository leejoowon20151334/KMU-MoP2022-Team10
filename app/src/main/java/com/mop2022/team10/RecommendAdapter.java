package com.mop2022.team10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private ArrayList<recommend> myDataList1 = null;
    RecommendAdapter(ArrayList<recommend> dataList1){myDataList1 = dataList1;}
    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendimageView;
        TextView recommendfood;
        TextView score;
        RatingBar ratingBar;
        RecommendViewHolder(View itemView){
            super(itemView);
            recommendimageView = itemView.findViewById(R.id.recomendimageView);
            recommendfood = itemView.findViewById(R.id.recomendfoodname);
            score = itemView.findViewById(R.id.score);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
    @NonNull
    @Override
    public RecommendAdapter.RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview6,parent,false);//???
        RecommendViewHolder recommendViewHolder = new RecommendViewHolder(view);
        return recommendViewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.recommendimageView.setImageResource(myDataList1.get(position).getImageResourceID());
        holder.recommendfood.setText(myDataList1.get(position).getFoodName());
        holder.ratingBar.setRating(myDataList1.get(position).getRate());
        holder.score.setText("난이도 : "+Float.toString(myDataList1.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return myDataList1.size();
    }
}
