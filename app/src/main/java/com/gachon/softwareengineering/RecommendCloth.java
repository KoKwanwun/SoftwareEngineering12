package com.gachon.softwareengineering;

import java.util.ArrayList;

public class RecommendCloth {
    public ArrayList<Clothes> clothes_outer;
    public ArrayList<Clothes> clothes_top;
    public ArrayList<Clothes> clothes_bottom;
    public WindChillTemperature weather;
    public int[] temperature_criteria = {28,23,20,17,12,9,5,-99};
    public int temperature_level;
    //28~   0단계 민소매 반팔 반바지 치마 <- 뭘입어도 더우니 최대한 얇은것들만
    //23~27 1단계 반팔 얇은셔츠 반바지 면바지
    //20~22 2단계 얇은카디건 긴팔티 면바지 청바지
    //17~19 3단계 얇은니트 가디건 얇은자켓 맨투맨 긴팔티 면바지 청바지
    //12~16 4단계 재킷 카디건 야상 맨투맨 니트 스타킹 청바지 면바지
    //9 ~11 5단계 재킷 트렌치코트 야상 맨투맨 니트 스타킹 청바지 면바지
    //5 ~ 8 6단계 코트 히트텍 니트 청바지 레깅스
    //  ~ 4 7단계 패딩 두꺼운코트 목도리 기모 <- 뭘입어도 추우니 최대한 두껍게

    //아우터
    //1 >= n  안입음
    //2 <= n <= 3 얇게
    //4 <= n <= 5 보통
    //6 <= n  두껍게

    //상의
    //0 == n 민소매, 얇고 짧게
    //1 <= n <= 2 얇고 길게/얇고 보통/얇고 짧게/보통 짧게
    //3 <= n <= 5 보통 보통/보통 길게
    //6<= n 두껍고 길게

    //하의
    //0 == n 얇고 짧게
    //1 <= n <= 2 얇고 길게/얇고 보통/얇고 짧게/보통 짧게/보통 보통/보통 길게
    //3 <= n <= 5 보통 보통/보통 길게/두껍고 보통
    //6<= n 두껍고 길게

    //현재 체감온도는 습도만을 취급하기때문에 풍속과 일사량을 포함해서
    //단계를 위아래로 내릴수 있으면 좋을듯
    //일교차가 클떄는 최고온도를 기준으로 입히고 아우터를 추가하는 식으로

    RecommendCloth(){
    }

    RecommendCloth(ArrayList<Clothes> clothes_outer,ArrayList<Clothes> clothes_top,ArrayList<Clothes> clothes_bottom, WindChillTemperature weather){
        this.clothes_outer = clothes_outer;
        this.clothes_top = clothes_top;
        this.clothes_bottom = clothes_bottom;
        this.weather = weather;

        for(int i = 0; i < 8; i++){
            if(temperature_criteria[i]<weather.WCT) {
                temperature_level = i;
                break;
            }
        }
    }

    public void setLevel(){
        for(int i = 0; i < 8; i++){
            if(temperature_criteria[i]<weather.WCT) {
                temperature_level = i;
                break;
            }
        }
    }

    public ArrayList<ClothesSet> calculate(){
        ArrayList<ClothesSet> list = new ArrayList<ClothesSet>();


        return null;
    }
}
