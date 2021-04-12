package com.example.nextword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewheadingActivity extends AppCompatActivity {

    TextView heading, description;
    Button edit, delete;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewheading);
        Intent i = getIntent();

        String head  = i.getStringExtra("heading");
        String descr  = i.getStringExtra("description");
        String timesta = i.getStringExtra("timestamp");

        heading = findViewById(R.id.TextHeading);
        description = findViewById(R.id.TextDescription);
        edit = findViewById(R.id.editbutton);
        delete = findViewById(R.id.deletebutton);

        heading.setText(head);
        description.setText(descr);

        db = FirebaseFirestore.getInstance();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewheadingActivity.this,EditInfoActivity.class);
                i.putExtra("heading",head);
                i.putExtra("description",descr);
                i.putExtra("timestamp",timesta);
                startActivity(i);
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(FirebaseAuth.getInstance().getUid()).document(timesta)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ViewheadingActivity.this,"Deleted information",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("aaaaaaaa", "Error deleting document", e);
                            }
                        });
            }
        });


    }
}