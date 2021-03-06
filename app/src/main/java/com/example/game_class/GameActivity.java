package com.example.game_class;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;


public class GameActivity extends AppCompatActivity {
    private int dropId[] = {
            R.id.drop_0_0, R.id.drop_0_1, R.id.drop_0_2, R.id.drop_0_3, R.id.drop_0_4, R.id.drop_1_0, R.id.drop_1_1, R.id.drop_1_2, R.id.drop_1_3,
            R.id.drop_1_4, R.id.drop_2_0, R.id.drop_2_1, R.id.drop_2_2, R.id.drop_2_3, R.id.drop_2_4, R.id.drop_3_0, R.id.drop_3_1, R.id.drop_3_2,
            R.id.drop_3_3, R.id.drop_3_4, R.id.drop_4_0, R.id.drop_4_1, R.id.drop_4_2, R.id.drop_4_3, R.id.drop_4_4, R.id.drop_5_0, R.id.drop_5_1,
            R.id.drop_5_2, R.id.drop_5_3, R.id.drop_5_4, R.id.drop_6_0, R.id.drop_6_1, R.id.drop_6_2, R.id.drop_6_3, R.id.drop_6_4};
    private ImageView[][] dropImgMatrix;

    private int coinId[] = {
            R.id.coin_0_0, R.id.coin_0_1, R.id.coin_0_2, R.id.coin_0_3, R.id.coin_0_4, R.id.coin_1_0, R.id.coin_1_1, R.id.coin_1_2, R.id.coin_1_3,
            R.id.coin_1_4, R.id.coin_2_0, R.id.coin_2_1, R.id.coin_2_2, R.id.coin_2_3, R.id.coin_2_4, R.id.coin_3_0, R.id.coin_3_1, R.id.coin_3_2,
            R.id.coin_3_3, R.id.coin_3_4, R.id.coin_4_0, R.id.coin_4_1, R.id.coin_4_2, R.id.coin_4_3, R.id.coin_4_4, R.id.coin_5_0, R.id.coin_5_1,
            R.id.coin_5_2, R.id.coin_5_3, R.id.coin_5_4, R.id.coin_6_0, R.id.coin_6_1, R.id.coin_6_2, R.id.coin_6_3, R.id.coin_6_4};
    private ImageView[][] coinImgMatrix;

    private int avatarId[] = {R.id.game_IMG_avatar_0, R.id.game_IMG_avatar_1, R.id.game_IMG_avatar_2, R.id.game_IMG_avatar_3, R.id.game_IMG_avatar_4};
    private ImageView[] avatarImgArr;

    private int lifeId[] = {R.id.game_IMG_life_0, R.id.game_IMG_life_1, R.id.game_IMG_life_2};
    private ImageView[] lifeImgArr;
    private int life;

    private int drophit1Id[] = {R.id.drophit1_0, R.id.drophit1_1, R.id.drophit1_2, R.id.drophit1_3, R.id.drophit1_4};
    private ImageView[] drophit1ImgArr;
    private int drophit2Id[] = {R.id.drophit2_0, R.id.drophit2_1, R.id.drophit2_2, R.id.drophit2_3, R.id.drophit2_4};
    private ImageView[] drophit2ImgArr;

    private int hit1Id[] = {R.id.hit1_0, R.id.hit1_1, R.id.hit1_2, R.id.hit1_3, R.id.hit1_4};
    private ImageView[] hit1ImgArr;
    private int hit2Id[] = {R.id.hit2_0, R.id.hit2_1, R.id.hit2_2, R.id.hit2_3, R.id.hit2_4};
    private ImageView[] hit2ImgArr;

    private TextView game_TXT_score;
    private int scoreCount = 0;

    private int rows, cols;
    private Button arrow_BTN_right, arrow_BTN_left;
    private int speed, maxSpeed, count, nextCount;

    private int bonusValue;
    private int bonusCount = 0;

    private boolean sensor;
    private boolean slow;

    private boolean pauseGame;

    private MyLogic logic;
    private boolean bArr[];


    private MySensor mySensor;

    private MediaPlayer mediaPlayerBackGround;
    private MediaPlayer mediaPlayerEffect;
    private int length = 0;

    MySharedPreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        msp = new MySharedPreferences(this);

        initValuesFromResource();
        initViews();
        findViews();
        setViewsInitialVisibility();

        logic = new MyLogic(rows, cols);

        slow = getIntent().getExtras().getBoolean("slow");
        setSpeed(slow);

        sensor = getIntent().getExtras().getBoolean("sensor");
        setMode(sensor);
    }

    private void initValuesFromResource() {
        rows = getResources().getInteger(R.integer.rows);
        cols = getResources().getInteger(R.integer.columns);
        life = getResources().getInteger(R.integer.life);
        bonusValue = getResources().getInteger(R.integer.bonusValue);
        count = getResources().getInteger(R.integer.countToSpeedup);
        nextCount = getResources().getInteger(R.integer.countToNextSpeedup);
    }

    private void findViews() {
        // drop images
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dropImgMatrix[i][j] = findViewById(dropId[i * cols + j]);
            }
        }
        // bonus images
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                coinImgMatrix[i][j] = findViewById(coinId[i * cols + j]);
            }
        }
        // avatar images
        for (int i = 0; i < cols; i++) {
            avatarImgArr[i] = findViewById(avatarId[i]);
        }
        // life images
        for (int i = 0; i < life; i++) {
            lifeImgArr[i] = findViewById(lifeId[i]);
        }
        // bottom hit images
        for (int i = 0; i < cols; i++) {
            drophit1ImgArr[i] = findViewById(drophit1Id[i]);
            drophit2ImgArr[i] = findViewById(drophit2Id[i]);
        }
        // avatar hit images
        for (int i = 0; i < cols; i++) {
            hit1ImgArr[i] = findViewById(hit1Id[i]);
            hit2ImgArr[i] = findViewById(hit2Id[i]);
        }
        // score text
        game_TXT_score = findViewById(R.id.game_TXT_score);
        // arrow buttons
        arrow_BTN_right = findViewById(R.id.game_BTN_rightarrow);
        arrow_BTN_left = findViewById(R.id.game_BTN_leftarrow);
    }

    private void initViews() {
        // drop images
        dropImgMatrix = new ImageView[rows][cols];
        // bonus images
        coinImgMatrix = new ImageView[rows][cols];
        // avatar images
        avatarImgArr = new ImageView[cols];
        // life images
        lifeImgArr = new ImageView[life];
        // bottom hit images
        drophit1ImgArr = new ImageView[cols];
        drophit2ImgArr = new ImageView[cols];
        // avatar hit images
        hit1ImgArr = new ImageView[cols];
        hit2ImgArr = new ImageView[cols];
    }

    private void setViewsInitialVisibility() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dropImgMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                coinImgMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < cols; i++) {
            avatarImgArr[i].setVisibility(View.INVISIBLE);
        }
        // set avatar position at middle
        avatarImgArr[cols / 2].setVisibility(View.VISIBLE);

        for (int i = 0; i < cols; i++) {
            drophit1ImgArr[i].setVisibility(View.INVISIBLE);
            drophit2ImgArr[i].setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < cols; i++) {
            hit1ImgArr[i].setVisibility(View.INVISIBLE);
            hit2ImgArr[i].setVisibility(View.INVISIBLE);
        }
    }

    private void setSpeed(Boolean isSlow) {
        if (isSlow) {
            speed = getResources().getInteger(R.integer.slowStartSpeed);
            maxSpeed = getResources().getInteger(R.integer.slowMaxSpeed);
        } else {
            speed = getResources().getInteger(R.integer.startSpeed);
            maxSpeed = getResources().getInteger(R.integer.maxSpeed);
        }
    }

    private void setMode(Boolean isSensor) {
        if (isSensor)
            sensorMode();
        else {
            setArrowsOnClickListeners();
        }
    }

    private void setArrowsOnClickListeners() {
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
    }

    private void sensorMode() {
        arrow_BTN_right.setVisibility(View.INVISIBLE);
        arrow_BTN_left.setVisibility(View.INVISIBLE);

        bArr = new boolean[cols];
        mySensor = new MySensor(this);
        mySensor.setCallBackSensor(callBack_sensor);
    }

    final CallBack_Sensor callBack_sensor = new CallBack_Sensor() {
        @Override
        public void center() {
            bArr[cols / 2] = true;
            logic.setArr(bArr);
            bArr[cols / 2] = false;
        }

        @Override
        public void centerRight() {
            bArr[cols / 2 + 1] = true;
            logic.setArr(bArr);
            bArr[cols / 2 + 1] = false;
        }

        @Override
        public void centerLeft() {
            bArr[cols / 2 - 1] = true;
            logic.setArr(bArr);
            bArr[cols / 2 - 1] = false;
        }

        @Override
        public void right() {
            bArr[cols - 1] = true;
            logic.setArr(bArr);
            bArr[cols - 1] = false;
        }

        @Override
        public void left() {
            bArr[0] = true;
            logic.setArr(bArr);
            bArr[0] = false;
        }

        @Override
        public void move() {
            for (int i = 0; i < cols; i++) {
                if (logic.getArr()[i]) {
                    avatarImgArr[i].setVisibility(View.VISIBLE);
                } else {
                    avatarImgArr[i].setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private void loopScore() {
        if (!pauseGame) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showScoreVertical();
                    loopScore();
                }
            }, 10);
        }
    }

    private void loopFunc() {
        if (!pauseGame) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextStep();
                    manageExplosionEffect();
                    manageHits();
                    manageBonus();
                    manageSpeed();
                    loopFunc();
                }
            }, speed);
        }
    }

    // speed acceleration
    private void manageSpeed() {
        count--;
        if (count == 0 && speed > maxSpeed) {
            nextCount *= 5; // next speedup will take after a longer count
            count = nextCount;
            speed *= 0.9;
        }
    }

    private void showScoreVertical() {
        scoreCount++;
        int tempScore = scoreCount;
        int digitsize = 1;
        String s = "";
        while (tempScore != 0) {
            digitsize *= 10;
            tempScore /= 10;
        }
        digitsize /= 10;
        while (digitsize != 0) {
            s = s + (scoreCount / (digitsize)) % 10 + "\n";
            digitsize /= 10;
        }
        game_TXT_score.setText(s);
    }

    // logical new location
    private void nextStep() {
        logic.step();
        logic.nextLine();
        showNextStep();
    }

    // show each drop in new logical location
    private void showNextStep() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (logic.getMatrix()[i][j] == 1) {
                    dropImgMatrix[i][j].setVisibility(View.VISIBLE);
                } else if (logic.getMatrix()[i][j] == 2) {
                    coinImgMatrix[i][j].setVisibility(View.VISIBLE);
                } else {
                    dropImgMatrix[i][j].setVisibility(View.INVISIBLE);
                    coinImgMatrix[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void moveRight() {
        logic.right();
        for (int i = 0; i < cols; i++) {
            if (logic.getArr()[i]) {
                avatarImgArr[i].setVisibility(View.VISIBLE);
            } else {
                avatarImgArr[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void moveLeft() {
        logic.left();
        for (int i = 0; i < cols; i++) {
            if (logic.getArr()[i]) {
                avatarImgArr[i].setVisibility(View.VISIBLE);
            } else {
                avatarImgArr[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    // check if collected bonus, if collected --> bonusCount++ , scoreCount += value
    private void manageBonus() {
        int index = logic.checkBonus();
        if (index != -1) {
            bonusCollectingEffect(index);
            bonusCount++;
            scoreCount += bonusValue;
        }
    }

    private void bonusCollectingEffect(int index) {
        // TODO: 1/9/2020 animation
        mediaPlayerEffect = MediaPlayer.create(this, R.raw.game_collectcoin);
        mediaPlayerEffect.start();
    }

    // check if hit, if hit --> loose life, if life == 0 --> score activity
    private void manageHits() {
        int index = logic.checkHit();
        if (index != -1) {
            manageHitExplosionEffect(index);
            hitVibrate();
        }
        if (logic.getLife() > 0)
            for (int i = life - 1; i > logic.getLife() - 1; i--) {
                lifeImgArr[i].setVisibility(View.INVISIBLE);
            }
        else {
            gotoScoreActivity();
        }
    }

    private void hitVibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(200);
        }
    }

    // animation if drop hits bottom
    private void manageExplosionEffect() {
        for (int i = 0; i < cols; i++) {
            if (logic.getMatrix()[rows - 1][i] == 1) {
                final ImageView img1 = findViewById(drophit1Id[i]);
                img1.setVisibility(View.VISIBLE);
                final ImageView img2 = findViewById(drophit2Id[i]);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img1.setVisibility(View.INVISIBLE);
                        img2.setVisibility(View.VISIBLE);
                    }
                }, 300);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img2.setVisibility(View.INVISIBLE);
                    }
                }, 800);
            }
        }
    }

    // animation if drop hits avatar
    private void manageHitExplosionEffect(int index) {
        mediaPlayerEffect = MediaPlayer.create(this, R.raw.game_explosionhit);
        mediaPlayerEffect.start();
        mediaPlayerEffect.seekTo(150);

        final ImageView img3 = findViewById(hit1Id[index]);
        img3.setVisibility(View.VISIBLE);
        final ImageView img4 = findViewById(hit2Id[index]);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img3.setVisibility(View.INVISIBLE);
                img4.setVisibility(View.VISIBLE);
            }
        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img4.setVisibility(View.INVISIBLE);
            }
        }, 600);
    }

    private void gotoScoreActivity() {
        pauseGame = true;

        Score score = new Score(scoreCount, 0);
        Gson gson = new Gson();
        String jsn = gson.toJson(score);
        msp.putString("score", jsn);

        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        intent.putExtra("sensor", sensor);
        intent.putExtra("slow", slow);

        startActivity(intent);

        this.finish();
    }

    private void startBackGroundMusic() {
        mediaPlayerBackGround = MediaPlayer.create(this, R.raw.game_track);
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

        pauseGame = false;
        loopFunc();
        loopScore();

        startBackGroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();

        pauseGame = true;

        releseBackGroundMusic();
    }
}