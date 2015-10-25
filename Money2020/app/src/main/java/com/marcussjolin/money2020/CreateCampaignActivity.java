package com.marcussjolin.money2020;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class CreateCampaignActivity extends AppCompatActivity {

    private CreateCampaignActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        mActivity = this;

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

        String title = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.size() > 0) {
            String type = getIntent().getStringExtra(MainActivity.TYPE);
            if (type.equals(MainActivity.NEW_ITEMS)) {
                title = getIntent().getStringExtra(MainActivity.NAME);
            } else {
                title = getIntent().getStringExtra(MainActivity.TITLE);
            }
        }
        EditText editTitle = (EditText) findViewById(R.id.title);
        if (title != null) {
            editTitle.setText(title);
            EditText description = (EditText) findViewById(R.id.description);
            description.requestFocus();
        }
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
        address.append(streetAddress.getText().toString()).append(", ").append(city.getText()
                .toString()).append(", ").append(state.getText().toString());

        Campaign campaign = new Campaign(
                null,
                title.getText().toString(),
                description.getText().toString(),
                address.toString(),
                Float.parseFloat(dailyBudget.getText().toString()),
                Float.parseFloat(radius.getText().toString())
        );

        RequestQueue queue = Volley.newRequestQueue(mActivity);
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "There was a problem creating your campaign.", Toast.LENGTH_LONG).show();
            }
        };

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(MainActivity.NAME, campaign.getTitle());
            jsonObject.put(MainActivity.DESCRIPTION, campaign.getDescription());
            jsonObject.put(MainActivity.DAILY_BUDGET, campaign.getDailyBudget());
            jsonObject.put(MainActivity.FACEBOOK_PAGE_ID, UUID.randomUUID());
            jsonObject.put(MainActivity.TARGET_ADDRESS, campaign.getTargetAddress());
            jsonObject.put(MainActivity.TARGET_RADIUS, campaign.getRadius());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    Money2020Application.CAMPAIGNS_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(mActivity, "Campaign was successfully created!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }, errorListener);
            queue.add(request);
        } catch (JSONException e) {
            Log.d("TAG", "JSONException in createCampaign()");
        }
    }
}
