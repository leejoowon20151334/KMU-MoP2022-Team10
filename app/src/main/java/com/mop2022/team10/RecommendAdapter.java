package com.mop2022.team10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private ArrayList<recommend> myDataList1 = null;
    private Context context;

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendimageView;
        TextView recommendfood;
        TextView score;
        TextView time;
        TextView evaluate;
        RecommendViewHolder(View itemView){
            super(itemView);
            recommendimageView = itemView.findViewById(R.id.recomendimageView);
            recommendfood = itemView.findViewById(R.id.recomendfoodname);
            score = itemView.findViewById(R.id.score);
            time = itemView.findViewById(R.id.time);
            evaluate = itemView.findViewById(R.id.recipeDetail_evaluation6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    recommend recommendselect;
                    recommendselect = myDataList1.get(pos);
                    Intent intent = new Intent(context,RecipeDetailActivity.class);
                    intent.putExtra("recipeId",recommendselect.getRecipeId());
                    context.startActivity(intent);

//                    Toast.makeText(context,recommendselect.getFoodName(),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public RecommendAdapter(ArrayList<recommend> dataList1){myDataList1 = dataList1;}
//    public RecommendAdapter(ArrayList<recommend> myDataList){myDataList1 = myDataList;}
    @NonNull
    @Override
    public RecommendAdapter.RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cardview6,parent,false);//???
        RecommendViewHolder recommendViewHolder = new RecommendViewHolder(view);
        return recommendViewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.recommendimageView.setImageBitmap(myDataList1.get(position).getImageResource());
        holder.recommendfood.setText(myDataList1.get(position).getFoodName());
        if(myDataList1.get(position).getRate()<3){holder.score.setText("쉬워요");}
        else if(myDataList1.get(position).getRate()<5){holder.score.setText("약간 쉬워요");}
        else if(myDataList1.get(position).getRate()<7){holder.score.setText("할만해요");}
        else if(myDataList1.get(position).getRate()<9){holder.score.setText("조금어려워요");}
        else{holder.score.setText("어려워요");}
        holder.time.setText(Integer.toString(myDataList1.get(position).getTime())+"분");
        holder.evaluate.setText(Double.toString(myDataList1.get(position).getEvaluate()));
//        holder.score.setText("난이도 : "+Float.toString(myDataList1.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return myDataList1.size();
    }
}
