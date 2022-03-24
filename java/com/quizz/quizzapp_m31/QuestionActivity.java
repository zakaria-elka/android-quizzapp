package com.quizz.quizzapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    private FirebaseFirestore dbroot;
    private List<Question> lq = new ArrayList<>();
    private int scoreCalcul=0,indice=1;
    private int docI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //////////////////////////////////////////////////////////////////////////////

        dbroot = FirebaseFirestore.getInstance();

        dbroot.collection("question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot d : task.getResult()) {

                        Question q = d.toObject(Question.class);

                        lq.add(q);

                    }
                    reloadQuestion();
                }


            }
        });


        RadioGroup radioGroup = findViewById(R.id.radio);

        Button btnext = findViewById(R.id.nextbtn);

        btnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();



                if(selectedId !=-1){

                    RadioButton radioButton = findViewById(selectedId);




                    radioGroup.clearCheck();

                    qmanage(radioButton.getText().toString());

                }
                else {
    Toast.makeText(QuestionActivity.this,"Please select choice",Toast.LENGTH_LONG).show();

                }


            }
        });


        ///////////////////////////////////////////////////////////////////////////
        TextView showuser = findViewById(R.id.showusername);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(QuestionActivity.this, "Error", Toast.LENGTH_SHORT).show();
            Intent switchActivityIntent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(switchActivityIntent);
            finish();
            return;
        }

        Toast.makeText(QuestionActivity.this, "Connect as " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                if (user != null) {
                    showuser.setText(" " + user.Username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });


    }

    private void signout() {
        FirebaseAuth.getInstance().signOut();
        Intent switchActivityIntent = new Intent(QuestionActivity.this, WelcomActivity.class);
        startActivity(switchActivityIntent);
        finish();

    }

    private int qmanage(String k) {

        if(k.equals(lq.get(docI).Truevalue)){

            scoreCalcul=scoreCalcul+20;



        }
        if(indice!=5){

            indice++;
            reloadQuestion();
        }
        else{
            indice=1;
            Intent intent=new Intent(QuestionActivity.this,ScoreActivity.class);
            intent.putExtra("score",String.valueOf(scoreCalcul));
            scoreCalcul=0;
            startActivity(intent);

        }

        return scoreCalcul;
    }

    //////////////////////////////////////////////////////////
    private void showquestion(int i) {

        ImageView img = findViewById(R.id.Qimage);


        int j;
        Random rand= new Random();
        RadioButton r1 = findViewById(R.id.value1);
        RadioButton r2 = findViewById(R.id.value2);
        RadioButton r3 = findViewById(R.id.value3);
        j = rand.nextInt(5);

        StorageReference nstorageReference;
        nstorageReference = FirebaseStorage.getInstance().getReference().child(lq.get(i).image.toString());

        try {
            final File localfile = File.createTempFile("image","jpg");
            nstorageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    img.setImageBitmap(bitmap);





                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (j) {
            case 0:
                r1.setText(lq.get(docI).Truevalue);
                r2.setText(lq.get(docI).falsevalue3);
                r3.setText(lq.get(docI).falsevalue1);
                break;

            case 2:
                r1.setText(lq.get(docI).falsevalue4);
                r2.setText(lq.get(docI).Truevalue);
                r3.setText(lq.get(docI).falsevalue2);
                break;

            case 3:
                r3.setText(lq.get(docI).falsevalue3);
                r1.setText(lq.get(docI).falsevalue1);
                r2.setText(lq.get(docI).Truevalue);
                break;

            case 1:
                r3.setText(lq.get(docI).falsevalue3);
                r1.setText(lq.get(docI).falsevalue1);
                r2.setText(lq.get(docI).Truevalue);
                break;
        }

    }


    private void reloadQuestion(){
        Random rand= new Random();



        docI = rand.nextInt(lq.size());

        showquestion(docI);


        }







}