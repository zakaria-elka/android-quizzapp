package com.quizz.quizzapp_m31;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.rpc.Help;


import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.Reference;
import java.net.URL;


public class Question {


    public String image;
    public String Truevalue;
    public String falsevalue1;
    public String falsevalue2;
    public String falsevalue3;
    public String falsevalue4;

    public Question(){

    }

    public Question(String Truevalue, String falsevalue1, String falsevalue2
                   ,String falsevalue3,String falsevalue4,String image){


        this.image=image;
        this.falsevalue3=falsevalue3;
        this.falsevalue4=falsevalue4;
        this.Truevalue = Truevalue;
        this.falsevalue1 =  falsevalue1;
        this.falsevalue2 = falsevalue2;
    }
}
