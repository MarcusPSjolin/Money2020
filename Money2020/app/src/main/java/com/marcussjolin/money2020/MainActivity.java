package com.marcussjolin.money2020;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String MENTION = "mention";
    public static final String HASHTAG = "hashtag";
    public static final String NEW_ITEMS = "new_items";
    public static final String EVENT = "event";
    public static final String ITEMS = "items";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String DAILY_BUDGET = "daily_budget";
    public static final String FACEBOOK_PAGE_ID = "fb_page_id";
    public static final String TARGET_ADDRESS = "target_address";
    public static final String TARGET_RADIUS = "target_radius";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_overview);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCards();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            // no-op
        } else if (id == R.id.nav_transaction) {

        } else if (id == R.id.nav_employees) {

        } else if (id == R.id.nav_social) {

        } else if (id == R.id.nav_campaigns) {
            Intent intent = new Intent(this, CampaignsActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getCards() {
        RequestQueue queue = Volley.newRequestQueue(mActivity);
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse error = " + error);
                Toast.makeText(mActivity, "There was an error.", Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Money2020Application.SOCIAL_URL,
                new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mAdapter = new CardAdapter(getCardsFromJsonArray(response), mActivity);
                mRecyclerView.setAdapter(mAdapter);
            }
        }, errorListener);

        queue.add(request);
    }

    private List<Card> getCardsFromJsonArray(JSONArray array) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);

                String type = object.getString(TYPE);
                if (type.equals(NEW_ITEMS)) {
                    JSONArray newItems = object.getJSONArray(ITEMS);
                    cards.addAll(getCardsFromNewItems(newItems));
                } else {
                    String title = object.getString(TITLE);
                    String mention = object.getString(MENTION);
                    String hashtag = object.getString(HASHTAG);
                    Drawable image = getCardImage(title);

                    Card card = new Card(image, title, mention, hashtag, Card.Action.TWEET, type);
                    card.setBackground(getCardBackground(title));

                    cards.add(card);
                }
            } catch (JSONException e) {
                Log.d("TAG", "JSONException in getCardsFromJsonArray = " + e);
            }
        }
        return cards;
    }

    private Drawable getCardImage(String title) {
        Drawable res;
        switch(title) {
            case Money2020Application.NFL:
                res = ContextCompat.getDrawable(this, R.drawable.tom_brady);
                break;
            case Money2020Application.BREAST_CANCER:
                res = ContextCompat.getDrawable(this, R.drawable.breast_cancer);
                break;
            case Money2020Application.HALLOWEEN:
                res = ContextCompat.getDrawable(this, R.drawable.pumpkin);
                break;
            default:
                res = ContextCompat.getDrawable(this, R.drawable.money_2020);
        }
        return res;
    }

    private Drawable getCardBackground(String title) {
        Drawable res;
        switch (title) {
            case Money2020Application.NFL:
                res = ContextCompat.getDrawable(this, R.drawable.nfl);
                break;
            case Money2020Application.BREAST_CANCER:
                res = ContextCompat.getDrawable(this, R.drawable.breast_cancer);
                break;
            case Money2020Application.HALLOWEEN:
                res = ContextCompat.getDrawable(this, R.drawable.halloween_background);
                break;
            default:
                res = ContextCompat.getDrawable(this, R.drawable.raining_cash);
        }
        return res;
    }

    private List<Card> getCardsFromNewItems(JSONArray array) {
        List<Card> cards = new ArrayList<>();
        String name;
        double price;
        for (int i = 0; i < array.length(); i++) {
            try {
                DecimalFormat df = new DecimalFormat("#.00");
                name = array.getJSONObject(i).getString(NAME);
                price = array.getJSONObject(i).getDouble(PRICE);
                Card card = new Card(null, name, df.format(price), null, Card.Action.TWEET, NEW_ITEMS);
                cards.add(card);
            } catch (JSONException e) {
                Log.d("TAG", "JSONException in getCardsFromNewItems e = " + e);
            }
        }
        return cards;
    }
}
