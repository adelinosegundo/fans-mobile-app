package co.tootz.fans.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.tootz.fans.R;

/**
 * TODO: Add a class header comment!
 */
public class FansRoomChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.battle_cry_fragment, container, false);

//        TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
//        tv.setText(getArguments().getString("msg"));

        return v;
    }

    public FansRoomChatFragment() {
    }

    public static FansRoomChatFragment newInstance(String text) {

        FansRoomChatFragment f = new FansRoomChatFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}