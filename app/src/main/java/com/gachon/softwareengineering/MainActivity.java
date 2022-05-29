package com.gachon.softwareengineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.Normalizer2;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
    private static final int G_PERMISSION=1004;

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        if (!runtimeCheckPermission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, G_PERMISSION);
        } else {
            // 권한이 있는 경우..
            Log.i("권한 테스트", "권한이 있네요");
        }
    }

    public boolean runtimeCheckPermission(Context context,String...permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case G_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 이미 있는 경우
                    Log.i("권한 테스트", "사용자가 권한을 부여해 줬습니다.");
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("앱 권한 설정");
                    alertDialog.setMessage("설정으로 이동합니다.");
                    alertDialog.setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 이 부분은 설정으로 이동하는 코드이므로 안드로이드 운영체제 버전에 따라 상이할 수 있다.
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                    startActivity(intent);
                                    dialogInterface.cancel();
                                }
                            });

                    alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    alertDialog.show();
                }
        }
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