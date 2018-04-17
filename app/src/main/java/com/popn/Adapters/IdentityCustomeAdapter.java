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
import com.popn.PopModels.ConnectionModel;
import com.popn.PopModels.SocialIdModel;
import com.popn.R;

import java.util.List;

/**
 * Created by Vs1 on 4/3/2018.
 */

public class IdentityCustomeAdapter extends BaseAdapter {
    Context context;
    List<SocialIdModel> socialIdModels=null;
    private static LayoutInflater inflater = null;
    Boolean isEdit =false;
    public AsyncResult<String > asyncResult_clickRegisterBtn1;
    AsyncResult<Integer> asyncResult_clickCrossBtn;
    public IdentityCustomeAdapter(Context context,List<SocialIdModel> socialIdModels,Boolean isEdit,AsyncResult<String> asyncResult_clickRegisterBtn1,AsyncResult<Integer> asyncResult_clickCrossBtn) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.socialIdModels = socialIdModels;
        this.isEdit = isEdit;
        this.asyncResult_clickRegisterBtn1 = asyncResult_clickRegisterBtn1;
        this.asyncResult_clickCrossBtn = asyncResult_clickCrossBtn;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public IdentityCustomeAdapter() {

    }

    /*public void isEditScreen(Boolean isEdit,AsyncResult<String> asyncResult_clickRegisterBtn1){
        this.isEdit = isEdit;
        this.asyncResult_clickRegisterBtn1 = asyncResult_clickRegisterBtn1;
    }
*/
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

        ImageView os_img,crossImage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        IdentityCustomeAdapter.Holder holder = new IdentityCustomeAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_layout, null);

        holder.os_img = (ImageView) rowView.findViewById(R.id.imageView);
        holder.crossImage = (ImageView) rowView.findViewById(R.id.cross);
        String uri = "@drawable/"+socialIdModels.get(position).getSocialId();  // where myresource (without the extension) is the file
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.os_img.setImageDrawable(res);


        if(isEdit && !(socialIdModels.get(position).getSocialUrl().equals("imageView_gray_plus"))){
         holder.crossImage.setVisibility(View.VISIBLE);
        }else{
            holder.crossImage.setVisibility(View.GONE);
        }
        holder.os_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(socialIdModels.get(position).getSocialUrl().equals("imageView_gray_plus")){
                    asyncResult_clickRegisterBtn1.success("1");
                }
               // Toast.makeText(context, "You Clicked "+socialIdModels.get(position).getSocialId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.crossImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                asyncResult_clickCrossBtn.success(position);
                // Toast.makeText(context, "You Clicked "+socialIdModels.get(position).getSocialId(), Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}