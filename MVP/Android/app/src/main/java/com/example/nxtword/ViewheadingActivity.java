package com.example.nxtword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewheadingActivity extends AppCompatActivity {

    TextView heading, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewheading);
        Intent i = getIntent();

        String head  = i.getStringExtra("heading");
        String descr  = i.getStringExtra("description");

        heading = findViewById(R.id.TextHeading);
        description = findViewById(R.id.TextDescription);

        heading.setText(head);
        description.setText(descr);


    }
}