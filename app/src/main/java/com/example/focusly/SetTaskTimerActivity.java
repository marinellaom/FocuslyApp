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


    public EditText tv_hour;
    public EditText tv_minute;
    public EditText tv_second;


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

    public void setTaskTimer(View set){

        Intent i = new Intent(this, ConfirmTaskActivity.class);

        String taskinput= ((EditText)findViewById(R.id.taskname)).getText().toString();
        String taskinput1= ((EditText)findViewById(R.id.minuteInput)).getText().toString();
        String taskinput2= ((EditText)findViewById(R.id.minuteInput2)).getText().toString();
        String taskinput3= ((EditText)findViewById(R.id.secondsInput)).getText().toString();
        String taskinput4= ((EditText)findViewById(R.id.secondsInput2)).getText().toString();
        ((EditText)findViewById(R.id.taskname)).setText(taskinput);
        ((EditText)findViewById(R.id.minuteInput)).setText(taskinput1);
        ((EditText)findViewById(R.id.minuteInput2)).setText(taskinput2);
        ((EditText)findViewById(R.id.secondsInput)).setText(taskinput3);
        ((EditText)findViewById(R.id.secondsInput2)).setText(taskinput4);
        i.putExtra("TASKINPUT", taskinput);
        i.putExtra("TASKINPUT1", taskinput1);
        i.putExtra("TASKINPUT2", taskinput2);
        i.putExtra("TASKINPUT3", taskinput3);
        i.putExtra("TASKINPUT4", taskinput4);

//
//        String inputMinute = ((EditText)findViewById(R.id.minuteInput)).getText().toString();
//        String inputSecond = ((EditText)findViewById(R.id.secondsInput)).getText().toString();
//
//        /*--- VALIDATE USER INPUT IF EMPTY---*/
//        if (TextUtils.isEmpty(taskinput)){
//            Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
//            return;
//            } else if ((taskinput).trim().length()==0){
//                Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (inputMinute.length()  == 0){
//                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (inputSecond.length()  == 0){
//                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
//                return;
//            }

        startActivity(i);
    }

}

