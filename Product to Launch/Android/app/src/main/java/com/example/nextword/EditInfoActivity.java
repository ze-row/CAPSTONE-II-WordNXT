package com.example.nextword;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nextword.ml.MyModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EditInfoActivity extends AppCompatActivity {

    EditText heading, description;
    Button save;
    String headingInfo, descriptionInfo;
    FirebaseFirestore db;
    Button predictHeading, predictDescription;
    List<String> index_word;
    int accept,decline,blank_head,blank_des;
    String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);


        Intent i = getIntent();

        String head  = i.getStringExtra("heading");
        String descr  = i.getStringExtra("description");
        timestamp = i.getStringExtra("timestamp");

        String[] blank_d = descr.split(" ");
        blank_des = blank_d.length;

        String[] blank_h = head.split(" ");
        blank_head = blank_h.length;



        heading = findViewById(R.id.editInfoTextHeading);
        description = findViewById(R.id.editInfoTextDescription);

        heading.setText(head);
        description.setText(descr);
        description.setSelection(description.getText().length());
        heading.setSelection(heading.getText().length());



        save = findViewById(R.id.editInfoActivityButtonSave);
        predictHeading = findViewById(R.id.predictHeadingInfo);
        predictDescription = findViewById(R.id.predictDescriptionInfo);

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
                if (s!=" "){
                    predictHeading.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(" ")) {
                    if (s.toString().endsWith(" ")) {
                        String[] lastword = s.toString().split(" ");
                        int[] value = new int[1];
                        for (int i = 0; i < index_word.size(); i++) {
                            if (index_word.get(i).matches(lastword[lastword.length - 1])) {
                                value[0] = i + 1;
                                break;
                            }
                        }
                        try {
                            MyModel model = MyModel.newInstance(EditInfoActivity.this);

                            // Creates inputs for reference.
                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                            int[] input = new int[1];
                            input[0] = value[0];
                            inputFeature0.loadArray(input);

                            // Runs model inference and gets result.
                            MyModel.Outputs outputs = model.process(inputFeature0);
                            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                            String output = getMax(outputFeature0.getFloatArray());
                            predictHeading.setVisibility(View.VISIBLE);
                            predictHeading.setText(output);
                            predictHeading.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    accept++;
                                    String pred_word = heading.getText().toString().trim();
                                    heading.setText(pred_word + " " + output + " ");
                                    heading.setSelection(heading.getText().length());
                                }
                            });


                            // Releases model resources if no longer used.
                            model.close();
                        } catch (IOException e) {
                            // TODO Handle the exception
                        }


                    }
                }

            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=" "){
                    predictDescription.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(" ")) {
                    if (s.toString().endsWith(" ")) {
                        String[] lastword = s.toString().split(" ");
                        int[] value = new int[1];
                        for (int i = 0; i < index_word.size(); i++) {
                            if (index_word.get(i).matches(lastword[lastword.length - 1])) {
                                value[0] = i + 1;
                                break;
                            }
                        }
                        try {
                            MyModel model = MyModel.newInstance(EditInfoActivity.this);

                            // Creates inputs for reference.
                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                            int[] input = new int[1];
                            input[0] = value[0];
                            inputFeature0.loadArray(input);

                            // Runs model inference and gets result.
                            MyModel.Outputs outputs = model.process(inputFeature0);
                            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                            String output = getMax(outputFeature0.getFloatArray());
                            predictDescription.setVisibility(View.VISIBLE);
                            predictDescription.setText(output);
                            predictDescription.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    accept++;
                                    String pred_word = description.getText().toString().trim();
                                    description.setText(pred_word + " " + output + " ");
                                    description.setSelection(description.getText().length());
                                }
                            });


                            // Releases model resources if no longer used.
                            model.close();
                        } catch (IOException e) {
                            // TODO Handle the exception
                        }


                    }
                }

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headingInfo = heading.getText().toString().trim();
                descriptionInfo = description.getText().toString().trim();

                String[] blanks_heading = headingInfo.split(" ");
                String[] blanks_description = descriptionInfo.split(" ");
                int decline = blanks_heading.length + blanks_description.length -2 - accept - blank_des - blank_head;

                String time = String.valueOf(System.currentTimeMillis());
                Map<String, Object> show = new HashMap<>();
                show.put("accept", accept);
                show.put("decline",decline);
                show.put("timestamp",time);

                db.collection("Administration").document(time)
                        .set(show, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditInfoActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });



                Map<String, Object> notes = new HashMap<>();
                notes.put("Heading", headingInfo);
                notes.put("Description",descriptionInfo);
                notes.put("Timestamp",timestamp);

                db.collection(FirebaseAuth.getInstance().getUid()).document(timestamp)
                        .set(notes, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditInfoActivity.this,"notes edited",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditInfoActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    public String getMax(float[] data){
        int j = 0;
        float max = Float.MIN_VALUE;
        for (int i = 0;i<data.length;i++){
            if(max < data[i]){
                max = data[i];
                j = i;
            }
        }
        String output = index_word.get(j-1);
        return output;
    }
}