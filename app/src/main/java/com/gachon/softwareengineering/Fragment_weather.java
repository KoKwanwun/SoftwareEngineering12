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
        TextView weather_state = v.findViewById(R.id.weather_state);
        TextView max_Temp = v.findViewById(R.id.max_temp);

        ImageView weatherImage = (ImageView) v.findViewById(R.id.weather_image);

        //하늘상태(맑음(1), 구름많음(3), 흐림(4))
        //강수형태(없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7))

        if(weather.precipitation==1||weather.precipitation==5)
        {
            weatherImage.setImageResource(R.drawable.rain);
            weather_state.setText("비");
        }
        else if(weather.precipitation==3||weather.precipitation==7)
        {
            weatherImage.setImageResource(R.drawable.snow);
            weather_state.setText("눈");
        }
        else if(weather.precipitation==2||weather.precipitation==6)
        {
            weatherImage.setImageResource(R.drawable.rainsnow);
            weather_state.setText("진눈깨비");
        }
        else {
            if(weather.sky==3||weather.sky==4)
            {
                weatherImage.setImageResource(R.drawable.clouds);
                weather_state.setText("흐림");
            }
            else
            {
                weatherImage.setImageResource(R.drawable.sunny);
                weather_state.setText("맑음");
            }
        }

        wind.setText(String.valueOf(weather.wind)+"m/s");
        humidity.setText(String.valueOf(weather.humidity)+"%");
        max_Temp.setText(String.valueOf(weather.maxTemp)+"°C");
        current_temp.setText(String.valueOf(weather.temperature)+"°C");

        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
