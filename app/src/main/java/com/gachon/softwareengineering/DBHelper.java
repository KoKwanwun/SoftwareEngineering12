package com.gachon.softwareengineering;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION =1;
    private static final String DB_NAME="mycloset.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //db가 생성되었을 때
        //데이터베이스가 생성이 될 떄 호출, 구조는 데이터베이스 -> 테이블 -> 컬럼 ->값
        db.execSQL("CREATE TABLE IF NOT EXISTS Closet (id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER NOT NULL, thickness INTEGER NOT NULL, info INTEGER NOT NULL, img_path STRING DEFAULT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Select 문 (내 옷 목록들을 조회)
    public ArrayList<Clothes> get_cloth_list(){
        ArrayList<Clothes> my_cloth_list = new ArrayList<>();

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery( "SELECT * FROM Closet ORDER BY id ", null);
        //Cursor 객체는 쿼리에 의하여 생성된 행들을 가리킨다.
        if(cursor.getCount()!=0){
            //조회된 데이터가 있을 때
            while (cursor.moveToNext()){
                @SuppressLint("Range") int id= cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int type= cursor.getInt(cursor.getColumnIndex("type"));
                @SuppressLint("Range") int thickness= cursor.getInt(cursor.getColumnIndex("thickness"));
                @SuppressLint("Range") int info= cursor.getInt(cursor.getColumnIndex("info"));
                @SuppressLint("Range") String img_path= cursor.getString(cursor.getColumnIndex("img_path"));

                Clothes MyCloth=new Clothes();
                MyCloth.id=id;
                MyCloth.type=type;
                MyCloth.thickness=thickness;
                MyCloth.info=info;
                MyCloth.image=img_path;
                my_cloth_list.add(MyCloth);
            }
        }
        cursor.close();

        return my_cloth_list;
    }

    //insert 문 (임시로 상의 하의 같은 타입과 옷 두께, 정보만 넣는 걸로)
    public void InsertCloth(int _type,int _thickness, int _info){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO Closet (type,thickness,color).VALUES('"+_type+"','"+_thickness+"','"+_info+"');");
        //뒤에서 두번 째 세미콜론은 SQL문의 세미콜론
    }

    //Delete 문( 기존 데베 안에 있는 정보를 제거)
    public void DeleteCloth(int _id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM Closet WHERE id='"+_id+"'");
    }
}