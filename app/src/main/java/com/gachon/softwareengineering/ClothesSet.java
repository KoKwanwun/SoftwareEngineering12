package com.gachon.softwareengineering;

import java.io.Serializable;
import java.util.ArrayList;

//선택된 아우터 상의 하의 리스트들
public class ClothesSet implements Serializable {
    public ArrayList<Clothes> outer;
    public ArrayList<Clothes> top;
    public ArrayList<Clothes> bottom;

    ClothesSet(){
        outer = new ArrayList<>();
        top = new ArrayList<>();
        bottom = new ArrayList<>();
    }

    ClothesSet(ArrayList<Clothes> outer,ArrayList<Clothes> top,ArrayList<Clothes> bottom){
        this.outer = outer;
        this.top = top;
        this.bottom = bottom;
    }
}
