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
        String taskinput = i.getStringExtra("TASKINPUT");
        String taskinput1 = i.getStringExtra("TASKINPUT1");
        String taskinput2 = i.getStringExtra("TASKINPUT2");
        String taskinput3 = i.getStringExtra("TASKINPUT3");
        String taskinput4 = i.getStringExtra("TASKINPUT4");
        ((TextView)findViewById(R.id.task_name_display)).setText(taskinput);
        ((TextView)findViewById(R.id.task_name_display2)).setText(taskinput1 + " min " + taskinput3 + " sec");
        ((TextView)findViewById(R.id.task_name_display3)).setText(taskinput2 + " min " + taskinput4 + " sec");

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