package com.gachon.softwareengineering;

public class WindChillTemperature {
    private float temperature = 0.0F;
    private float rainPerHour = 0.0F;
    private float wind = 0.0F;
    private int humidity = 0;
    private int precipitation = 0;

    WindChillTemperature(){

    }

    WindChillTemperature(float temperature, float rainPerHour, float wind, int humidity, int precipitation){
        this.temperature = temperature;
        this.rainPerHour = rainPerHour;
        this.wind = wind;
        this.humidity = humidity;
        this.precipitation = precipitation;
    }

    //관측 자료를 토대로한 체감온도 계산
    //공식 출처 기상청
    //https://data.kma.go.kr/climate/windChill/selectWindChillChart.do?pgmNo=111
    public double calculate(){
        float Ta = temperature;
        float RH = humidity;
        double Tw = Ta*Math.atan(0.151977*Math.pow((RH+8.313659),((double) 1/2))) + Math.atan(Ta+RH) - Math.atan(RH-1.67633) + 0.00391838*Math.pow(RH,((double)3/2))*Math.atan(0.023101*RH) - 4.686035;
        return -0.2442 + (0.55399*Tw) + (0.45535*Ta) - (0.0022*Math.pow(Tw,2)) + (0.00278*Tw*Ta) + 3.5;
    }
}