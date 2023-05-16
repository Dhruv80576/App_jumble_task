package com.example.sample2;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListAdapter extends ArrayAdapter<ListModel> {
    ArrayList<ListModel> arrayList=new ArrayList<>();
    public ListAdapter(@NonNull Context context, ArrayList<ListModel> arrayList) {
        super(context, 0,arrayList);
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListAdapter.HolderView holderview;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listview_home,parent,false);
            holderview=new ListAdapter.HolderView(convertView);
            convertView.setTag(holderview);
        }
        else {
            holderview=(ListAdapter.HolderView) convertView.getTag();
        }
        holderview.word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayList.set(position,new ListModel(holderview.word.getText().toString(),holderview.clue.getText().toString()));
            }
        });
        holderview.clue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayList.set(position,new ListModel(holderview.word.getText().toString(),holderview.clue.getText().toString()));
            }
        });
        holderview.word.setText(arrayList.get(position).getWord_model());
        holderview.clue.setText(arrayList.get(position).getLetter_model());
        return convertView;
    }
    private static class HolderView{
        private final EditText word;
        private final EditText clue;
        public HolderView(View view){
            word =view.findViewById(R.id.edittext_word_listview);
            clue=view.findViewById(R.id.edittext_clue_listview);
        }

    }
}
