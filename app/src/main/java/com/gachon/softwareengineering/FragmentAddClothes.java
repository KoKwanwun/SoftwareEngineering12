package com.gachon.softwareengineering;


import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentAddClothes extends Fragment {

    ArrayList<String> infoList;
    ListView lvlist;
    DBHelper mDBHelper;
    MyAdapter myadapter;
    ImageView IV;
    Uri uri;
    String suri;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_close, container, false);


        mDBHelper=new DBHelper(getContext());
        IV=(ImageView)view.findViewById(R.id.imgV);
        final Spinner itemCategory = (Spinner) view.findViewById(R.id.itemCategory);
        final Spinner info = (Spinner) view.findViewById(R.id.info);

        String[] itemList = getResources().getStringArray(R.array.itemCategory);
        infoList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.topInfo)));

        final ArrayAdapter<String> adapter_info = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,infoList);
        adapter_info.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        info.setAdapter(adapter_info);

        final ArrayAdapter<String> adapter_item = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,itemList);
        adapter_item.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter_item);

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //intent.setAction();
                launcher.launch(intent);
            }
        });
        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                if (result.getResultCode() == RESULT_OK)
                {
                    Intent intent = result.getData();
                    uri = intent.getData();
                    suri=getPath(uri);   //절대경로로 저장
                    IV.setImageURI(uri);

                }
            }
        });

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

        final Spinner thickness = (Spinner) view.findViewById(R.id.thickness);

        String[] thick = getResources().getStringArray(R.array.thickness);

        final ArrayAdapter<String> adapter_thickness = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,thick);
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

        Button signUpClose = view.findViewById(R.id.signUpClose);


        signUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 눌렀을때 옷이 등록되어 옷리스트에 정보가 뜨도록 넣어주세요
                //getSelectedItem().toString으로 문자열로 정상적으로 받을 수 있음.        이미지도 추가.
                mDBHelper.InsertCloth(itemCategory.getSelectedItem().toString(),thickness.getSelectedItem().toString(),info.getSelectedItem().toString(),suri);
                Toast.makeText(getContext(), "옷이 옷장에 추가되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        Button backbtn = view.findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frameLayout, new Fragment_closet()).commitAllowingStateLoss();
            }

        });
        return view;
    }


    public String getPath(Uri u){
        Cursor cursor=getContext().getContentResolver().query(u,null,null,null,null);
        cursor.moveToNext();
        @SuppressLint("Range") String path=cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return path;
    }
}
