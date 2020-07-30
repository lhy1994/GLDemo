package com.lhy.learning.gldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lhy.learning.gldemo.R;
import com.lhy.learning.gldemo.test.MyActivity;

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

    public void openFingerPaint(View view) {
        Intent intent = new Intent(this, FingerPaintActivity.class);
//        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }
}