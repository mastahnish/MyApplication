package com.example.piotr.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick1(View view) {

        Intent i1 = new Intent(this, ListActivity.class);
        startActivity(i1);
    }

    public void onClick2(View view) {
        Intent i2 = new Intent(this, OptionActivity.class);
        startActivity(i2);
    }


}
