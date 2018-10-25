package com.example.willi.buddy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class quiz_home extends AppCompatActivity {

    Button c1, c2, c3;

    //Navigation bottom bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    startActivity(new Intent(quiz_home.this, MainActivity.class));
                    return true;
                case R.id.navigation_Quiz:
                    return true;
                case R.id.navigation_todo:
                    startActivity(new Intent(quiz_home.this, to_do.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        c1 = findViewById(R.id.b1);
        c2 = findViewById(R.id.b2);
        c3 = findViewById(R.id.b3);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_Quiz);

        //Button on click listener
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quiz_home.this, quiz_start.class);
                intent.putExtra("quizCat", 1);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quiz_home.this, quiz_start.class);
                intent.putExtra("quizCat", 2);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quiz_home.this, quiz_start.class);
                intent.putExtra("quizCat", 3);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(quiz_home.this, MainActivity.class));
    }
}