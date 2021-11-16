package com.example.focusly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MusicActivity extends AppCompatActivity {

    Button startCountdownTimer;
    Button resetCountdownTimer;

    boolean isTimerRunning;

    CountDownTimer taskCountdownTimer;

    long startTimeInput = GlobalVariable.OffTimer;
    long timeLeft = startTimeInput * 60000;
    long endTime;

    TextView OffTaskTimer;

    // SOUNDS
    private ImageView natureSounds;
    private ImageView pianoSounds;
    private ImageView localMusicFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        startCountdownTimer = findViewById(R.id.button_start_pause);
        resetCountdownTimer = findViewById(R.id.button_reset);

        OffTaskTimer = (TextView) findViewById(R.id.music_countdown);

        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        OffTaskTimer.setText(timeLeftDisplay);


        //EDIT: MUSIC BUTTONS
        natureSounds = (ImageView) findViewById(R.id.rain_button);
        pianoSounds = (ImageView) findViewById(R.id.piano_button);
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(MusicActivity.this, R.raw.nature);
        final MediaPlayer mediaPlayer2 = MediaPlayer.create(MusicActivity.this, R.raw.instrument);

        natureSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer1.isPlaying()){
                    mediaPlayer1.pause();

                } else {
                    mediaPlayer1.start();
                }
            }
        });

        pianoSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer2.isPlaying()){
                    mediaPlayer2.pause();

                } else {
                    mediaPlayer2.start();

                }
            }
        });


        localMusicFile = (ImageView) findViewById(R.id.localmusic_button);
        localMusicFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("*/*");
                startActivityForResult(in, 1);
            }
        });


        // START BUTTON
        startCountdownTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });


        // RESET BUTTON = RESETS TIMER
        resetCountdownTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }



    /*---- COUNTDOWN | START PAUSE BUTTON ----*/
    public void startTimer(){

        endTime = System.currentTimeMillis() + timeLeft;


        taskCountdownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long timeUntilFinished) {
                timeLeft = timeUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                updateTimeInterface();

            }
        }.start();

        isTimerRunning = true;
        updateTimeInterface();
    }


    /*---- PAUSE COUNTDOWN TIMER ----*/
    public void pauseTimer(){
        taskCountdownTimer.cancel();
        isTimerRunning = false;
        updateTimeInterface();
    }

    /*---- RESET COUNTDOWN TIMER ----*/
    public void resetTimer(){

        taskCountdownTimer.cancel();
        timeLeft = GlobalVariable.OnTimer * 60000;
        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        OffTaskTimer.setText(timeLeftDisplay);

        updateTimeInterface();

    }

    /*---- UPDATE COUNTDOWN TEXT DISPLAY ----*/
    public void updateCountdownText(){
//        taskCountdownTimer.cancel();
//        timeLeft = GlobalVariable.OnTimer * 60000;
        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        OffTaskTimer.setText(timeLeftDisplay);
    }

    /*---- UPDATE COUNTDOWN BUTTONS | VISIBILITY & BUTTON TEXT ----*/
    public void updateTimeInterface(){
        if(isTimerRunning){
            resetCountdownTimer.setVisibility(View.VISIBLE);
            startCountdownTimer.setText("PAUSE");
        } else {
            startCountdownTimer.setText("START");


            if (timeLeft < 1000){

                /*----DEFAULT TIME----*/
                timeLeft = startTimeInput;
                updateCountdownText();

                /*---- PROCEEDS TO NEXT PAGE IF TIMER IS DONE----*/
                Intent in = new Intent(MusicActivity.this, OffContinueActivity.class);
                startActivity(in);
            }
        }
    }


    /*---- KEEPS THE TIMER RUNNING ON BACKGROUND OR WHEN ORIENTATION CHANGES----*/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", timeLeft);
        outState.putBoolean("timerRunning", isTimerRunning);
        outState.putLong("timeEnd", endTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        timeLeft = savedInstanceState.getLong("millisLeft");
        isTimerRunning = savedInstanceState.getBoolean("timerRunning");

        updateCountdownText();
        updateTimeInterface();

        if(isTimerRunning){
            endTime = savedInstanceState.getLong("timeEnd");
            timeLeft = endTime - System.currentTimeMillis();
            startTimer();
        }
    }



    /*---- DONE BUTTON IF THE USER FINISHED TASK EARLY BEFORE TIME RUNS OUT----*/
    public void doneTaskEarly(View view){

        Intent i = new Intent(this, OffContinueActivity.class);
        startActivity(i);

        if(isTimerRunning){
            taskCountdownTimer.cancel();
            timeLeft = GlobalVariable.OnTimer * 60000;
            int minutes = (int) ((timeLeft/1000)%3600)/60;
            int seconds = (int) (timeLeft/1000)%60;

            String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            OffTaskTimer.setText(timeLeftDisplay);
        }
    }



}