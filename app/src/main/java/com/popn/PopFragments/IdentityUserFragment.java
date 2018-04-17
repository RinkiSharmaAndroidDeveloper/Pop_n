package com.popn.PopFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.popn.Adapters.CustomAdapter;
import com.popn.AsyncResult;
import com.popn.R;

/**
 * Created by Vs1 on 2/15/2018.
 */

public class IdentityUserFragment extends Fragment {
    AsyncResult<String> asyncResult_selectedCategory;
    ImageView imageView;
    GridView gridView;
    String[] osImages = {
            "whatsapp_32",
            "insta_32",
            "phone",
            "canvas_32",
            "email_32",
            "sky_32",
            "sms_32",
            "linkedin_32",
            "snapchat_32",
            "tiwtter_32",
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public IdentityUserFragment newInstance(AsyncResult<String> asyncResult_selectedCategory) {
        IdentityUserFragment fragment = new IdentityUserFragment();
        Bundle args = new Bundle();
        this.asyncResult_selectedCategory = asyncResult_selectedCategory;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identities_details, container, false);
        //      imageView =(ImageView)view.findViewById(R.id.imageView_gray_plus);
        gridView = (GridView) view.findViewById(R.id.customgrid);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gridView.setAdapter(new CustomAdapter(getContext().getApplicationContext(), osImages));
        }*/
     /*   imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                asyncResult_selectedCategory.success("1");
            }
        });*/
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
