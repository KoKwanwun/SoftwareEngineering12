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
                System.out.println("옷 삭제 모드로 변경됩니다.");
            }
        });

        lvlist=(ListView)v.findViewById(R.id.listView);
        ct=container.getContext();
        displayClothes();

        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                DBHelper mDBHelper=new DBHelper(ct);
                ArrayList<Clothes> mCloth= mDBHelper.get_cloth_list();
                Clothes sel_clo= mCloth.get(pos);
                int del_id=sel_clo.id;
                //System.out.println("선택된 pos은 "+pos+ "입니다,따라서 입력된 옷의 타입은 "+sel_clo.type);
                //Toast.makeText(getActivity(),String.valueOf(sel_clo.id),Toast.LENGTH_SHORT).show();
                if(del_mode) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("경고").setMessage("이 옷을 삭제하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //옷삭제메소드

                                System.out.println("삭제 모드가 참인 상태이기에 딜리트 메소드 호출");
                                mDBHelper.DeleteCloth(del_id);
                                Toast.makeText(getActivity(), "옷이 옷장에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                del_mode = false;
                               System.out.println("옷이 삭제되었을 수 있기에 디스플레이 메소드 호출 ");
                               displayClothes();
                        }
                    });

                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                             del_mode=false;
                            System.out.println("옷이 삭제되었을 수 있기에 디스플레이 메소드 호출 ");
                            displayClothes();
                        }
                    });

                    builder.show();
                }//삭제모드인 경우
            }
        });
        return v;
    }

    void displayClothes() {
        System.out.println("옷 리스트 가져오기.");
        DBHelper mDBHelper=new DBHelper(ct);
        ArrayList<Clothes> mCloth= mDBHelper.get_cloth_list();
        MyAdapter adapter=new MyAdapter();
        System.out.println("현재 데이터베이스 안에 있는 옷들");
        for (Clothes i:mCloth){
            System.out.println("id: "+i.id+" type: "+i.type+" thickness: "+i.thickness+" info: "+i.info);
            adapter.addInformation(i);
        }
        lvlist.setAdapter(adapter);

    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add_close:
                Intent intent = new Intent(view.getContext(), Add_close.class);
                startActivity(intent);
                break;
            case R.id.del_close:
                //아이디(정수)를 입력받게 하여 삭제, 혹은 리스트뷰에서 옷 항목 하나를 클릭하게 해서 삭제?
        }
    }

}
