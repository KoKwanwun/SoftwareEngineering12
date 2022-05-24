package com.gachon.softwareengineering;

import java.util.ArrayList;

public class RecommandCloth {
    public ArrayList<Clothes> clothes;
    public WindChillTemperature weather;

    RecommandCloth(){
    }

    RecommandCloth(ArrayList<Clothes> clothes, WindChillTemperature weather){
        this.clothes = clothes;
        this.weather = weather;
    }

    public ArrayList<ClothesSet> calculate(){
        return null;
    }
}
