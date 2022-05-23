package com.gachon.softwareengineering;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Add_close extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_close);

        final Spinner itemCategory = (Spinner) findViewById(R.id.itemCategory);

        String[] itemList = getResources().getStringArray(R.array.itemCategory);

        final ArrayAdapter<String> adapter_item = new ArrayAdapter<String>(this,R.layout.spinner_item,itemList);
        adapter_item.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter_item);

        itemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 이 메소드에서 position이 몇번째 값이 클릭됐는지 알 수 있음
                // getItemAtPosition(position)을 통해서 해당 값을 받아올 수 있음
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
            }
        });

        Button backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}