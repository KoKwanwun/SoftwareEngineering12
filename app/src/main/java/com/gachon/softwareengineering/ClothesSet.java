package com.gachon.softwareengineering;
//아우터 상의 하의 한세트 -> 추천 화면 하나 단위
public class ClothesSet {
    public Clothes outer;
    public Clothes top;
    public Clothes bottom;

    ClothesSet(){
    }

    ClothesSet(Clothes outer,Clothes top,Clothes bottom){
        this.outer = outer;
        this.top = top;
        this.bottom = bottom;
    }
}
