package com.gachon.softwareengineering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<Clothes> clist=new ArrayList<Clothes>();


    @Override
    public int getCount() {
        return clist.size();
    }

    @Override
    public Object getItem(int i) {
        return clist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return clist.get(i).id;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        final Context context= viewgroup.getContext();
        //리스트뷰에 옷이 인플레이트 되어있는지 확인
        //없다면 아래 if문으로 옷 레이아웃을 인플레이트,
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cloth_list,viewgroup,false);
        }


        ImageView iv=(ImageView)view.findViewById(R.id.TempImgV);
        TextView tv_type=(TextView) view.findViewById(R.id.tv_type);
        TextView tv_thick=(TextView) view.findViewById(R.id.tv_thick);
        TextView tv_info=(TextView) view.findViewById(R.id.tv_info);


        Clothes now_cloth=clist.get(position);
        tv_type.setText(now_cloth.type);
        tv_thick.setText(now_cloth.thickness);
        tv_info.setText(now_cloth.info);
        return view;
    }

    public void addInformation(Clothes c){
        clist.add(c);
    }
}
