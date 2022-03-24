package com.quizz.quizzapp_m31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView txt = findViewById(R.id.progresText);
        ProgressBar prg = findViewById(R.id.progressbar);


        Intent myIntent = getIntent();
        String Score=myIntent.getStringExtra("score");


        txt.setText(Score+"%");
        prg.setProgress(Integer.parseInt(Score));


        Button btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }

    });

}
    private void signout(){
        FirebaseAuth.getInstance().signOut();
        Intent switchActivityIntent = new Intent(ScoreActivity.this, WelcomActivity.class);
        startActivity(switchActivityIntent);
        finish();

    }
}