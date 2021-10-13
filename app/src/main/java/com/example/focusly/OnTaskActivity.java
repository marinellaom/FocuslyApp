package com.example.focusly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OnTaskActivity extends AppCompatActivity {

    private TextView taskTimerDisplay;
    private Button startCountdownTimer;
    private Button resetCountdownTimer;

    private boolean isTimerRunning;

    private CountDownTimer taskCountdownTimer;

    // Temporary default time na naka-set lol
    private static long startTimeInput = 900000;


    private long timeLeft = startTimeInput;
    private long endTime;


    //EDIT: TRY SET INPUT TIME AS TIMER
    EditText editText;
    Button button;





//    TRTYYYYY

    private Button wow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_task);

        SetTaskTimerActivity set = new SetTaskTimerActivity();

        Intent i = getIntent();/*-- GET & DISPLAY TASK NAME OVERVIEW--*/
        String taskDisplay = i.getStringExtra("TASKDISPLAY");
        ((TextView) findViewById(R.id.task_display)).setText("TASK: " + taskDisplay);

        taskTimerDisplay = findViewById(R.id.text_view_countdown);
        startCountdownTimer = findViewById(R.id.button_start_pause);
        resetCountdownTimer = findViewById(R.id.button_reset);

        //EDIT: TRY SET INPUT TIME AS TIMER
        editText = (EditText) findViewById(R.id.minute);
        button = (Button) findViewById(R.id.button1);


        wow = findViewById(R.id.wow); /*-- TRY LANG SOUND --*/


        //EDIT: TRY SET INPUT TIME AS TIMER *UMIYAK SO MUCH*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (!text.equalsIgnoreCase("")) {
                    long minutes = Long.valueOf(text);
                    new CountDownTimer(minutes * 60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String text = String.format(Locale.getDefault(), " %02d min: %02d sec",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                            taskTimerDisplay.setText(text);
                        }

                        @Override
                        public void onFinish() {
                            taskTimerDisplay.setText("DONE");
                        }
                    }.start();
                }
            }
        });

//EDIT: HANGGANG DITO LOL


        // START BUTTON WHEN CLICKED NAGIGING PAUSE BUTTON (SUPER GULO PA NITO HUHU)
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

        updateCountdownText();
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
                        updateButtons();

                    }
                }.start();

                isTimerRunning = true;
                updateButtons();


            }


            /*---- PAUSE COUNTDOWN TIMER ----*/
            public void pauseTimer(){
                taskCountdownTimer.cancel();
                isTimerRunning = false;
                updateButtons();

            }

            /*---- RESET COUNTDOWN TIMER ----*/
            public void resetTimer(){
                timeLeft = startTimeInput;
                updateCountdownText();
                updateButtons();

            }

            /*---- UPDATE COUNTDOWN TEXT DISPLAY ----*/
            public void updateCountdownText(){
                int minutes = (int) (timeLeft/1000)/60;
                int seconds = (int) (timeLeft/1000)%60;

                String timeLeftDisplay = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

                taskTimerDisplay.setText(timeLeftDisplay);

            }

            /*---- UPDATE COUNTDOWN BUTTONS | VISIBILITY & BUTTON TEXT ----*/
            public void updateButtons(){
                if(isTimerRunning){
                    resetCountdownTimer.setVisibility(View.INVISIBLE);
                    startCountdownTimer.setText("PAUSE");
                } else {
                    startCountdownTimer.setText("RESUME");

                    if (timeLeft < 1000){
                        startCountdownTimer.setVisibility(View.INVISIBLE);
                    } else {
                        startCountdownTimer.setVisibility(View.VISIBLE);
                    }

                    if (timeLeft < startTimeInput) {
                        resetCountdownTimer.setVisibility(View.VISIBLE);
                    } else {
                        resetCountdownTimer.setVisibility(View.INVISIBLE);
                    }
                    if (timeLeft == 0){
                        Intent i = new Intent(this, DoneOnTaskActivity.class);
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
            }


            /*---- KEEPS THE TIMER RUNNING ON BACKGROUND OR WHEN ORIENTATION CHANGES----*/

            @Override
            protected void onSaveInstanceState(@NonNull Bundle outState) {
                super.onSaveInstanceState(outState);
                outState.putLong("countdownLeft", timeLeft);
                outState.putBoolean("timerRunning", isTimerRunning);
                outState.putLong("timeEnd", endTime);
            }

            @Override
            protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
                super.onRestoreInstanceState(savedInstanceState);

                timeLeft = savedInstanceState.getLong("countdownLeft");
                isTimerRunning = savedInstanceState.getBoolean("timerRunning");

                updateCountdownText();
                updateButtons();

                    if(isTimerRunning){
                        endTime = savedInstanceState.getLong("timeEnd");
                        timeLeft = endTime - System.currentTimeMillis();
                        startTimer();
                    }
            }


            /*---- DONE BUTTON IF THE USER FINISHED TASK EARLY BEFORE TIME RUNS OUT----*/
            public void doneTaskEarly(View view){

                Intent i = new Intent(this, DoneOnTaskActivity.class);
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