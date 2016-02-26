package co.tootz.fans.activities;

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
import java.util.List;

import co.tootz.fans.R;
import co.tootz.fans.adapters.FansRoomAdapter;
import co.tootz.fans.application.FansApplication;
import co.tootz.fans.domain.FansRoom;
import co.tootz.fans.domain.Match;

public class MatchDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private ListView fans_roomsList;
    private FansRoomAdapter fansRoomAdapter;
    private String matchId;


    private void engineFansRoomsList(List<FansRoom> fansRooms) {
        fans_roomsList = (ListView) findViewById(R.id.fans_rooms_list);
        fansRoomAdapter = new FansRoomAdapter(this, R.layout.activity_match_detail, fansRooms);
        fans_roomsList.setAdapter(fansRoomAdapter);
        fansRoomAdapter.update();
    }

    private void getFansRooms(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.25.20:3000/matches/"+matchId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", "Response is: " + response);
                        List<FansRoom> fansRooms = new ArrayList<FansRoom>();
                        try {
                            JSONArray fansRoomsJSON = new JSONArray(response);
                            for (int i = 0; i < fansRoomsJSON.length(); i++){
                                JSONObject fansRoomJSON = fansRoomsJSON.getJSONObject(i);
                                String id = fansRoomJSON.getString("_id");
                                String name = fansRoomJSON.getString("name");
                                int numberOfFans = fansRoomJSON.getInt("number_of_fans");

                                fansRooms.add(new FansRoom(id, matchId, name, numberOfFans));
                            }
                            engineFansRoomsList(fansRooms);
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
        setContentView(R.layout.activity_match_detail);

        this.matchId = getIntent().getExtras().getString("match_id");

        getFansRooms();



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        View navigationViewHeader = navigationView.getHeaderView(0);
//        TextView mNavigationUsernameTextView = (TextView) navigationViewHeader.findViewById(R.id.navigationUsernameTextView);
//
//        this.sharedPreferences = getSharedPreferences(FansApplication.SHARED_PREFERENCES, MODE_PRIVATE);
//
//        String name = sharedPreferences.getString("username", "Usuário");
//
//        mNavigationUsernameTextView.setText(name);



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
        getMenuInflater().inflate(R.menu.match_detail, menu);
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
