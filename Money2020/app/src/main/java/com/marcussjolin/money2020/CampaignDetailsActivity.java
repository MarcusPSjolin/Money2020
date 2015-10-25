package com.marcussjolin.money2020;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CampaignDetailsActivity extends AppCompatActivity {

    public static final String CAMPAIGN = "campaign";

    private Campaign mCampaign;
    private CampaignDetailsActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_details);

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

        mCampaign = getIntent().getParcelableExtra(CAMPAIGN);
        Bitmap bitmap = ((BitmapDrawable) getCardBackground(mCampaign.getTitle())).getBitmap();

        setUpGraph();

        TextView title = (TextView) findViewById(R.id.details_title);
        TextView desc = (TextView) findViewById(R.id.details_description);
        TextView address = (TextView) findViewById(R.id.details_address);
        ImageView image = (ImageView) findViewById(R.id.details_image);

        title.setText(mCampaign.getTitle());
        desc.setText(mCampaign.getDescription());
        address.setText(mCampaign.getTargetAddress());
        if (bitmap == null) {
            Log.d("TAG", "Bitmap is null");
        }
        image.setImageBitmap(bitmap);

    }

    public Drawable getCardBackground(String title) {
        Drawable res;
        switch (title) {
            case Money2020Application.NFL:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.nfl);
                break;
            case Money2020Application.BREAST_CANCER:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.breast_cancer);
                break;
            case Money2020Application.HALLOWEEN:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.halloween_background);
                break;
            case Money2020Application.MONEY_2020:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.raining_cash);
                break;
            default:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.brick_and_mortar);
        }
        return res;
    }

    private void setUpGraph() {
        String url = Money2020Application.BASE_URL + Money2020Application.GRAPH_1 + mCampaign.getId() + Money2020Application.GRAPH_2;
        RequestQueue queue = Volley.newRequestQueue(this);
        Response.ErrorListener listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "Oops, something went wrong.", Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BarChart barChart = (BarChart) findViewById(R.id.details_chart);
                barChart.setData(getBarData(response));
                barChart.setMinimumHeight(500);
                barChart.invalidate();
            }
        }, listener);
        queue.add(request);
    }

    private BarData getBarData(JSONObject response) {
        try {
            ArrayList<BarEntry> yVals = new ArrayList<>();

            List<String> xVals = new ArrayList<>();
            JSONArray xArray = response.getJSONArray("x");
            JSONArray yArray = response.getJSONArray("y");

            for (int i = 0; i < xArray.length(); i++) {
                Random rand = new Random();
                float y = rand.nextFloat();
                xVals.add(xArray.getString(i));
                yVals.add(new BarEntry(y * 15, i));
            }

            BarDataSet dataSet = new BarDataSet(yVals, "Sales");

            return new BarData(xVals, dataSet);
        } catch (JSONException e) {
            Log.d("TAG", "JSONException in getBarData e = " + e);
            return null;
        }
    }
}
