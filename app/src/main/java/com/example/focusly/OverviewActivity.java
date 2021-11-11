package com.example.focusly;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverviewActivity extends AppCompatActivity {

    private Button continueBtn;
    private Button setNewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        continueBtn = findViewById(R.id.overview_cont);
        setNewBtn = findViewById(R.id.overview_set_new);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OverviewActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
//                builder.setMessage("Click 'OK' if you would like to continue your task");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(OverviewActivity.this, OnTaskActivity.class);
                        startActivity(in);
                    }
                });
                builder.show();
            }
        });


        setNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(OverviewActivity.this, SetTaskTimerActivity.class);
                startActivity(i);

            }
        });


    }
}