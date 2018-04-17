package com.popn.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.popn.AsyncResult;
import com.popn.PopActivities.IdentiesMainActivity;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Vs1 on 2/15/2018.
 */

public class CustomAdapter extends BaseAdapter {


    Context context;
    List<SocialIdModel> socialIdModels;
    AsyncResult<String> asyncResultSocialId;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context,List<SocialIdModel> socialIdModels,AsyncResult<String> asyncResultSocialId) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.socialIdModels = socialIdModels;
        this.asyncResultSocialId=asyncResultSocialId;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount()
    {
        return socialIdModels.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {

        ImageView os_img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_layout, null);

        holder.os_img = (ImageView) rowView.findViewById(R.id.imageView);
        String uri = "@drawable/"+socialIdModels.get(position).getSocialId();  // where myresource (without the extension) is the file
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.os_img.setImageDrawable(res);


        holder.os_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                asyncResultSocialId.success(socialIdModels.get(position).getSocialId());
            }
        });


        return rowView;
    }
}