package com.example.focusly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OffTaskOptionsActivity extends AppCompatActivity {

    private Button PlayMusic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_task_options);

        PlayMusic = findViewById(R.id.music_button);

        PlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OffTaskOptionsActivity.this, MusicActivity.class);
                startActivity(i);
            }
        });


    }
}