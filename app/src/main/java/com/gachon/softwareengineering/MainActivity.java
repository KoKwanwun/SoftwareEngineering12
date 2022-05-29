package com.gachon.softwareengineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// fragment 활용 메인화면 설정

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment_closet fragmentCloset = new Fragment_closet();
    private Fragment_home fragmentHome = new Fragment_home();
    private Fragment_weather fragmentWeather = new Fragment_weather();

    WeatherAPI api;
    boolean apiFinished = false;
    Button closet, home, weather;
    Bundle bundle;

    //불러온 날씨 데이터
    WindChillTemperature calculator = new WindChillTemperature();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frameLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        //API
        api = new WeatherAPI();
        APIthread apiThread = new APIthread();
        apiThread.start();

        //api 불러오기까지 대기(스레드 완료 대기)
        while(!apiFinished){ }

        double wct = calculator.calculate();
        //날씨 프래그먼트으로 날씨정보 보내기
        bundle = new Bundle();
        bundle.putSerializable("weather",calculator);
        fragmentWeather.setArguments(bundle);

        RecommendCloth recommend = new RecommendCloth(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),calculator);
        //api 테스트 코드
        Toast.makeText(this, "오늘 체감온도는 "+String.valueOf(Math.round(wct))+"도 입니다", Toast.LENGTH_SHORT).show();
        //
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.menu_closet:
                    transaction.replace(R.id.main_frameLayout, fragmentCloset).commitAllowingStateLoss();
                    break;
                case R.id.menu_home:
                    transaction.replace(R.id.main_frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.menu_weather:
                    transaction.replace(R.id.main_frameLayout, fragmentWeather).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    //api용 Thread
    private class APIthread extends Thread{
        public void run(){
            try{
                api.apiLoad();

                calculator.temperature = api.getTemperature();
                calculator.rainPerHour = api.getRainPerHour();
                calculator.wind = api.getWind();
                calculator.humidity = api.getHumidity();
                calculator.precipitation = api.getPrecipitation();
                calculator.maxTemp = api.getMaxTemp();
                calculator.sky = api.getSky();

                /*
                temperature: 기온
                rainPerHour: 시간당 강수량(mm)
                wind: 풍속(m/s)
                humidity: 습도(%)
                precipitation: 강수형태(없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7))
                maxTemp: 최고기온
                sky: 하늘상태(맑음(1), 구름많음(3), 흐림(4))
                */

                apiFinished = true;
            }catch(Exception e){
                Log.d("api thread error", e.toString());
            }


        }
    }
}