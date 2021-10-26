package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        final Spinner OnSpinner = findViewById(R.id.on_spinner);
        final Spinner OffSpinner = findViewById(R.id.off_spinner);
        Button set_button = findViewById(R.id.set_button);

        //DEFAULT TIME LIST
        Integer[] ontime = new Integer[]{15, 20, 25, 30, 35, 40, 45};
        Integer[] offtime = new Integer[]{10, 15, 20, 25, 30};

        //ADAPTER describes how the items (time list) are displayed
        ArrayAdapter<Integer> OnAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_layout,  ontime);
        ArrayAdapter<Integer> OffAdapter = new ArrayAdapter<>(this, R.layout.spinner_text_layout, offtime);

        OnSpinner.setAdapter(OnAdapter);
        OffSpinner.setAdapter(OffAdapter);


        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*--- GET AND DISPLAY INFO TO OTHER ACTIVITY (PAGE) ---*/
                Intent i = new Intent(SetTaskTimerActivity.this, ConfirmTaskActivity.class);

                String onTimeDisplay = OnSpinner.getSelectedItem().toString();
                String offTimeDisplay = OffSpinner.getSelectedItem().toString();
                String taskName= ((EditText)findViewById(R.id.taskname)).getText().toString();

                ((EditText)findViewById(R.id.taskname)).setText(taskName);

                i.putExtra("TASKNAME", taskName);
                i.putExtra("ONTIME", onTimeDisplay);
                i.putExtra("OFFTIME", offTimeDisplay);


                /*--- VALIDATE USER INPUT IF EMPTY---*/
                if (TextUtils.isEmpty(taskName)){
                    Toast.makeText(SetTaskTimerActivity.this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((taskName).trim().length()==0){
                    Toast.makeText(SetTaskTimerActivity.this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(i);
            }
        });

    }


    public void goBack(View back){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

//    // SET BUTTON
//    public void setTaskTimer(View set){
//
//        Intent i = new Intent(this, ConfirmTaskActivity.class);
//
//        String taskName= ((EditText)findViewById(R.id.taskname)).getText().toString();
//
//        ((EditText)findViewById(R.id.taskname)).setText(taskName);
//
//        i.putExtra("TASKNAME", taskName);
//
//
//        /*--- VALIDATE USER INPUT IF EMPTY---*/
//        // INPUT VALIDATION - MIN / MAX / EMPTY / ZERO
//        if (TextUtils.isEmpty(taskName)){
//            Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
//            return;
//            } else if ((taskName).trim().length()==0){
//                Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        startActivity(i);
//    }

}
