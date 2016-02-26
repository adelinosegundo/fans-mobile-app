package co.tootz.fans.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

import co.tootz.fans.R;
import co.tootz.fans.domain.User;
import co.tootz.fans.fragments.FansRoomBattleCryFragment;
import co.tootz.fans.fragments.FansRoomChatFragment;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class FansRoomActivity extends AppCompatActivity {

    private Socket mSocket;
    private Context context;
    private EditText mInputMessage;
    private Button mSendButton;
    private TextView mTextView;
    private TextView mNumberUsersTextView;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        context = this;

        mInputMessage = (EditText) findViewById(R.id.chat_edit_text);
        mSendButton = (Button) findViewById(R.id.send_button);
        mTextView = (TextView) findViewById(R.id.chat_text_view);
        mNumberUsersTextView = (TextView) findViewById(R.id.num_user_text_view);

        setupSocket();
    }

    private void setDisconnected(){
        this.finish();
    }

    private void setNumberUsers(JSONObject obj){
        try {
            mNumberUsersTextView.setText(obj.getString("numUsers") + " users connected.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void userMessage(JSONObject obj){
        try {
            String username = obj.getString("username");
            String message = obj.getString("message");
            mTextView.append("\n" + username + ": " + message);
        }catch (Exception e){
            Log.e("userMessage", e.getMessage());
        }
    }

    private void infoMessage(JSONObject obj, boolean joining){
        try {
            String username = obj.getString("username");
            String numUsers = obj.getString("numUsers");
            if (joining)
                mTextView.append("\n" +username + " just joined");
            else
                mTextView.append("\n" +username + " just left");
            setNumberUsers(obj);
        }catch (Exception e){
            Log.e("infoMessage", e.getMessage());
        }
    }

    private void setupSocket(){
        try {
            mSocket = IO.socket("http://fans-chat-server.herokuapp.com/");

            mSocket.connect();
            mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDisconnected();
                        }
                    });
                }
            });

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args){
                    mSocket.emit("authenticate", User.getTokeen(context));
                }
            });

            mSocket.on("authenticated", new Emitter.Listener(){
                @Override
                public void call(Object... args) {
                    mSendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            attemptSend();
                        }
                    });
                    mSocket.emit("add user", "android user");
                }
            });

            mSocket.on("login", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONObject obj = (JSONObject) args[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setNumberUsers(obj);
                        }
                    });
                }
            });

            mSocket.on("user joined", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONObject obj = (JSONObject) args[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            infoMessage(obj, true);
                        }
                    });
                }
            });

            mSocket.on("user left", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONObject obj = (JSONObject) args[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            infoMessage(obj, false);
                        }
                    });
                }
            });

            mSocket.on("new message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    final JSONObject obj = (JSONObject) args[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userMessage(obj);
                        }
                    });
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void attemptSend() {
        final String message = mInputMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mInputMessage.setText("");

        final JSONObject obj = new JSONObject();
        try {
            obj.put("username", "android user");
            obj.put("message", message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    userMessage(obj);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("new message", message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fans_room, menu);
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0 : return FansRoomChatFragment.newInstance("Chat");
                case 1 : return FansRoomBattleCryFragment.newInstance("Gritos de Guerra");
                default : return FansRoomChatFragment.newInstance("Chat");

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CHAT";
                case 1:
                    return "GRITOS DE GUERRA";
            }
            return null;
        }
    }
}
