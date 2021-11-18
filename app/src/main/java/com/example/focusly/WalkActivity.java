package com.example.focusly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class WalkActivity extends AppCompatActivity {

    Button startCountdownTimer;
    Button resetCountdownTimer;

    boolean isTimerRunning;

    CountDownTimer taskCountdownTimer;

    long startTimeInput = GlobalVariable.OffTimer;
    long timeLeft = startTimeInput * 60000;
    long endTime;

    TextView OffTaskTimer;

    private static final long IMAGE_UPDATE_DELAY_MILLIS = 8000;

    private static final int[] ALL_DRAWABLE_RES = new int[] {
            R.drawable.mwalk1,
            R.drawable.mwalk2,
            R.drawable.mwalk3,
            R.drawable.mwalk4,
            R.drawable.mwalk5,
            R.drawable.twalk1,
            R.drawable.twalk2,
            R.drawable.twalk3,
            R.drawable.twalk4,
            R.drawable.twalk5,
            R.drawable.bwalk1,
            R.drawable.bwalk2,
            R.drawable.bwalk3,
            R.drawable.bwalk4,
            R.drawable.bwalk5
    };

    private int currentDrawableResIndex;
    private ImageView imageView;
    private Runnable updateImageTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        startCountdownTimer = findViewById(R.id.button_start_pause);
        resetCountdownTimer = findViewById(R.id.button_reset);
        OffTaskTimer = (TextView) findViewById(R.id.walk_countdown);

        /*--TIMER DISPLAY--*/
        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        OffTaskTimer.setText(timeLeftDisplay);


        /*--MULTIPLE IMAGE DISPLAY--*/
        updateImageTask = new UpdateImageTask();

        imageView = (ImageView) findViewById(R.id.image_display);

        currentDrawableResIndex = 0;
        imageView.setImageResource(ALL_DRAWABLE_RES[0]);
        imageView.removeCallbacks(updateImageTask);
        imageView.postDelayed(updateImageTask, IMAGE_UPDATE_DELAY_MILLIS);



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


    //NEW EDIT TRYYYYY
    private class UpdateImageTask implements Runnable {
        @Override
        public void run() {
            currentDrawableResIndex++;

            if (currentDrawableResIndex < ALL_DRAWABLE_RES.length) {
                imageView.setImageResource(ALL_DRAWABLE_RES[currentDrawableResIndex]);
                imageView.postDelayed(this, IMAGE_UPDATE_DELAY_MILLIS);
            } else {
                imageView.setImageResource(R.drawable.mwalk1);
            }
        }
    }

    //NEW EDIT TRYYYYY
    @Override
    protected void onStop() {
        super.onStop();
        imageView.setImageResource(R.drawable.mwalk1);
        imageView.removeCallbacks(updateImageTask);
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
                Intent in = new Intent(WalkActivity.this, OffContinueActivity.class);
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