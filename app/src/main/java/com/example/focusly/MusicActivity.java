package com.example.focusly;

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

    private TextView taskTimerDisplay;
    private Button startCountdownTimer;
    private Button resetCountdownTimer;

    private boolean isTimerRunning;

    private CountDownTimer taskCountdownTimer;

    private long startTimeInput;
    private long timeLeft;
    private long endTime;

    //EDIT: TEMPORARY | TRY SET INPUT TIME AS TIMER (SHOULD NOT BE ON SAME PAGE)
    private EditText editText1;
    private Button set_button1;

    // SOUNDS
    private ImageView natureSounds;
    private ImageView pianoSounds;
    private ImageView localMusicFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        taskTimerDisplay = findViewById(R.id.music_countdown);
        startCountdownTimer = findViewById(R.id.button_start_pause);
        resetCountdownTimer = findViewById(R.id.button_reset);

        //EDIT: TEMPORARY | TRY SET INPUT TIME AS TIMER (SHOULD NOT BE ON SAME PAGE)
        editText1 = findViewById(R.id.minute);
        set_button1 = findViewById(R.id.set_time_music);


        //EDIT: MUSIC BUTTONS
        natureSounds = (ImageView) findViewById(R.id.rain_button);
        pianoSounds = (ImageView) findViewById(R.id.piano_button);
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(MusicActivity.this, R.raw.nature);
        final MediaPlayer mediaPlayer2 = MediaPlayer.create(MusicActivity.this, R.raw.piano);

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

        //EDIT: TEMPORARY | TRY SET INPUT TIME AS TIMER (SHOULD NOT BE ON SAME PAGE)
        set_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText1.getText().toString();
                if (text.length() == 0) {
                    Toast.makeText(MusicActivity.this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(text) * 60000;
                if (millisInput  == 0){
                    Toast.makeText(MusicActivity.this, "Positive Number Only!", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                editText1.setText("");

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

    private void setTime(long milliseconds){
        startTimeInput = milliseconds;
        resetTimer();
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
        timeLeft = startTimeInput;
        updateCountdownText();
        updateTimeInterface();

    }

    /*---- UPDATE COUNTDOWN TEXT DISPLAY ----*/
    public void updateCountdownText(){
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft/1000)%3600)/60;
        int seconds = (int) (timeLeft/1000)%60;

        String timeLeftDisplay;
        if (hours > 0){
            timeLeftDisplay = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else{
            timeLeftDisplay = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        taskTimerDisplay.setText(timeLeftDisplay);

    }

    /*---- UPDATE COUNTDOWN BUTTONS | VISIBILITY & BUTTON TEXT ----*/
    public void updateTimeInterface(){
        if(isTimerRunning){
            editText1.setVisibility(View.INVISIBLE);
            set_button1.setVisibility(View.INVISIBLE);

            resetCountdownTimer.setVisibility(View.INVISIBLE);
            startCountdownTimer.setText("PAUSE");
        } else {
//            editText1.setVisibility(View.VISIBLE);
//            set_button1.setVisibility(View.VISIBLE);
            startCountdownTimer.setText("START");

//            if (timeLeft < 1000){
//                startCountdownTimer.setVisibility(View.INVISIBLE);
//            } else {
//                startCountdownTimer.setVisibility(View.VISIBLE);
//            }

            if (timeLeft < startTimeInput) {
                resetCountdownTimer.setVisibility(View.VISIBLE);
            } else {
                resetCountdownTimer.setVisibility(View.INVISIBLE);
            }

            if (timeLeft < 1000){

                /*----DEFAULT TIME----*/
                timeLeft = 600000;
                updateCountdownText();

                /*---- PROCEEDS TO NEXT PAGE IF TIMER IS DONE ----*/
                /*---- TEMPORARY: IBA PA DAPAT PAGE NA PUPUNTAHAN NETO ----*/
                Intent in = new Intent(MusicActivity.this, ContinueActivity.class);
                startActivity(in);


//                if(isTimerRunning){
//                    timeLeft = startTimeInput;
//                    taskCountdownTimer.cancel();
//                    updateCountdownText();
//                }
            }
        }
    }


    /*---- KEEPS THE TIMER RUNNING ON BACKGROUND OR WHEN ORIENTATION CHANGES----*/


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTime", startTimeInput);
        editor.putLong("millisLeft", timeLeft);
        editor.putBoolean("timerRunning", isTimerRunning);
        editor.putLong("timeEnd", endTime);

        editor.apply();

        if (taskCountdownTimer != null){
            taskCountdownTimer.cancel();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        startTimeInput = prefs.getLong("startTime", 600000);
        timeLeft = prefs.getLong("millisLeft", startTimeInput);
        isTimerRunning = prefs.getBoolean("timerRunning", false);


        updateCountdownText();
        updateTimeInterface();

        if(isTimerRunning){
            endTime = prefs.getLong("timeEnd", 0);
            timeLeft = endTime - System.currentTimeMillis();

            if (timeLeft < 0){
                timeLeft = 0;
                isTimerRunning = false;
                updateCountdownText();
                updateTimeInterface();
            } else {
                startTimer();
            }
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