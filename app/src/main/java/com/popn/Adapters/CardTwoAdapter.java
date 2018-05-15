package com.popn.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.popn.AsyncResult;
import com.popn.PopModels.InterestModel;
import com.popn.PopModels.KeywordModel;
import com.popn.PopModels.PurposeModel;
import com.popn.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Android-Dev2 on 4/20/2018.
 */

public class CardTwoAdapter extends RecyclerView.Adapter<CardTwoAdapter.MyViewHolder> {
    private Context mContext;
    private List<KeywordModel> keywordModelList;
    AsyncResult<KeywordModel> asyncResult;


    public CardTwoAdapter(){

    }

    public CardTwoAdapter(Context mContext, List<KeywordModel> keywordModelList, AsyncResult<KeywordModel> asyncResult){
        this.mContext = mContext;
        this.keywordModelList = keywordModelList;
        this.asyncResult = asyncResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_two_adapter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // InterestModel interestModel = interestModelList.get(position);
      //  holder.interestName.setText(interestModelList.get(position).getInterestName());
        //holder.interestKeyword.setText(interestModel.getInterestName());
       /* holder.tv.setText(keywordModelList.get(position).getKeywordName());
        holder.tv.setHeight(90);
        holder.tv.setTextSize(16.0f);
        holder.tv.setGravity(Gravity.CENTER);
        holder.tv.setTextColor(Color.parseColor("#868383"));
        if(position%2 == 0){
            holder.tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_pink));
        }else{
            holder.tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_blue));
        }

        holder.tv.setId(position + 1);

        holder.tv.setTag(position);
        holder.tv.setPadding(20, 10, 20, 10);*/

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(10, 10, 10, 10);
        final TextView tv = new TextView(mContext);

        tv.setText(keywordModelList.get(position).getKeywordName());

        tv.setHeight(90);
        tv.setTextSize(16.0f);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#868383"));

        if(position%2 == 0){
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_pink));
        }else{
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_blue));
        }

        tv.setId(position + 1);
        tv.setLayoutParams(buttonLayoutParams);
        tv.setTag(position);
        tv.setPadding(20, 10, 20, 10);

        holder.container.addView(tv);

        /*for(int i=0;i<keywordModelList.size();i++){


            tv.setText(keywordModelList.get(i).getKeywordName());

            tv.setHeight(90);
            tv.setTextSize(16.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#868383"));

            if(i%2 == 0){
                tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_pink));
            }else{
                tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_corner_blue));
            }

            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(20, 10, 20, 10);

            holder.container.addView(tv);
        }*/

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncResult.success(keywordModelList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return keywordModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public FlexboxLayout container;
        public TextView tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            container= (FlexboxLayout) itemView.findViewById(R.id.v_container);
           // tv = (TextView) itemView.findViewById(R.id.tv);


        }
    }

}
