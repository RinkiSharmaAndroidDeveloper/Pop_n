package com.popn.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastLocationAdapter extends RecyclerView.Adapter<BroadcastLocationAdapter.MyViewHolder> {
    private Context mContext;

    private List<BroadcastLocationModel> broadcastLocationModelList;
    AsyncResult<BroadcastLocationModel> asyncResult;

    public BroadcastLocationAdapter(Context mContext, List<BroadcastLocationModel> broadcastLocationModelList,AsyncResult<BroadcastLocationModel> asyncResult){
           this.mContext = mContext;
           this.broadcastLocationModelList = broadcastLocationModelList;
           this.asyncResult = asyncResult;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.broadcast_location_adapter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
         BroadcastLocationModel broadcastLocationModel = broadcastLocationModelList.get(position);
         holder.name.setText(broadcastLocationModel.getName());
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
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
        public TextView name;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}
