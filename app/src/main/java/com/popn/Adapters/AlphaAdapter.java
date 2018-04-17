package com.popn.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popn.PopModels.ConnectionModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Vs1 on 2/14/2018.
 */

public class AlphaAdapter extends RecyclerView.Adapter<AlphaAdapter.MyViewHolder> {

    private List<String> alpha;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.list_item);


        }
    }
    public AlphaAdapter(List<String> alpha) {
        this.alpha = alpha;

    }

    @Override
    public AlphaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_alphabets, parent, false);

        return new AlphaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlphaAdapter.MyViewHolder holder, int position) {
        String  data = alpha.get(position);
        holder.title.setText(data);


    }

    @Override
    public int getItemCount() {
        return alpha.size();
    }
}