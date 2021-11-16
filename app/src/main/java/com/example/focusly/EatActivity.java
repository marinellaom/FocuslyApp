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

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Locale;

public class EatActivity extends AppCompatActivity {

    Button startCountdownTimer;
    Button resetCountdownTimer;

    boolean isTimerRunning;

    CountDownTimer taskCountdownTimer;

    long startTimeInput = GlobalVariable.OffTimer;
    long timeLeft = startTimeInput * 60000;
    long endTime;

    TextView OffTaskTimer;

    //Image Slider
    SliderView sliderView;
    int[] images = {R.drawable.teat1,
            R.drawable.teat2,
            R.drawable.teat3,
            R.drawable.teat4,
            R.drawable.teat5,
            R.drawable.meat1,
            R.drawable.meat2,
            R.drawable.meat3,
            R.drawable.meat4,
            R.drawable.meat,
            R.drawable.beat1,
            R.drawable.beat2,
            R.drawable.beat3,
            R.drawable.beat4,
            R.drawable.beat5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        startCountdownTimer = findViewById(R.id.button_start_pause);
        resetCountdownTimer = findViewById(R.id.button_reset);
        OffTaskTimer = (TextView) findViewById(R.id.eat_countdown);

        /*--TIMER DISPLAY--*/
        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        OffTaskTimer.setText(timeLeftDisplay);


        /*--IMAGE SLIDER--*/
        sliderView = findViewById(R.id.eat_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


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
                Intent in = new Intent(EatActivity.this, OffContinueActivity.class);
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
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wow);

        mediaPlayer.start();
        startActivity(i);

        if(isTimerRunning){
            timeLeft = startTimeInput;
            taskCountdownTimer.cancel();
            updateCountdownText();
        }
    }



}