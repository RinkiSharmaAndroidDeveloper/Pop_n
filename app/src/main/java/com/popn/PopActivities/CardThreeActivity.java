package com.popn.PopActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.popn.Adapters.CardOneAdapter;
import com.popn.Adapters.CardThreeAdapter;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.R;

import java.util.ArrayList;
import java.util.List;

public class CardThreeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CardThreeAdapter cardThreeAdapter;
    private List<BroadcastLocationModel> broadcastLocationModelList;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_three);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back = (ImageView) findViewById(R.id.back);
        broadcastLocationModelList = new ArrayList<>();

        setAdapter();
        prepareBroadcastLocationModel();

        back.setOnClickListener(this);

    }

    public void setAdapter() {
        cardThreeAdapter = new CardThreeAdapter(getApplicationContext(), broadcastLocationModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(cardThreeAdapter);
    }

    private void prepareBroadcastLocationModel() {

        BroadcastLocationModel broadcastLocationModel = new BroadcastLocationModel("Coffee");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Drink");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Breakfast");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Lunch");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Brunch");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Dinner");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Something quick!");
        broadcastLocationModelList.add(broadcastLocationModel);

        cardThreeAdapter.notifyDataSetChanged();
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
