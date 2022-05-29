package com.gachon.softwareengineering;

import java.io.Serializable;

public class WindChillTemperature implements Serializable{
    public float temperature = 0.0F;
    public float maxTemp = 0.0F;
    public float rainPerHour = 0.0F;
    public float wind = 0.0F;
    public int humidity = 0;
    public int precipitation = 0;
    public int sky = 0;
    public double WCT = 0;


    WindChillTemperature(){

    }

    WindChillTemperature(float temperature, float rainPerHour, float wind, int humidity, int precipitation){
        this.temperature = temperature;
        this.rainPerHour = rainPerHour;
        this.wind = wind;
        this.humidity = humidity;
        this.precipitation = precipitation;
    }

    //예보 자료를 토대로한 체감온도 계산/최고기온기준
    //공식 출처 기상청
    //https://data.kma.go.kr/climate/windChill/selectWindChillChart.do?pgmNo=111
    public double calculate(){
        float Ta = maxTemp;
        float RH = humidity;
        double Tw = Ta*Math.atan(0.151977*Math.pow((RH+8.313659),((double) 1/2))) + Math.atan(Ta+RH) - Math.atan(RH-1.67633) + 0.00391838*Math.pow(RH,((double)3/2))*Math.atan(0.023101*RH) - 4.686035;
        this.WCT =  -0.2442 + (0.55399*Tw) + (0.45535*Ta) - (0.0022*Math.pow(Tw,2)) + (0.00278*Tw*Ta) + 3.5;
        return WCT;
    }
}
