package com.example.game_class;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class FragmentChart extends Fragment {
    private ScoreActivity scoreActivity;
    private View view;
    private Button [] places ;
    private final int NUM_OF_PLACES = 10;
    MySharedPreferences msp;
    Gson gson;

    public void setActivity(ScoreActivity scoreActivity) {
        Log.d("pttt", "setActivity");
        this.scoreActivity = scoreActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("pttt", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fram_chart, container, false);
        }
        msp = new MySharedPreferences(scoreActivity);
        gson = new Gson();
        places = new Button[NUM_OF_PLACES];
        findViews(view);
        setPlaces(view);
        setListeners();
        return view;
    }

    private void setPlaces(View view) {
        String sc = "";
        for(int i = 0; i < NUM_OF_PLACES; i++)
        {
            sc = msp.getString("p"+i, "NOTFOUND");
            if(sc.equals("NOTFOUND")){
                places[i].setVisibility(View.GONE);
            }
            Score score = gson.fromJson(sc, Score.class);
            places[i].setText(score.getName());
        }
    }

    private void findViews(View view) {
        places[0] = view.findViewById(R.id.framchart_BTN_p0);
        places[1] = view.findViewById(R.id.framchart_BTN_p1);
        places[2] = view.findViewById(R.id.framchart_BTN_p2);
        places[3] = view.findViewById(R.id.framchart_BTN_p3);
        places[4] = view.findViewById(R.id.framchart_BTN_p4);
        places[5] = view.findViewById(R.id.framchart_BTN_p5);
        places[6] = view.findViewById(R.id.framchart_BTN_p6);
        places[7] = view.findViewById(R.id.framchart_BTN_p7);
        places[8] = view.findViewById(R.id.framchart_BTN_p8);
        places[9] = view.findViewById(R.id.framchart_BTN_p9);
    }

    private void setListeners() {
        places[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "opennewfragment");

            }
        });

    }
}