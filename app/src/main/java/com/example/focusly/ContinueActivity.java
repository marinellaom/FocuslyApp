package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContinueActivity extends AppCompatActivity {

    private Button cont;
    private Button rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        cont = findViewById(R.id.continute_task_btn);
        rest = findViewById(R.id.rest_btn);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContinueActivity.this, OnTaskActivity.class);
                startActivity(i);
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContinueActivity.this, OffTaskActivity.class);
                startActivity(i);
            }
        });
    }
}