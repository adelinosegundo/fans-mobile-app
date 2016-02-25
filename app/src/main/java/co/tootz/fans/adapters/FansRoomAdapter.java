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

    public FansRoomAdapter(Context context, int id) {
        super(context, id);
        this.context = context;
        this.update();
    }

    public void update() {
        this.clear();

        List<FansRoom> fansRoomsList = new ArrayList<>();
        fansRoomsList.add(new FansRoom("1", "1", "Fans Room 1", 12));
        fansRoomsList.add(new FansRoom("5", "1", "Fans Room 2", 134));
        fansRoomsList.add(new FansRoom("3", "1", "Fans Room 3", 54));
        fansRoomsList.add(new FansRoom("7", "1", "Fans Room 4", 95));
        fansRoomsList.add(new FansRoom("2", "2", "Fans Room 1", 10));
        fansRoomsList.add(new FansRoom("4", "2", "Fans Room 2", 33));
        fansRoomsList.add(new FansRoom("6", "2", "Fans Room 3", 4));
        fansRoomsList.add(new FansRoom("8", "2", "Fans Room 4", 2));

        this.addAll(fansRoomsList);
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

