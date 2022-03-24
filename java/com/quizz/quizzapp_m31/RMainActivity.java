package com.quizz.quizzapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmain);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }
        Button btnregister = findViewById(R.id.bregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser(){
        EditText Username = findViewById(R.id.Uregister);
        EditText Em = findViewById(R.id.Eregister);
        EditText Pas = findViewById(R.id.Pregister);

        String User= Username.getText().toString();
        String email= Em.getText().toString();
        String password= Pas.getText().toString();

        if(User.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please Fill all fileds", Toast.LENGTH_LONG).show();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User user = new User(User,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            goActivity();


                        } else {

                            Toast.makeText(RMainActivity.this, "User Not Registred!", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }
    public void goActivity(){
        Intent intent = new Intent(RMainActivity.this,QuestionActivity.class);
        startActivity(intent);
        finish();

    }

}