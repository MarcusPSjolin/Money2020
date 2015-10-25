package com.marcussjolin.money2020;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateCampaignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button createCampaign = (Button) findViewById(R.id.create_campaign_button);
        createCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCampaign();
            }
        });
    }

    private void createCampaign() {
        EditText title = (EditText) findViewById(R.id.title);
        EditText description = (EditText) findViewById(R.id.description);
        EditText streetAddress = (EditText) findViewById(R.id.target_address);
        EditText city = (EditText) findViewById(R.id.target_city);
        EditText state = (EditText) findViewById(R.id.target_state);
        EditText radius = (EditText) findViewById(R.id.radius);
        EditText dailyBudget = (EditText) findViewById(R.id.daily_budget);

        StringBuilder address = new StringBuilder();
        address.append(streetAddress).append(", ").append(city).append(", ").append(state);

        Campaign campaign = new Campaign(
                null,
                title.getText().toString(),
                description.getText().toString(),
                address.toString(),
                Float.parseFloat(dailyBudget.getText().toString()),
                Float.parseFloat(radius.getText().toString()));
    }
}
