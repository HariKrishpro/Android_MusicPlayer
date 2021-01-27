package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView loginTextView;
    private EditText nameEditText,ageEditText,passwordEditText;
    private Button saveButton,autofillButton;
    long id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setTitle("Login For Music");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        autofillButton = (Button) findViewById(R.id.autofillButton);
        autofillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditText.setText("Hari");
                ageEditText.setText("20");
                passwordEditText.setText("Hari");

            }
        });
        loginTextView.setEnabled(false);
        User user = new User();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("User");
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    id = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(nameEditText.getText().toString().trim());
                user.setAge(Integer.parseInt(ageEditText.getText().toString()));
                user.setPassword(passwordEditText.getText().toString());
                db.child("1").setValue(user);
                Toast.makeText(MainActivity.this,"Done Sucessfully",Toast.LENGTH_LONG).show();

                Intent in = new Intent(getApplicationContext(),Selector.class);
                startActivity(in);

            }
        });



    }
}