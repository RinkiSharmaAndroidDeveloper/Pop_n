package com.popn.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastDetailsModel;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.PersonalBroadcastModel;
import com.popn.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastLocationAdapter extends RecyclerView.Adapter<BroadcastLocationAdapter.MyViewHolder> {
    private Context mContext;

    private List<PersonalBroadcastModel> broadcastLocationModelList;
    AsyncResult<BroadcastLocationModel> asyncResult;
    AsyncResult<PersonalBroadcastModel> asyncResult_clickBtn;

    List<String> keyList1 =new ArrayList<>();
    public BroadcastLocationAdapter(Context mContext, List<PersonalBroadcastModel> broadcastLocationModelList, AsyncResult<PersonalBroadcastModel> asyncResult_clickBtn) {
        this.mContext = mContext;
        this.broadcastLocationModelList = broadcastLocationModelList;
        this.asyncResult_clickBtn = asyncResult_clickBtn;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.broadcast_location_adapter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        PersonalBroadcastModel broadcastLocationModel = broadcastLocationModelList.get(position);
       List<String> keywords=null;
        holder.name.setText(broadcastLocationModel.getUserName());
        holder.interest.setText(broadcastLocationModel.getInterest1() + " ,");
        holder.purpose.setText(broadcastLocationModel.getPurpose1());
       // Glide.with(mContext).load("http://vertosys.com/popnapp/" + broadcastLocationModel.).into(circleImageView);
        keywords = splitKeys(broadcastLocationModel.getKeyword1());
        if(keywords.get(0)!=null && keywords.size()<=1) {
            holder.key1.setVisibility(View.VISIBLE);
            holder.key1.setText(keywords.get(0));
            holder.key1.setSingleLine(true);
            holder.key1.setEllipsize(TextUtils.TruncateAt.END);
        }else
        if(keywords.size()<=2) {
            holder.key1.setVisibility(View.VISIBLE);
            holder.key1.setText(keywords.get(0));
            holder.key1.setSingleLine(true);
            holder.key1.setEllipsize(TextUtils.TruncateAt.END);
            holder.key2.setVisibility(View.VISIBLE);
            holder.key2.setText(keywords.get(1));
            holder.key2.setSingleLine(true);
            holder.key2.setEllipsize(TextUtils.TruncateAt.END);
        }else
        if(keywords.size()<=3) {
            holder.key1.setVisibility(View.VISIBLE);
            holder.key1.setText(keywords.get(0));
            holder.key1.setSingleLine(true);
            holder.key1.setEllipsize(TextUtils.TruncateAt.END);
            holder.key2.setVisibility(View.VISIBLE);
            holder.key2.setText(keywords.get(1));
            holder.key2.setSingleLine(true);
            holder.key2.setEllipsize(TextUtils.TruncateAt.END);
            holder.key3.setVisibility(View.VISIBLE);
            holder.key3.setText(keywords.get(2));
            holder.key3.setSingleLine(true);
            holder.key3.setEllipsize(TextUtils.TruncateAt.END);
        }
       holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 asyncResult_clickBtn.success(broadcastLocationModelList.get(position));
             }
         });
    }
public List<String> splitKeys(String keyList){
    String[] arrSplit=null;
   if(keyList1.size()>0){
       keyList1.clear();
   }
     arrSplit = keyList.split(",");
    for (int i=0; i < arrSplit.length; i++)
    {
        keyList1.add(arrSplit[i]);
    }
    return keyList1;
}
    @Override
    public int getItemCount() {
        return broadcastLocationModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, interest, purpose, key1, key2, key3;
        RelativeLayout relativeLayout;
        CircleImageView circleImageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            interest = (TextView) itemView.findViewById(R.id.interest);
            purpose = (TextView) itemView.findViewById(R.id.purpose);
            key1 = (TextView) itemView.findViewById(R.id.key1);
            key2 = (TextView) itemView.findViewById(R.id.key2);
            key3 = (TextView) itemView.findViewById(R.id.key3);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}
