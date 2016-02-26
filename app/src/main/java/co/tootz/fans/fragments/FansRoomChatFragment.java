package co.tootz.fans.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import co.tootz.fans.R;
import co.tootz.fans.domain.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * TODO: Add a class header comment!
 */
public class FansRoomChatFragment extends Fragment {
    private Socket mSocket;
    private Context context;
    private EditText mInputMessage;
    private Button mSendButton;
    private TextView mTextView;
    private TextView mNumberUsersTextView;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_fragment, container, false);

//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));
        context = v.getContext();
        mInputMessage = (EditText) v.findViewById(R.id.chat_edit_text);
        mSendButton = (Button) v.findViewById(R.id.send_button);
        mTextView = (TextView) v.findViewById(R.id.chat_text_view);
        mNumberUsersTextView = (TextView) v.findViewById(R.id.num_user_text_view);

        setupSocket();
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

        return v;
    }

    public FansRoomChatFragment() {

    }

    private void setDisconnected(){
        if (activity != null) activity.finish();
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
                    Log.d("SOCKET", "DICONNECTED");
                    getActivity().runOnUiThread(new Runnable() {
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
                    Log.d("SOCKET", "CONNECTED");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("token", User.getTokeen(context));
                        mSocket.emit("authenticate", obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            mSocket.on("authenticated", new Emitter.Listener(){
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET", "AUTHENTICATED");
                    mSocket.emit("add user", User.getUsername(context));
                }
            });

            mSocket.on("login", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.d("SOCKET", "LOGIN");
                    final JSONObject obj = (JSONObject) args[0];
                    activity.runOnUiThread(new Runnable() {
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
                    Log.d("SOCKET", "USER JOINED");
                    final JSONObject obj = (JSONObject) args[0];
                    activity.runOnUiThread(new Runnable() {
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
                    Log.d("SOCKET", "USER LEFT");
                    final JSONObject obj = (JSONObject) args[0];
                    activity.runOnUiThread(new Runnable() {
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
                    Log.d("SOCKET", "NEW MESSAGE");
                    final JSONObject obj = (JSONObject) args[0];
                    activity.runOnUiThread(new Runnable() {
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
        Log.d("SOCKET", "ATTEMPT SEND");
        final String message = mInputMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mInputMessage.setText("");

        final JSONObject obj = new JSONObject();
        try {
            obj.put("username", "android user");
            obj.put("message", message);
            getActivity().runOnUiThread(new Runnable() {
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



    public static FansRoomChatFragment newInstance(String text) {

        FansRoomChatFragment f = new FansRoomChatFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            this.activity = (Activity) context;
        }
    }
}