package com.popn.PopActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.popn.Adapters.BroadcastDescriptionAdapter;
import com.popn.Adapters.CardOneAdapter;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.R;

import java.util.ArrayList;
import java.util.List;

public class BroadcastDescription extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private BroadcastDescriptionAdapter broadcastDescriptionAdapter;
    private List<BroadcastLocationModel> broadcastLocationModelList;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_description);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back = (ImageView) findViewById(R.id.back);

        broadcastLocationModelList = new ArrayList<>();

        setAdapter();
        prepareBroadcastLocationModel();

        back.setOnClickListener(this);

    }

    public void setAdapter() {
        broadcastDescriptionAdapter = new BroadcastDescriptionAdapter(getApplicationContext(), broadcastLocationModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(broadcastDescriptionAdapter);
    }

    private void prepareBroadcastLocationModel() {

        BroadcastLocationModel broadcastLocationModel = new BroadcastLocationModel("Ramneesh", "Nice!");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Rohit", "I know him too!");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Edward", "It was nice meeting you!");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastDescriptionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.back):
                finish();
                break;
        }
    }

}
