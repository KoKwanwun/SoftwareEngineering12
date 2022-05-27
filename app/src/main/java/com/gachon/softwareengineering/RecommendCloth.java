package com.gachon.softwareengineering;

import java.util.ArrayList;
import java.util.Arrays;

public class RecommendCloth {
    public ArrayList<Clothes> clothes_outer;
    public ArrayList<Clothes> clothes_top;
    public ArrayList<Clothes> clothes_bottom;

    public WindChillTemperature weather;

    public int[] temperature_criteria = {28,23,20,17,12,9,5,-99};
    public int max_temperature_level;
    public int current_temperature_level;
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

    public int[] wind_criteria = {10,5,-1};
    public int wind_level;
    //10 ~    2단계
    //5  ~ 9  1단게
    //0  ~ 4  0단계
    //각 단계만큼 내리면 적당할듯
    public boolean has_big_temperature_gap;


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

        setWeatherLevel();
        setTemperatureGapLevel();
    }

    public void setWeatherLevel(){
        boolean max = true;
        boolean cur = true;
        boolean wind = true;

        for(int i = 0; i < 8; i++){
            if(max&&temperature_criteria[i]<weather.WCT) {
                max_temperature_level = i;
                max = false;
            }
            if(cur&&temperature_criteria[i]<weather.temperature) {
                current_temperature_level = i;
                cur = false;
            }
            if(wind&&wind_criteria[i]<weather.wind) {
                wind_level = 2 - i;
                wind = false;
            }
        }
    }

    public void setTemperatureGapLevel(){
        //일교차가 크다 / 레벨 차이를 1로 할지 2로 할지 현재 고민중
        if(max_temperature_level>=current_temperature_level+2)
            has_big_temperature_gap = true;
        else
            has_big_temperature_gap = false;
    }

    public ClothesSet recommend(){
        ArrayList<Clothes> outer_list;
        if(has_big_temperature_gap)//일교차가 크먄 현재 온도 기준으로 아우터 선정
            outer_list = chooseOuter(clothes_outer,current_temperature_level+wind_level);
        else//일교차가 작으면 최고 기온 기준으로 아우터 선정
            outer_list = chooseOuter(clothes_outer,max_temperature_level+wind_level);
        ArrayList<Clothes> top_list = chooseTop(clothes_top,max_temperature_level+wind_level);
        ArrayList<Clothes> bottom_list = chooseBottom(clothes_bottom,max_temperature_level+wind_level);
        //상하의는 둘다 최고기온 기준
        //풍속레벨을 넣어서 전체 레벨 조정
        return new ClothesSet(outer_list,top_list,bottom_list);
    }

    public ArrayList<Clothes> chooseOuter(ArrayList<Clothes> clothes_outer,int level){
        ArrayList<Condition> condition_list = new ArrayList<>();

        if(level <= 1)return null;//이땐 안입음
        else if(2 <= level && level <= 3){
            condition_list.add(new Condition(0,null));
            condition_list.add(new Condition(1,null));
        }
        else if(4 <= level && level <= 5){
            condition_list.add(new Condition(2,null));
            condition_list.add(new Condition(3,null));
        }
        else if(6 <= level ){
            condition_list.add(new Condition(4, null));
        }

        return chooseClothes(clothes_outer,condition_list);
    }

    //0 == n 민소매, 얇고 짧게
    //1 <= n <= 2 얇고 길게/얇고 보통/얇고 짧게/보통 짧게
    //3 <= n <= 5 보통 보통/보통 길게
    //6<= n 두껍고 길게

    public ArrayList<Clothes> chooseTop(ArrayList<Clothes> clothes_top,int level){
        ArrayList<Condition> condition_list = new ArrayList<>();

        if(level==0){
            condition_list.add(new Condition(0, new int[]{0, 1}));
            condition_list.add(new Condition(1, new int[]{0, 1}));
        }
        else if(1 <= level && level <= 2){
            condition_list.add(new Condition(0, new int[]{1, 2}));
            condition_list.add(new Condition(1, new int[]{1, 2}));
            condition_list.add(new Condition(2, new int[]{1, 2}));
        }
        else if(3 <= level && level <= 5){
            condition_list.add(new Condition(2, new int[]{2, 3}));
            condition_list.add(new Condition(3, new int[]{2, 3}));
        }
        else if(6 <= level ){
            condition_list.add(new Condition(4, new int[]{2, 3}));
        }

        return chooseClothes(clothes_top,condition_list);
    }

    //하의
    //0 == n 얇고 짧게
    //1 <= n <= 2 얇고 길게/얇고 보통/얇고 짧게/보통 짧게/보통 보통/보통 길게
    //3 <= n <= 5 보통 보통/보통 길게/두껍고 보통
    //6<= n 두껍고 길게

    public ArrayList<Clothes> chooseBottom(ArrayList<Clothes> clothes_bottom,int level){
        ArrayList<Condition> condition_list = new ArrayList<>();

        if(level==0){
            condition_list.add(new Condition(0, new int[]{0, 1}));
        }
        else if(1 <= level && level <= 2){
            condition_list.add(new Condition(0, new int[]{0, 1, 2, 3}));
            condition_list.add(new Condition(1, new int[]{0, 1, 2, 3}));
            condition_list.add(new Condition(2, new int[]{0, 1}));
        }
        else if(3 <= level && level <= 5){
            condition_list.add(new Condition(2, new int[]{3}));
            condition_list.add(new Condition(3, new int[]{3}));
        }
        else if(6 <= level ){
            condition_list.add(new Condition(3, new int[]{3}));
            condition_list.add(new Condition(4, new int[]{3}));
        }

        return chooseClothes(clothes_bottom,condition_list);
    }

    public ArrayList<Clothes> chooseClothes(ArrayList<Clothes> clothes_list,ArrayList<Condition> condition_list){
        if(condition_list==null)return null;
        ArrayList<Clothes> selected = new ArrayList<>();
        for(Clothes clothes : clothes_list){
            for(Condition condition : condition_list ){
                if(Arrays.asList(condition.thickness).contains(clothes.thickness)){
                    if ((condition.info == null) || (Arrays.asList(condition.info).contains(clothes.info))){
                        selected.add(clothes);
                    }
                }
            }
        }
        return selected;
    }

    public class Condition{
        public int thickness;
        public int[] info;

        Condition(int thickness, int[] info){
            this.thickness = thickness;
            this.info = info;
        }
    }
}
