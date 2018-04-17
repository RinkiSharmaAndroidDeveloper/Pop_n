package com.popn.Adapters;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popn.AsyncResult;
import com.popn.R;
import com.popn.PopModels.User;

import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

/**
 * Created by Vs1 on 2/8/2018.
 */

public class UserItemViewBinder extends ViewBinder<User, UserItemViewBinder.ViewHolder> {
    AsyncResult<String > asyncResult_addNewConnection;

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    public UserItemViewBinder(AsyncResult<String > asyncResult_addNewConnection) {
        this.asyncResult_addNewConnection=asyncResult_addNewConnection;
    }

    @Override
    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, User entity) {
        String userFName =entity.getUserFName();
        String userLName =entity.getUserLName();
         final String userId=entity.getUserID();
        // mattie.setTypeface(null, Typeface.BOLD);
        //String Name = userLName +  " " + "<b>" + userFName + "</b>" ;
        String Name = "<b>" + userFName + "</b>" ;
        holder.tvId.setText(Html.fromHtml(Name));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               asyncResult_addNewConnection.success(userId);
                                           }
                                       }
        );

    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.item_user;
    }

    static class ViewHolder extends ViewBinder.ViewHolder {
        public TextView tvId;
         LinearLayout linearLayout;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvId = (TextView) rootView.findViewById(R.id.tv_id);
            this.linearLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout);

        }

    }
}
