package com.gachon.softwareengineering;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder> {
    private ArrayList<Clothes> list;

    public ClothesAdapter(ArrayList<Clothes> list) {
        if(list==null)this.list = new ArrayList<>();
        else {
            this.list = list;
            this.list.add(new Clothes("","","",""));
            this.list.add(0,new Clothes("","","",""));
        }
    }

    @Override
    public ClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ClothesViewHolder holder, int position) {
        Clothes clothes = list.get(position);
        String filename= clothes.image;
        if(filename.compareTo("null")==0){//이미지 없으면 출력할 이미지 설정
            holder.imageView.setImageBitmap(BitmapFactory.decodeResource(holder.imageView.getResources(),R.drawable.clothes));
        }else {
            File file = new File(filename);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(Clothes data) {
        list.add(data);
    }

    // 아이템 뷰를 저장하는 클래스
    public class ClothesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ClothesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.clothesImage);
        }
    }
}
