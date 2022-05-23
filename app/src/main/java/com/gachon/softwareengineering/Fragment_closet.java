package com.gachon.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_closet extends Fragment implements View.OnClickListener{

    public Fragment_closet(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_closet, container, false);
        Button button=(Button)v.findViewById(R.id.add_close);
        button.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add_close:
                Intent intent = new Intent(view.getContext(), Add_close.class);
                startActivity(intent);
                break;
        }
    }
}
