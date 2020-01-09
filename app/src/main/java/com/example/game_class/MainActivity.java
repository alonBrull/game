package com.example.game_class;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button main_BTN_play;
    private Switch main_SW_sensor;
    private RadioGroup main_RBG_group;
    private MediaPlayer mediaPlayerBackGround;
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViews();
        setOnclickListeners();
    }

    private void setOnclickListeners() {
        main_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGameActivity();
            }
        });
    }

    private void findViews() {
        main_SW_sensor = findViewById(R.id.main_SW_sensor);
        main_RBG_group = findViewById(R.id.main_RBG_group);
        main_BTN_play = findViewById(R.id.main_BTN_play);
    }

    private void gotoGameActivity() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);

        boolean checked = main_SW_sensor.isChecked();
        intent.putExtra("sensor", checked);

        int radioButtonId = main_RBG_group.getCheckedRadioButtonId();
        if (radioButtonId == R.id.main_RB_slow)
            intent.putExtra("slow", true);
        else
            intent.putExtra("slow", false);

        startActivity(intent);
    }

    private void startBackGroundMusic() {
        mediaPlayerBackGround = MediaPlayer.create(this, R.raw.main_track);
        mediaPlayerBackGround.start();
        mediaPlayerBackGround.seekTo(length);
        mediaPlayerBackGround.setLooping(true);
    }

    private void releseBackGroundMusic() {
        mediaPlayerBackGround.pause();
        length = mediaPlayerBackGround.getCurrentPosition();
        mediaPlayerBackGround.release();
        mediaPlayerBackGround = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        startBackGroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();

        releseBackGroundMusic();
    }
}