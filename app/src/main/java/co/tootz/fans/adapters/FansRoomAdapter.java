package co.tootz.fans.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.tootz.fans.R;
import co.tootz.fans.domain.FansRoom;

public class FansRoomAdapter extends ArrayAdapter<FansRoom> {
    private Context context;
    private List<FansRoom> fansRooms;

    public FansRoomAdapter(Context context, int id, List<FansRoom> fansRooms) {
        super(context, id);
        this.context = context;
        this.fansRooms = fansRooms;
        this.update();
    }

    public void update() {
        this.clear();


        this.addAll(fansRooms);
        this.notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {
        final FansRoom fansRoom = getItem(position);
        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fans_room_row, null);

            holder = new ViewHolder();

            holder.fansRoomName = (TextView) view.findViewById(R.id.fansRoomName);
            holder.fansRoomNumberOfFans = (TextView) view.findViewById(R.id.fansRoomNumberOfFans);

            view.setTag(holder);


//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String fansRoom_id = fansRoom.getId();
//                    Intent intent = new Intent(context, FansRoomDetailActivity.class);
//                    intent.putExtra("fansRoom_id", fansRoom_id);
//
//                    context.startActivity(intent);
//                }
//            });
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (fansRoom != null) {
            holder.fansRoomName.setText(fansRoom.getName());
            holder.fansRoomNumberOfFans.setText(String.valueOf(fansRoom.getNumberOfFans()));
        }

        return view;
    }

    static class ViewHolder {
        TextView fansRoomName;
        TextView fansRoomNumberOfFans;
    }
}

