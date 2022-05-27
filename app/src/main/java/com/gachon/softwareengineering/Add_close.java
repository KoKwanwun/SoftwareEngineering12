package com.gachon.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Add_close extends AppCompatActivity {

    ArrayList<String> infoList;
    ListView lvlist;
    DBHelper mDBHelper;
    MyAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDBHelper=new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_close);

        final Spinner itemCategory = (Spinner) findViewById(R.id.itemCategory);
        final Spinner info = (Spinner) findViewById(R.id.info);

        String[] itemList = getResources().getStringArray(R.array.itemCategory);
        infoList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.topInfo)));

        final ArrayAdapter<String> adapter_info = new ArrayAdapter<String>(this,R.layout.spinner_item,infoList);
        adapter_info.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        info.setAdapter(adapter_info);

        final ArrayAdapter<String> adapter_item = new ArrayAdapter<String>(this,R.layout.spinner_item,itemList);
        adapter_item.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter_item);

        itemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 이 메소드에서 position이 몇번째 값이 클릭됐는지 알 수 있음
                // getItemAtPosition(position)을 통해서 해당 값을 받아올 수 있음
                if(i == 2) {
                    infoList.clear();
                    infoList.addAll(Arrays.asList(getResources().getStringArray(R.array.bottomInfo)));
                }else {
                    infoList.clear();
                    infoList.addAll(Arrays.asList(getResources().getStringArray(R.array.topInfo)));
                }
                adapter_info.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner thickness = (Spinner) findViewById(R.id.thickness);

        String[] thick = getResources().getStringArray(R.array.thickness);

        final ArrayAdapter<String> adapter_thickness = new ArrayAdapter<String>(this,R.layout.spinner_item,thick);
        adapter_thickness.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        thickness.setAdapter(adapter_thickness);

        thickness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 이 메소드에서 position이 몇번째 값이 클릭됐는지 알 수 있음
                // getItemAtPosition(position)을 통해서 해당 값을 받아올 수 있음

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button signUpClose = findViewById(R.id.signUpClose);


        signUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 눌렀을때 옷이 등록되어 옷리스트에 정보가 뜨도록 넣어주세요
                //getSelectedItem().toString으로 문자열로 정상적으로 받을 수 있음.
               mDBHelper.InsertCloth(itemCategory.getSelectedItem().toString(),thickness.getSelectedItem().toString(),info.getSelectedItem().toString());
               Toast.makeText(getApplicationContext(), "옷이 옷장에 추가되었습니다!", Toast.LENGTH_SHORT).show();
                //((Fragment_closet) getSupportFragmentManager().findFragmentByTag("fragmentCloset")).displayClothes();
                //System.out.println("데이터 수정이 있을 수 있음으로 디스플레이 메소드 호출");
            }
        });

        Button backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //((Fragment_closet) getSupportFragmentManager().findFragmentByTag("fragmentCloset")).displayClothes();
                    //System.out.println("데이터 수정이 있을 수 있음으로 디스플레이 메소드 호출");

                onBackPressed();

            }

        });
    }
}
