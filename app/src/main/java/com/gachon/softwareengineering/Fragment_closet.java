package com.gachon.softwareengineering;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class Fragment_closet extends Fragment implements View.OnClickListener,MyAdapter.getMode{
    boolean del_mode=false;

    public boolean returnMode(){
        return del_mode;
    }
    public Fragment_closet(){

    }
    ListView lvlist;
    Context ct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.menu_closet, container, false);
        Button button=(Button)v.findViewById(R.id.add_close);
        button.setOnClickListener(this);
        Button button1=(Button)v.findViewById(R.id.del_close);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del_mode=true;
                Toast.makeText(getActivity(),"삭제할 옷을 아래 리스트에서 선택해주세요.",Toast.LENGTH_SHORT).show();
            }
        });

        lvlist=(ListView)v.findViewById(R.id.listView);
        ct=container.getContext();

        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                DBHelper mDBHelper=new DBHelper(ct);
                ArrayList<Clothes> mCloth= mDBHelper.get_cloth_list();
                Clothes sel_clo= mCloth.get(pos);
                int del_id=sel_clo.id;
                if(del_mode) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("경고").setMessage("이 옷을 삭제하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //옷삭제메소드

                                mDBHelper.DeleteCloth(del_id);
                                Toast.makeText(getActivity(), "옷이 옷장에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                del_mode = false;
                               displayClothes();
                        }
                    });

                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                             del_mode=false;
                            displayClothes();
                        }
                    });

                    builder.show();
                }//삭제모드인 경우
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayClothes();
    }

    void displayClothes() {
        DBHelper mDBHelper=new DBHelper(ct);
        ArrayList<Clothes> mCloth= mDBHelper.get_cloth_list();
        MyAdapter adapter=new MyAdapter();
        for (Clothes i:mCloth){
            adapter.addInformation(i);
        }
        lvlist.setAdapter(adapter);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add_close:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frameLayout, new FragmentAddClothes()).commitAllowingStateLoss();
                break;
            case R.id.del_close:
        }
    }

}
