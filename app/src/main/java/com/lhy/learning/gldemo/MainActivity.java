package com.lhy.learning.gldemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lhy.learning.gldemo.activity.Lesson1Activity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void lesson1(View view) {
        Intent intent = new Intent(this, Lesson1Activity.class);
        startActivity(intent);
    }
}