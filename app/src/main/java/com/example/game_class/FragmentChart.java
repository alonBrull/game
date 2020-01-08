package com.example.game_class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentChart extends Fragment {
    private CallBack_ActivityList callBack_activityList;
    private CallBack_Location callBack_location;
    private View view;
    private RecyclerView list_LST_scores;
    private Adapter_ScoreCardView adapter_scoreCardView;

    public void setCallBackList(CallBack_ActivityList _callBack_activityList) {
        this.callBack_activityList = _callBack_activityList;
    }
    public void setCallBackLocation(CallBack_Location callBack_Location) {
        this.callBack_location = callBack_Location;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fram_chart, container, false);
        }

        list_LST_scores = view.findViewById(R.id.list_LST_scores);

        initScoreList();

        return view;
    }

    private void initScoreList() {
        ArrayList<Score> scores = callBack_activityList.getScores();
        adapter_scoreCardView = new Adapter_ScoreCardView(getActivity(), scores);
        list_LST_scores.setHasFixedSize(true);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        list_LST_scores.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_LST_scores.setAdapter(adapter_scoreCardView);

        adapter_scoreCardView.SetOnItemClickListener(new Adapter_ScoreCardView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Score score) {
                callBack_location.changeCameraPosition(score);
            }
        });
    }
}
