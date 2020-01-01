package com.example.game_class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

public class ScoreActivity extends AppCompatActivity {
    private Button score_BTN_playAgain, score_BTN_menu, score_BTN_scores;

    private FragmentChart fram_chart;
    private FragmentAskName fram_askname;

    private FrameLayout score_FRM_chart, score_FRM_askname;

    private TextView score_TXT_score;

    private Score score;

    private Score[] highScores;

    private String[] highScoresStrings;

    private final int NUM_OF_SCORES = 10;

    MySharedPreferences msp;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);

        msp = new MySharedPreferences(this);
        gson = new Gson();

        findViews();
        setOnClickListeners();

        String sc = msp.getString("score", "NA");
        score = gson.fromJson(sc, Score.class);

        score_TXT_score.setText("SCORE\n" + "" + score.getScore());

        highScores = new Score[NUM_OF_SCORES];
        highScoresStrings = new String[NUM_OF_SCORES];

        fram_askname = new FragmentAskName();
        fram_askname.setCallBack(myCallBack);

        fram_chart = new FragmentChart();
        fram_chart.setActivity(this);

        initScores();

        manageScores();
    }

    private void setOnClickListeners() {
        score_BTN_scores.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.score_FRM_chart, fram_chart);
                transaction.commit();
            }
        }));

        score_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGameActivity();
            }
        });

        score_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
    }

    private void findViews() {
        score_TXT_score = findViewById(R.id.score_TXT_score);

        score_FRM_chart = findViewById(R.id.score_FRM_chart);
        score_FRM_askname = findViewById(R.id.score_FRM_askname);

        score_BTN_scores = findViewById(R.id.score_BTN_scores);
        score_BTN_playAgain = findViewById(R.id.score_BTN_playAgain);
        score_BTN_menu = findViewById(R.id.score_BTN_menu);
    }

    private void gotoGameActivity() {
        Intent intent = new Intent(ScoreActivity.this, GameActivity.class);
        startActivity(intent);

        this.finish();
    }

    private void gotoMainActivity() {
        this.finish();
    }

    private void initScores() {
        setScoresStrings();
        for (int i = 0; i < 10; i++) {
            if (!(highScoresStrings[i].equals("NOTFOUND"))) {
                highScores[i] = gson.fromJson(highScoresStrings[i], Score.class);
            } else {
                highScores[i] = null;
            }
        }
    }

    private void setScoresStrings() {
        for (int i = 0; i < NUM_OF_SCORES; i++)
            highScoresStrings[i] = msp.getString("p" + i, "NOTFOUND");
    }

    private void manageScores() {
        for (int i = 0; i < NUM_OF_SCORES; i++) {
            if (highScores[i] == null) {
                highScores[i] = score;
                highScoresStrings[i] = gson.toJson(score);
                String key = "p" + i; // key for shared preferences
                msp.putString(key, highScoresStrings[i]);
                return;
            }
            if (score.getScore() > highScores[i].getScore()) {
                requestName(this);
                demoteScores(i);
                highScores[i] = score;
                highScoresStrings[i] = gson.toJson(score);
                String key = "p" + i; // key for shared preferences
                msp.putString(key, highScoresStrings[i]);
                return;
            }
        }
    }

    private void requestName(Context context) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.score_FRM_askname, fram_askname);
        transaction.commit();
    }

    private void demoteScores(int i) {
        for (int j = NUM_OF_SCORES - 1; j > i; j--) {
            if (highScores[j - 1] != null) {
                highScores[j] = highScores[j - 1];
                highScoresStrings[j] = highScoresStrings[j - 1];
                String key = "p" + j; // key for shared preferences
                msp.putString(key, highScoresStrings[j]);
            }
        }
    }

    CallBack_ActivityList myCallBack = new CallBack_ActivityList() {
        @Override
        public void setName(String name) {
            Log.d("pttt", name);
            score.setName(name);
        }

        @Override
        public void hideFragmentAskName() {
            FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

            ft.hide(fram_askname);
            score_FRM_askname.setVisibility(View.GONE);
        }
    };

}