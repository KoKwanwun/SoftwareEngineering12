package com.gachon.softwareengineering;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_closet extends Fragment implements View.OnClickListener{




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

        lvlist=(ListView)v.findViewById(R.id.listView);
        ct=container.getContext();
        displayClothes();
        //옷을 추가하고 뒤로 가기를 통해 들어올 떈
        return v;
    }

    private void displayClothes() {
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
