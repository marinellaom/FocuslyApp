package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import android.*;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;


public class SetTaskTimerActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_task_timer);
        setTitle("On Task");

    }


    public void goBack(View back){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // SET BUTTON
    public void setTaskTimer(View set){

        Intent i = new Intent(this, ConfirmTaskActivity.class);

        String taskName= ((EditText)findViewById(R.id.taskname)).getText().toString();
        String minInput= ((EditText)findViewById(R.id.minuteInput)).getText().toString();
        String minInput2= ((EditText)findViewById(R.id.minuteInput2)).getText().toString();
        String secInput= ((EditText)findViewById(R.id.secondsInput)).getText().toString();
        String secInput2= ((EditText)findViewById(R.id.secondsInput2)).getText().toString();

        ((EditText)findViewById(R.id.taskname)).setText(taskName);
        ((EditText)findViewById(R.id.minuteInput)).setText(minInput);
        ((EditText)findViewById(R.id.minuteInput2)).setText(minInput2);
        ((EditText)findViewById(R.id.secondsInput)).setText(secInput);
        ((EditText)findViewById(R.id.secondsInput2)).setText(secInput2);

        i.putExtra("TASKNAME", taskName);
        i.putExtra("MININPUT", minInput);
        i.putExtra("MININPUT2", minInput2);
        i.putExtra("SECINPUT", secInput);
        i.putExtra("SECINPUT2", secInput2);


        /*--- VALIDATE USER INPUT IF EMPTY---*/
        // INPUT VALIDATION - MIN / MAX / EMPTY / ZERO
        if (TextUtils.isEmpty(taskName)){
            Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
            } else if ((taskName).trim().length()==0){
                Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                return;
            } else if (minInput.length()  == 0){
                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(minInput) > 45){
                Toast.makeText(this, "ON TASK: 45mins maximum time only.", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(minInput) < 15){
                Toast.makeText(this, "ON TASK: 15mins minimum time only.", Toast.LENGTH_SHORT).show();
                return;
            } else if (minInput2.length()  == 0){
                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(minInput2) > 30){
                Toast.makeText(this, "OFF TASK: 30mins maximum time only.", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(minInput2) < 10){
                Toast.makeText(this, "OFF TASK: 10mins minimum time only.", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(secInput) > 60){
                Toast.makeText(this, "SECONDS: Invalid Input!", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.valueOf(secInput2) > 60){
                Toast.makeText(this, "SECONDS: Invalid Input!", Toast.LENGTH_SHORT).show();
                return;
            }
        startActivity(i);
    }

}
