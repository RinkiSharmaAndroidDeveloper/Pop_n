package com.popn.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.InterestModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Android-Dev2 on 4/12/2018.
 */

public class CardThreeAdapter extends RecyclerView.Adapter<CardThreeAdapter.MyViewHolder> {
    private Context mContext;
    private List<InterestModel> broadcastLocationModelList;

    AsyncResult<InterestModel> asyncResult;
    public CardThreeAdapter(){

    }

    public CardThreeAdapter(Context mContext, List<InterestModel> broadcastLocationModelList,AsyncResult<InterestModel> asyncResult){
        this.mContext = mContext;
        this.broadcastLocationModelList = broadcastLocationModelList;
        this.asyncResult = asyncResult;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_three_adapter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        InterestModel broadcastLocationModel = broadcastLocationModelList.get(position);
        holder.broadcastName.setText(broadcastLocationModel.getInterestName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncResult.success(broadcastLocationModelList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return broadcastLocationModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView broadcastName;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            broadcastName = (TextView) itemView.findViewById(R.id.broadcastName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }


}
