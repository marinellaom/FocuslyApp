package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ConfirmTaskActivity extends AppCompatActivity {

    TextView taskNameDisplay;
    TextView OnTimeDisplay;
    TextView OffTimeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_task);

        /*--DISPLAY USER INPUT--*/
        taskNameDisplay = (TextView) findViewById(R.id.task_name_display);
        taskNameDisplay.setText(GlobalVariable.taskname);

        OnTimeDisplay = (TextView) findViewById(R.id.on_time_display);
        OffTimeDisplay = (TextView) findViewById(R.id.off_time_display);

        OnTimeDisplay.setText(GlobalVariable.OnTimer + " minutes");
        OffTimeDisplay.setText(GlobalVariable.OffTimer + " minutes");

    }


    /*--- BACK BUTTON ---*/
    public void goBack(View back){
        Intent i = new Intent(this, SetTaskTimerActivity.class);
        startActivity(i);
    }



    public void confirmInput(View displayText){
        displayText.setEnabled(false);

//        Toast.makeText(this, "Confirmed!", Toast.LENGTH_SHORT).show();
//
//        Log.d("myLog", "User Input Confirmed");
//
//        Intent txt = new Intent(this, OnTaskActivity.class);

        Intent i = new Intent(this, OnTaskActivity.class);
//
        String taskDisplay = ((TextView)findViewById(R.id.task_name_display)).getText().toString();
        i.putExtra("TASKDISPLAY", taskDisplay);



        startActivity(i);
//        startActivity(txt);

    }



}