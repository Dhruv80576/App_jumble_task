package com.example.sample2;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recycleview_adapter extends RecyclerView.Adapter<Recycleview_adapter.ViewHolder> {

        private ArrayList<ListModel> arrayList;

        // RecyclerView recyclerView;
        public Recycleview_adapter(ArrayList<ListModel> listdata) {
            this.arrayList = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.listview_home, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ListModel myListData = arrayList.get(position);
            holder.clue_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    arrayList.set(position,new ListModel(holder.word_name.getText().toString(),holder.clue_name.getText().toString()));
                }
            });
            holder.word_name.setText(arrayList.get(position).getWord_model());
            holder.clue_name.setText(arrayList.get(position).getLetter_model());
        }


        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public EditText word_name;
            public  EditText clue_name;
            public ViewHolder(View itemView) {
                super(itemView);
                this.word_name=itemView.findViewById(R.id.edittext_word_listview);
                this.clue_name=itemView.findViewById(R.id.edittext_clue_listview);
            }
        }
    }
