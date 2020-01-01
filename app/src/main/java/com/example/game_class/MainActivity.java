package com.example.game_class;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button playBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        playBTN = findViewById(R.id.main_BTN_play);

        playBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGameActivity();
            }
        });
    }

    private void gotoGameActivity() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
