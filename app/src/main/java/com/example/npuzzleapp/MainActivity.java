package com.example.npuzzleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tekst = (TextView) findViewById(R.id.textView);
    }

    public void shuffle(android.view.View v){
        tekst.setText("shuffle");
    }

    public void reset(android.view.View v){
        tekst.setText("reset");
    }
}
