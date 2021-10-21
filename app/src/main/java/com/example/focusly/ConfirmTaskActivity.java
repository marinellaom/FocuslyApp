package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ConfirmTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_task);
        Intent i = getIntent();

        /*--- GET & DISPLAY USER INPUT OVERVIEW ---*/
        String taskName = i.getStringExtra("TASKNAME");
        String minInput = i.getStringExtra("MININPUT");
        String minInput2 = i.getStringExtra("MININPUT2");
        String secInput = i.getStringExtra("SECINPUT");
        String secInput2 = i.getStringExtra("SECINPUT2");
        ((TextView)findViewById(R.id.task_name_display)).setText(taskName);
        ((TextView)findViewById(R.id.task_name_display2)).setText(minInput + " min " + secInput + " sec");
        ((TextView)findViewById(R.id.task_name_display3)).setText(minInput2 + " min " + secInput2 + " sec");

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

        String taskDisplay = ((TextView)findViewById(R.id.task_name_display)).getText().toString();
        i.putExtra("TASKDISPLAY", taskDisplay);



        startActivity(i);
//        startActivity(txt);

    }



}