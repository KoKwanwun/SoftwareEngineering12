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
import android.widget.ListView;
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

    //DB
    protected ArrayList<Clothes> m_Cloth_list;
    protected DBHelper mDBHelper;
    protected ListView cloth_list;
    protected static MyAdapter myAdapter;

    WeatherAPI api;
    boolean apiFinished = false;
    Button closet, home, weather;

    //불러온 날씨 데이터
    private float temperature = 0.0F;
    private float rainPerHour = 0.0F;
    private float wind = 0.0F;
    private int humidity = 0;
    private int precipitation = 0;
    private float maxTemp = 0.0F;

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
        maxTemp: 최고기온
         */
        //setInit();
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

                temperature = api.getTemperature();
                rainPerHour = api.getRainPerHour();
                wind = api.getWind();
                humidity = api.getHumidity();
                precipitation = api.getPrecipitation();
                maxTemp = api.getMaxTemp();

                apiFinished = true;
            }catch(Exception e){
                Log.d("api thread error", e.toString());
            }


        }
    }
}