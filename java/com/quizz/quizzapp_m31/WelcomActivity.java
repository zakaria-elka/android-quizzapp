package com.quizz.quizzapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent switchActivityIntent = new Intent(WelcomActivity.this, QuestionActivity.class);
            startActivity(switchActivityIntent);
            finish();
            return;

        }

        Button btnlogin = findViewById(R.id.blogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userlogin();
            }
        });

        Button btngoregister = findViewById(R.id.goRegister);
        btngoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();

            }
        });
    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(WelcomActivity.this, RMainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void Userlogin(){
        EditText etemail = findViewById(R.id.Ulogin);
        EditText etpass = findViewById(R.id.Plogin);

        String email = etemail.getText().toString();
        String password = etpass.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(WelcomActivity.this, "Fill All fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goQuestion();

                        } else {
                            Toast.makeText(WelcomActivity.this, "Error Login", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    private void goQuestion(){
       Intent intent = new Intent(WelcomActivity.this,QuestionActivity.class);
       startActivity(intent);
       finish();


    }
}