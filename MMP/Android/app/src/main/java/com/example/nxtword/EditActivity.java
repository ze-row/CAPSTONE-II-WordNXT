package com.example.nxtword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    EditText heading, description;
    Button save;
    String headingInfo, descriptionInfo;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        heading = findViewById(R.id.editTextHeading);
        description = findViewById(R.id.editTextDescription);
        save = findViewById(R.id.editactivitybuttonsave);
        db = FirebaseFirestore.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headingInfo = heading.getText().toString().trim();
                descriptionInfo = description.getText().toString().trim();

                Map<String, Object> notes = new HashMap<>();
                notes.put("Heading", headingInfo);
                notes.put("Description",descriptionInfo);
                String timestamp = String.valueOf(System.currentTimeMillis());

                db.collection(FirebaseAuth.getInstance().getUid()).document(timestamp)
                        .set(notes, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditActivity.this,"notes info saved",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }
}