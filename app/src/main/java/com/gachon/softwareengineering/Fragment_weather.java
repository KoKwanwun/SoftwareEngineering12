package com.gachon.softwareengineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//이미지 관련은 없음
public class Fragment_weather extends Fragment implements View.OnClickListener {
    Bundle bundle;
    WindChillTemperature weather;

    ImageView weatherImage;
    TextView weatherState;

    public Fragment_weather(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_weather, container, false);
        //날씨데이터 메인액티비티로부터 수신
        bundle = getArguments();
        weather = (WindChillTemperature) bundle.getSerializable("weather");

        TextView current_temp = v.findViewById(R.id.current_temp);
        TextView wind = v.findViewById(R.id.wind);
        TextView humidity = v.findViewById(R.id.humidity);
        TextView max_Temp = v.findViewById(R.id.max_temp);

        weatherImage =  v.findViewById(R.id.weather_image);
        weatherState = v.findViewById(R.id.weather_state);

        //하늘상태(맑음(1), 구름많음(3), 흐림(4))
        //강수형태(없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7))
        int pre = weather.precipitation;

        if(pre==1 || pre==5)
            weatherSet(R.drawable.rain, "비");
        else if(pre==3 || pre==7)
            weatherSet(R.drawable.snow, "눈");
        else if(pre==2 || pre==6)
            weatherSet(R.drawable.rainsnow, "진눈깨비");
        else {
            if(weather.sky==3 || weather.sky==4)
                weatherSet(R.drawable.clouds, "흐림");
            else
                weatherSet(R.drawable.sunny, "맑음");
        }

        wind.setText(String.valueOf(weather.wind)+"m/s");
        humidity.setText(String.valueOf(weather.humidity)+"%");
        max_Temp.setText(String.valueOf(weather.maxTemp)+"°C");
        current_temp.setText(String.valueOf(weather.temperature)+"°C");

        return v;
    }

    private void weatherSet(int drawable, String weatherTxt){
        weatherImage.setImageResource(drawable);
        weatherState.setText(weatherTxt);
    }

    @Override
    public void onClick(View view) {

    }
}
