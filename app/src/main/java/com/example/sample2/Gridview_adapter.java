package com.example.sample2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Random;

public class Gridview_adapter extends ArrayAdapter<Character> {

    ArrayList<Character> arrayList=new ArrayList<>();
    public Gridview_adapter(@NonNull Context context,ArrayList<Character> arrayList) {
        super(context, 0,arrayList);
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holderview holderview;
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.letter_layout,parent,false);
            holderview=new Holderview(convertView);
            convertView.setTag(holderview);
        }
        else {
            holderview=(Holderview) convertView.getTag();
        }
        holderview.textview.setText(String.valueOf(arrayList.get(position)));
        Random random=new Random();
        holderview.textview.setBackgroundColor(Color.argb(100,random.nextInt(255),random.nextInt(255),random.nextInt(255)));

        return convertView;
    }


    @Override
    public int getCount() {
        return arrayList.size();

    }

    @Nullable
    @Override
    public Character getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }

    private static class Holderview{
        private final TextView textview;
        private final CardView cardView;
        public Holderview(View view){
            textview=view.findViewById(R.id.letters_main);
            cardView=view.findViewById(R.id.cardview_gridview);
        }

    }

}