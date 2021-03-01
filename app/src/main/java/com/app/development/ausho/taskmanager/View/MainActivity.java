package com.app.development.ausho.taskmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.app.development.ausho.taskmanager.R;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //initialise Mobile ads
        MobileAds.initialize(this, "ca-app-pub-6254720266276269~6124952935");

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i = new Intent(MainActivity.this,NotesListPage.class);
               startActivity(i);

           }
       },1000);
    }
}