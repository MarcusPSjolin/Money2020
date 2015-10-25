package com.marcussjolin.money2020;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CampaignsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CampaignsActivity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaigns);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActivity = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getCampaigns();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, CreateCampaignActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_campaigns);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCampaigns();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.campaigns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCampaigns() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        Response.ErrorListener listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, "Could not retrieve your campaigns at this time.", Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Money2020Application.CAMPAIGNS_URL,
                new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Campaign> campaignList = getCampaignsListFromJson(response);
                mAdapter = new CampaignAdapter(campaignList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, listener);

        queue.add(request);
    }

    private List<Campaign> getCampaignsListFromJson(JSONArray array) {
        List<Campaign> campaigns = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                String title = object.getString(MainActivity.NAME);
                String desc = object.getString(MainActivity.DESCRIPTION);
                float dailyBudget = (float) object.getDouble(MainActivity.DAILY_BUDGET);
                String address = object.getString(MainActivity.TARGET_ADDRESS);
                float radius = (float) object.getDouble(MainActivity.TARGET_RADIUS);
                String startDate = object.getString(MainActivity.START_DATE);
                String endDate = object.getString(MainActivity.END_DATE);
                String id = object.getString(MainActivity.ID);
                Campaign campaign = new Campaign(id, null, title, desc, address, dailyBudget, radius);
                campaigns.add(campaign);
            } catch (JSONException e) {
                Log.d("TAG", "JSONException in getCampaignsListFromJson e = " + e);
            }
        }
        return campaigns;
    }
}
