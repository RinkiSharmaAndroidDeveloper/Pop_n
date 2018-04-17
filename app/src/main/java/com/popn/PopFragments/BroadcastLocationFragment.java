package com.popn.PopFragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popn.Adapters.BroadcastLocationAdapter;
import com.popn.AsyncResult;
import com.popn.PopActivities.BroadcastRequestActivity;
import com.popn.PopActivities.Network_url;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.PopModels.IdentityInformationModel;
import com.popn.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastLocationFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private BroadcastLocationAdapter broadcastLocationAdapter;
    private List<BroadcastLocationModel> broadcastLocationModelList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.broadcast_location_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        broadcastLocationModelList = new ArrayList<>();
        setAdapter();
        prepareBroadcastLocationModel();

        return view;
    }

    AsyncResult<BroadcastLocationModel> asyncResult = new AsyncResult<BroadcastLocationModel>() {
        @Override
        public void success(BroadcastLocationModel broadcastLocationModel) {
            Intent intent = new Intent(getActivity(), BroadcastRequestActivity.class);
            intent.putExtra("Name", broadcastLocationModel.getName());
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };

    public void setAdapter() {
        broadcastLocationAdapter = new BroadcastLocationAdapter(getContext(), broadcastLocationModelList,asyncResult);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(broadcastLocationAdapter);
    }

    private void prepareBroadcastLocationModel() {

        BroadcastLocationModel broadcastLocationModel = new BroadcastLocationModel("andrew");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("brad");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("ranoldo");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("flintopp");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

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
