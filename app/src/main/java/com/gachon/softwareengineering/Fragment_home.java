package com.gachon.softwareengineering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_home extends Fragment implements View.OnClickListener {
    RecyclerView recyclerViewOuter;
    RecyclerView recyclerViewTop;
    RecyclerView recyclerViewBottom;
    ClothesAdapter adapterOuter;
    ClothesAdapter adapterTop;
    ClothesAdapter adapterBottom;
    ClothesSet clothesSet;

    public Fragment_home(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_home, container, false);
        clothesSet = (ClothesSet) getArguments().getSerializable("clothes");

        recyclerViewOuter = (RecyclerView)view.findViewById(R.id.recyclerViewOuter);
        recyclerViewTop = (RecyclerView)view.findViewById(R.id.recyclerViewTop);
        recyclerViewBottom = (RecyclerView)view.findViewById(R.id.recyclerViewBottom);

        recyclerViewOuter.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)) ;
        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)) ;
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)) ;

        adapterOuter = new ClothesAdapter(clothesSet.outer);
        adapterTop = new ClothesAdapter(clothesSet.top);
        adapterBottom = new ClothesAdapter(clothesSet.bottom);

        recyclerViewOuter.setAdapter(adapterOuter);
        recyclerViewTop.setAdapter(adapterTop);
        recyclerViewBottom.setAdapter(adapterBottom);

        SnapHelper helperOuter = new LinearSnapHelper();
        SnapHelper helperTop = new LinearSnapHelper();
        SnapHelper helperBottom = new LinearSnapHelper();
        helperOuter.attachToRecyclerView(recyclerViewOuter);
        helperTop.attachToRecyclerView(recyclerViewTop);
        helperBottom.attachToRecyclerView(recyclerViewBottom);

        return view;
    }



    @Override
    public void onClick(View view) {

    }
}
