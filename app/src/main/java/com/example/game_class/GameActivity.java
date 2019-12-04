package com.example.game_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    private int dropId[] = {
            R.id.drop_0_0, R.id.drop_0_1, R.id.drop_0_2, R.id.drop_1_0, R.id.drop_1_1, R.id.drop_1_2, R.id.drop_2_0, R.id.drop_2_1,
            R.id.drop_2_2, R.id.drop_3_0, R.id.drop_3_1, R.id.drop_3_2, R.id.drop_4_0, R.id.drop_4_1, R.id.drop_4_2, R.id.drop_5_0, R.id.drop_5_1,
            R.id.drop_5_2, R.id.drop_6_0, R.id.drop_6_1, R.id.drop_6_2};
    private ImageView [][] dropImgMatrix;
    private int avatarId[] = {R.id.avatar_0, R.id.avatar_1, R.id.avatar_2};
    private ImageView [] avatarImgArr;
    private int lifeId[] = {R.id.life_0, R.id.life_1, R.id.life_2};
    private ImageView [] lifeImgArr;
    private int life;
    private int rows, cols;
    private MyLogic logic;
    private Button game_BTN_debug;
    private Button arrow_BTN_right, arrow_BTN_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rows = getResources().getInteger(R.integer.rows);
        cols = getResources().getInteger(R.integer.columns);

        logic = new MyLogic(rows, cols);

        dropImgMatrix = new ImageView[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                dropImgMatrix[i][j] = findViewById(dropId[i*cols+j]);
                dropImgMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }

        avatarImgArr = new ImageView[cols];

        for(int i = 0; i < cols; i++){
            avatarImgArr[i] = findViewById(avatarId[i]);
            avatarImgArr[i].setVisibility(View.INVISIBLE);
        }
        avatarImgArr[cols/2].setVisibility(View.VISIBLE);

        life = getResources().getInteger(R.integer.life);

        lifeImgArr = new ImageView[life];

        for(int i = 0; i < life; i++){
            lifeImgArr[i] = findViewById(lifeId[i]);
        }

        game_BTN_debug = findViewById(R.id.game_BTN_debug);

        arrow_BTN_right = findViewById(R.id.arrow_BTN_right);

        arrow_BTN_left = findViewById(R.id.arrow_BTN_left);

        game_BTN_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
                manageHits();
            }
        });


        arrow_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRight();
            }
        });

        arrow_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });

        loopFunc();
    }

    private void loopFunc() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loopFunc();
                nextStep();
                manageHits();
            }
        }, 500);
    }

    private void nextStep() {
        logic.step();
        logic.nextLine();
        refreshMatrix();
    }

    private void refreshMatrix() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (logic.getMatrix()[i][j]){
                    dropImgMatrix[i][j].setVisibility(View.VISIBLE);
                }
                else{
                    dropImgMatrix[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void moveRight(){
        logic.right();
        for(int i = 0; i < cols; i++){
            if(logic.getArr()[i]){
                avatarImgArr[i].setVisibility(View.VISIBLE);
            }
            else{
                avatarImgArr[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    private void moveLeft(){
        logic.left();
        for(int i = 0; i < cols; i++){
            if(logic.getArr()[i]){
                avatarImgArr[i].setVisibility(View.VISIBLE);
            }
            else{
                avatarImgArr[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void manageHits() {
        logic.checkHit();
        if (logic.getLife() > 0)
        for (int i = life - 1; i > logic.getLife() - 1; i--){
            lifeImgArr[i].setVisibility(View.INVISIBLE);
        }
        else {
            gotoMainActivity();
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
