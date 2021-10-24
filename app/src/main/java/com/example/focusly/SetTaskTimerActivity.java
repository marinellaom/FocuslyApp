package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.text.TextUtils;


public class SetTaskTimerActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_task_timer);
        setTitle("On Task");

        Spinner OnSpinner = findViewById(R.id.on_spinner);
        Spinner OffSpinner = findViewById(R.id.off_spinner);

        //create a list of items for the spinner.
        Integer[] ontime = new Integer[]{15, 20, 25, 30, 35, 40, 45};
        Integer[] offtime = new Integer[]{10, 15, 20, 25, 30};

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<Integer> OnAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_layout,  ontime);
        ArrayAdapter<Integer> OffAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_layout, offtime);

        //set the spinners adapter to the previously created one.
        OnSpinner.setAdapter(OnAdapter);
        OffSpinner.setAdapter(OffAdapter);


    }


    public void goBack(View back){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // SET BUTTON
    public void setTaskTimer(View set){

        Intent i = new Intent(this, ConfirmTaskActivity.class);

        String taskName= ((EditText)findViewById(R.id.taskname)).getText().toString();
//        String minInput= ((EditText)findViewById(R.id.minuteInput)).getText().toString();
//        String minInput2= ((EditText)findViewById(R.id.minuteInput2)).getText().toString();
//        String secInput= ((EditText)findViewById(R.id.secondsInput)).getText().toString();
//        String secInput2= ((EditText)findViewById(R.id.secondsInput2)).getText().toString();

        ((EditText)findViewById(R.id.taskname)).setText(taskName);
//        ((EditText)findViewById(R.id.minuteInput)).setText(minInput);
//        ((EditText)findViewById(R.id.minuteInput2)).setText(minInput2);
//        ((EditText)findViewById(R.id.secondsInput)).setText(secInput);
//        ((EditText)findViewById(R.id.secondsInput2)).setText(secInput2);

        i.putExtra("TASKNAME", taskName);
//        i.putExtra("MININPUT", minInput);
//        i.putExtra("MININPUT2", minInput2);
//        i.putExtra("SECINPUT", secInput);
//        i.putExtra("SECINPUT2", secInput2);


        /*--- VALIDATE USER INPUT IF EMPTY---*/
        // INPUT VALIDATION - MIN / MAX / EMPTY / ZERO
        if (TextUtils.isEmpty(taskName)){
            Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
            } else if ((taskName).trim().length()==0){
                Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                return;
//            } else if (minInput.length()  == 0){
//                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(minInput) > 45){
//                Toast.makeText(this, "ON TASK: 45mins maximum time only.", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(minInput) < 15){
//                Toast.makeText(this, "ON TASK: 15mins minimum time only.", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (minInput2.length()  == 0){
//                Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(minInput2) > 30){
//                Toast.makeText(this, "OFF TASK: 30mins maximum time only.", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(minInput2) < 10){
//                Toast.makeText(this, "OFF TASK: 10mins minimum time only.", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(secInput) > 60){
//                Toast.makeText(this, "SECONDS: Invalid Input!", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (Integer.valueOf(secInput2) > 60){
//                Toast.makeText(this, "SECONDS: Invalid Input!", Toast.LENGTH_SHORT).show();
//                return;
            }
        startActivity(i);
    }

}
