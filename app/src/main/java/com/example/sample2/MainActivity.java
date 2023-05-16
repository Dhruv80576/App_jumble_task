package com.example.sample2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String word;
    String clue;
    int homeScore = 0;
    int column_size;
    int row_size;
    ArrayList<ListModel> arrayList = new ArrayList<>();
    RecyclerView listView;
    ListAdapter listAdapter;

    ArrayList<ListModel> main_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("high_score")) {
            homeScore = sharedPreferences.getInt("high_score", 0);
        }
        TextView textView = (TextView) findViewById(R.id.highest_score_textview);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout_high);
        if (homeScore == 0) {
            linearLayout1.setVisibility(View.GONE);
        } else if (homeScore != 0) {
            linearLayout1.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(homeScore));
        }
        Button start = (Button) findViewById(R.id.start_home);
        Button add = (Button) findViewById(R.id.add_home);
        EditText word_box = (EditText) findViewById(R.id.edittext_word);
        EditText clue_box = (EditText) findViewById(R.id.edittext_clue);
        ImageView setting = (ImageView) findViewById(R.id.setting_main);
        SharedPreferences sharedPreferences1 = getSharedPreferences("Setting", 0);
        column_size = sharedPreferences1.getInt("Column", 4);
        row_size = sharedPreferences1.getInt("Row", 4);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Setting_activity.class);
                startActivity(intent1);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                word = word_box.getText().toString();
                clue = clue_box.getText().toString();
                if (word.isEmpty() && clue.isEmpty()) {
                    word_box.setError("Word field cannot be null");
                    clue_box.setError("Clue field cannot be null");
                } else if (word.isEmpty()) {

                    word_box.setError("Word field cannot be null");
                } else if (clue.isEmpty()) {
                    clue_box.setError("Clue field cannot be null");
                } else {
                    for (int i = 0; i < arrayList.size() + 1; i++) {
                        if (i == 0) {
                            main_list.add(0, new ListModel(word, clue));
                        } else {
                            main_list.add(i, arrayList.get(i - 1));
                        }
                        System.out.println(main_list.get(i).getWord_model() + "\n\n\n\n");

                    }
                    if (word.length() > column_size * row_size) {
                        Toast.makeText(MainActivity.this, "Reduce length of word", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, Quiz_Screen.class);
                        intent.putExtra("Word", word);
                        intent.putExtra("Clue", clue);
                        intent.putExtra("size", main_list.size());
                        for (int i = 0; i < main_list.size(); i++) {
                            intent.putExtra("Word" + i + 1, main_list.get(i).getWord_model());
                            intent.putExtra("Clue" + i + 1, main_list.get(i).getLetter_model());

                        }
                        startActivity(intent);
                    }
                }
            }
        });

        listView = (RecyclerView) findViewById(R.id.listview_main);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setNestedScrollingEnabled(false);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(new ListModel("", ""));
                listAdapter = new ListAdapter(MainActivity.this, arrayList);
                Recycleview_adapter adapter=new Recycleview_adapter(arrayList);
                listView.setAdapter(adapter);
            }
        });

    }
}
