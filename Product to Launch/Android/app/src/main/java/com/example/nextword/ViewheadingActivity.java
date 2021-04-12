package com.example.nextword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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