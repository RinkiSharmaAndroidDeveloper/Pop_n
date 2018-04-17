package com.popn.PopActivities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import com.popn.R;


public class KeywordsActivity extends AppCompatActivity {
    List<String> list_text=new ArrayList<>();
   // public FlexboxLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywords);

     //   container=(FlexboxLayout) findViewById(R.id.tv_container);
        list_text.add("Avik");
        list_text.add("Rocky bolboa");
        list_text.add("Master");
        list_text.add("avhihsked");
        list_text.add("Prem chopra");
        list_text.add("Rambo");
        list_text.add("Piyali");
        list_text.add("Sheha Singh");
        list_text.add("Sheha Sarker");
        list_text.add("Supriya");
        list_text.add("Manish Singh");
        list_text.add("Chacha ji");
        list_text.add("DEbarun da");
        list_text.add("Asaduk da");
        list_text.add("Sunidhi chauhan");
        list_text.add("Shreya");
        list_text.add("Sourav mitra");

        inflatelayout();

    }

    private void inflatelayout() {
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(5,5,5,5);
        for(int i=0;i<list_text.size();i++){
            final TextView tv = new TextView(getApplicationContext());
            tv.setText(list_text.get(i));
            tv.setHeight(100);
            tv.setTextSize(16.0f);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setBackground(getResources().getDrawable(R.drawable.rounded_corner_yellow));
            tv.setId(i + 1);
            tv.setLayoutParams(buttonLayoutParams);
            tv.setTag(i);
            tv.setPadding(20, 10, 20, 10);
           // container.addView(tv);
        }

    }
}