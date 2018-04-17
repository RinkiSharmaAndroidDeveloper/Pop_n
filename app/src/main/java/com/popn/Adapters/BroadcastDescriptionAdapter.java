package com.popn.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popn.PopModels.BroadcastLocationModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Android-Dev2 on 4/13/2018.
 */

public class BroadcastDescriptionAdapter extends RecyclerView.Adapter<BroadcastDescriptionAdapter.MyViewHolder> {
    private Context mContext;
    private List<BroadcastLocationModel> broadcastLocationModelList;


    public BroadcastDescriptionAdapter(Context mContext, List<BroadcastLocationModel> broadcastLocationModelList){
        this.mContext = mContext;
        this.broadcastLocationModelList = broadcastLocationModelList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.broadcast_description_adapter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BroadcastLocationModel broadcastLocationModel = broadcastLocationModelList.get(position);
        holder.name.setText(broadcastLocationModel.getName());
        holder.message.setText(broadcastLocationModel.getMessage());

    }

    @Override
    public int getItemCount() {
        return broadcastLocationModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, message;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Name);
            message = (TextView) itemView.findViewById(R.id.message);
        }
    }

}
