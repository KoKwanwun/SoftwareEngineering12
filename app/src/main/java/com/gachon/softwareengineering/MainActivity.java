package com.gachon.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

// fragment 활용 메인화면 설정

public class MainActivity extends AppCompatActivity {

    WeatherAPI api;
    boolean apiFinished = false;
    Button closet, home, weather;

    //불러온 날씨 데이터
    private float temperature = 0.0F;
    private float rainPerHour = 0.0F;
    private float wind = 0.0F;
    private int humidity = 0;
    private int precipitation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //API
        api = new WeatherAPI();
        APIthread apiThread = new APIthread();
        apiThread.start();
        //

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

        //api 불러오기까지 대기(스레드 완료 대기)
        while(!apiFinished){ }
        WindChillTemperature calculator = new WindChillTemperature(temperature,rainPerHour,wind,humidity,precipitation);
        double wct = calculator.calculate();
        RecommendCloth recommend = new RecommendCloth(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),calculator);
        //api 테스트 코드
        Toast.makeText(this, "오늘 체감온도는 "+String.valueOf(Math.round(wct))+"도 입니다", Toast.LENGTH_SHORT).show();
        //

        /*
        temperature: 기온
        rainPerHour: 시간당 강수량(mm)
        wind: 풍속(m/s)
        humidity: 습도(%)
        precipitation: 강수형태(없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7))
         */
    }

    //api용 Thread
    private class APIthread extends Thread{
        public void run(){
            try{
                api.apiLoad();

                temperature = api.getTemperature();
                rainPerHour = api.getRainPerHour();
                wind = api.getWind();
                humidity = api.getHumidity();
                precipitation = api.getPrecipitation();

                apiFinished = true;
            }catch(Exception e){
                Log.d("api thread error", e.toString());
            }


        }
    }
}