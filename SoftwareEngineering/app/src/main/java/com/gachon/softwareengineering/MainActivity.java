package com.gachon.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// fragment 활용 메인화면 설정

public class MainActivity extends AppCompatActivity {

    Button closet, home, weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment_home fragment_home = new Fragment_home();
        transaction.replace(R.id.frame, fragment_home);
        transaction.commit();

        closet = (Button) findViewById(R.id.closet);
        home = (Button) findViewById(R.id.home);
        weather = (Button) findViewById(R.id.weather);

        closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment_closet fragment_closet = new Fragment_closet();
                transaction.replace(R.id.frame, fragment_closet);
                transaction.commit();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment_home fragment_home = new Fragment_home();
                transaction.replace(R.id.frame, fragment_home);
                transaction.commit();
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment_weather fragment_weather = new Fragment_weather();
                transaction.replace(R.id.frame, fragment_weather);
                transaction.commit();
            }
        });
    }
}