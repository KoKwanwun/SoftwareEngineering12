package com.gachon.softwareengineering;

//DB에서 옷을 불러올 형식
public class Clothes {
    public int type;// 0->아우터 1->상의 2->하의
    public int thickness;//0->매우두꺼움 ~ 4->매우얇음
    public int info;//기장 0->가장짧은 ~ 3->가장
    public String image;//이미지 저장할 발법을 모르겠음 주소나 경로저장이면 String으로 될것으로 추정

    Clothes(){
    }

    Clothes(int type, int thickness, int info, String image){
        this.type = type;
        this.thickness = thickness;
        this.info = info;
        this. image = image;
    }
}
