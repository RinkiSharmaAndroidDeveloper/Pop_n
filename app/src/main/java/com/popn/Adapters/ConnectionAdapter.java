package com.popn.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popn.AsyncResult;
import com.popn.PopModels.ConnectionModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Vs1 on 2/13/2018.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.MyViewHolder> {

    private List<ConnectionModel> connectionModelList;
    Context context;
    AsyncResult<ConnectionModel > asyncResult_clickConnection;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.social_name);
            imageView = (ImageView) view.findViewById(R.id.image);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);

        }
    }
    public ConnectionAdapter(List<ConnectionModel> connectionModelList,Context context,AsyncResult<ConnectionModel > asyncResult_clickConnection) {
        this.connectionModelList = connectionModelList;
        this.context = context;
        this.asyncResult_clickConnection = asyncResult_clickConnection;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_connection, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ConnectionModel movie = connectionModelList.get(position);
        holder.title.setText(movie.getTitle());
        String uri = "@drawable/"+movie.getImageUrl();  // where myresource (without the extension) is the file
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.imageView.setImageDrawable(res);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncResult_clickConnection.success(connectionModelList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return connectionModelList.size();
    }
}