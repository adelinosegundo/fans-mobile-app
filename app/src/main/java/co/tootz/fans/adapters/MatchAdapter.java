package co.tootz.fans.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.tootz.fans.R;
import co.tootz.fans.activities.MatchDetailActivity;
import co.tootz.fans.domain.Match;

public class MatchAdapter extends ArrayAdapter<Match> {
    private Context context;
    private List<Match> matchesList;
    public MatchAdapter(Context context, int id, List<Match> matchesList) {
        super(context, id);
        this.context = context;
        this.matchesList = matchesList;
        this.update();
    }

    public void update() {
        this.clear();
        this.addAll(matchesList);
        this.notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {
        final Match match = getItem(position);
        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.match_row, null);

            holder = new ViewHolder();

            holder.firstTeamName = (TextView) view.findViewById(R.id.matchRowFirstTeamName);
            holder.firstTeamScore = (TextView) view.findViewById(R.id.matchRowFirstTeamScore);
            holder.firstTeamFlag = (ImageView) view.findViewById(R.id.matchRowFirstTeamFlag);
            holder.secondTeamName = (TextView) view.findViewById(R.id.matchRowSecondTeamName);
            holder.secondTeamScore = (TextView) view.findViewById(R.id.matchRowSecondTeamScore);
            holder.secondTeamFlag = (ImageView) view.findViewById(R.id.matchRowSecondTeamFlag);

            view.setTag(holder);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String match_id = match.getId();
                    Intent intent = new Intent(context, MatchDetailActivity.class);

                    intent.putExtra("match_id", match_id);

                    context.startActivity(intent);
                }
            });
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (match != null) {
            holder.firstTeamName.setText(match.getFirstTeamName());
            holder.firstTeamScore.setText(match.getFirstTeamScore());
//            holder.firstTeamFlag.setImageBitmap(match.getFirstTeamFlagImageBitmap());
            holder.secondTeamName.setText(match.getSecondTeamName());
            holder.secondTeamScore.setText(match.getSecondTeamScore());
//            holder.firstTeamFlag.setImageBitmap(match.getFirstTeamFlagImageBitmap());
        }

        return view;
    }

    static class ViewHolder {
        TextView firstTeamName;
        TextView firstTeamScore;
        ImageView firstTeamFlag;
        TextView secondTeamName;
        TextView secondTeamScore;
        ImageView secondTeamFlag;
    }
}