package co.tootz.fans.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.tootz.fans.R;
import co.tootz.fans.adapters.MatchAdapter;
import co.tootz.fans.application.FansApplication;
import co.tootz.fans.domain.Match;
import co.tootz.fans.domain.User;

public class MatchesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private ListView matchesList;
    private MatchAdapter matchAdapter;


    private void engineMatchesList(List<Match> matches) {
        matchesList = (ListView) findViewById(R.id.matches_list);
        matchAdapter = new MatchAdapter(this, R.layout.activity_matches, matches);
        matchesList.setAdapter(matchAdapter);
        matchAdapter.update();
    }

    private void getMatches(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.25.20:3000/matches";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", "Response is: " + response);
                        List<Match> matches = new ArrayList<Match>();
                        try {
                            JSONArray matchesJson = new JSONArray(response);
                            for (int i = 0; i < matchesJson.length(); i++){
                                JSONObject matchJSON = matchesJson.getJSONObject(i);
                                String id = matchJSON.getString("_id");
                                String team_one = matchJSON.getString("team_one");
                                String team_one_score = matchJSON.getString("team_one_score");
                                String team_one_avatar_url = matchJSON.getString("team_one_avatar_url");
                                String team_two = matchJSON.getString("team_two");
                                String team_two_score = matchJSON.getString("team_two_score");
                                String team_two_avatar_url = matchJSON.getString("team_two_avatar_url");
                                matches.add(new Match(id,  team_one,  team_one_score,  team_one_avatar_url,  "",  team_two,  team_two_score,  team_two_avatar_url,  ""));
                            }
                            engineMatchesList(matches);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "That didn't work!");
                    }
                }
        );
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        getMatches();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationViewHeader = navigationView.getHeaderView(0);
        TextView mNavigationUsernameTextView = (TextView) navigationViewHeader.findViewById(R.id.navigationUsernameTextView);

        this.sharedPreferences = getSharedPreferences(FansApplication.SHARED_PREFERENCES, MODE_PRIVATE);

        String name = sharedPreferences.getString("username", "Usuário");

        mNavigationUsernameTextView.setText(name);


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
        getMenuInflater().inflate(R.menu.matches, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
