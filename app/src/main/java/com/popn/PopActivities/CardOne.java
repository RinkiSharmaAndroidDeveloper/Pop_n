package com.popn.PopActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.popn.Adapters.BroadcastLocationAdapter;
import com.popn.Adapters.CardOneAdapter;
import com.popn.AsyncResult;
import com.popn.PopModels.BroadcastLocationModel;
import com.popn.R;

import java.util.ArrayList;
import java.util.List;

public class CardOne extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CardOneAdapter cardOneAdapter;
    private List<BroadcastLocationModel> broadcastLocationModelList;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_one);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back = (ImageView) findViewById(R.id.back);
        broadcastLocationModelList = new ArrayList<>();

        setAdapter();
        prepareBroadcastLocationModel();

        back.setOnClickListener(this);
    }


    public void setAdapter() {
        cardOneAdapter = new CardOneAdapter(getApplicationContext(), broadcastLocationModelList, asyncResult);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(cardOneAdapter);
    }

    AsyncResult<BroadcastLocationModel> asyncResult = new AsyncResult<BroadcastLocationModel>() {
        @Override
        public void success(BroadcastLocationModel broadcastLocationModel) {
            Intent intent = new Intent(CardOne.this, CustomKeywordActivity.class);
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };

    private void prepareBroadcastLocationModel() {

        BroadcastLocationModel broadcastLocationModel = new BroadcastLocationModel("Social");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Business");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Sales");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Date");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Quick Chat");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Networking");
        broadcastLocationModelList.add(broadcastLocationModel);

        broadcastLocationModel = new BroadcastLocationModel("Show me Around!");
        broadcastLocationModelList.add(broadcastLocationModel);

        cardOneAdapter.notifyDataSetChanged();
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
