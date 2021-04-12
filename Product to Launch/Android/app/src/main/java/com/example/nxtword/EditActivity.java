package com.example.nxtword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EditActivity extends AppCompatActivity {

    EditText heading, description;
    Button save;
    String headingInfo, descriptionInfo;
    FirebaseFirestore db;
    Button predictHeading, predictDescription;
    List<String> index_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        heading = findViewById(R.id.editTextHeading);
        description = findViewById(R.id.editTextDescription);
        save = findViewById(R.id.editActivityButtonSave);
        predictHeading = findViewById(R.id.predictHeading);
        predictDescription = findViewById(R.id.predictDescription);

        db = FirebaseFirestore.getInstance();
        InputStream index_word_is = getResources().openRawResource(R.raw.index_word);
        InputStream word_index_is = getResources().openRawResource(R.raw.word_index);

        Scanner index_word_scanner =  new Scanner(index_word_is);
        Scanner word_index_scanner =  new Scanner(word_index_is);

        StringBuilder index_word_sb  = new StringBuilder();
        StringBuilder word_index_sb  = new StringBuilder();

        while (index_word_scanner.hasNextLine()){
            index_word_sb.append(index_word_scanner.nextLine());
        }
        while (word_index_scanner.hasNextLine()){
            word_index_sb.append(word_index_scanner.nextLine());
        }

        index_word = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(index_word_sb.toString());
            for(int arr=0; arr<jsonArray.length();arr++){
                index_word.add(jsonArray.getJSONObject(arr).getString(String.valueOf(arr+1)));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        

        heading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 if (s.toString().endsWith(" ")){
                     String[] lastword = s.toString().split(" ");
                     int[] value = new int[1];
                     for (int i=0;i<index_word.size();i++){
                         if(index_word.get(i).matches(lastword[lastword.length-1])){
                             value[0] = i+1;
                             break;
                         }
                     }
                     Log.i("aaaaaaaaa", String.valueOf(value[0]));

                 }

            }
        });


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