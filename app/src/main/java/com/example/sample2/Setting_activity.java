package com.example.sample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Setting_activity extends AppCompatActivity {
    int num_colums;
    int num_rows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences sharedPreferences=getSharedPreferences("Setting",0);
        num_colums=sharedPreferences.getInt("Column",4);
        num_rows=sharedPreferences.getInt("Row",4);
        EditText column=(EditText) findViewById(R.id.editxt_column);
        EditText row=(EditText) findViewById(R.id.editxt_row);
        column.setText(String.valueOf(num_colums));
        row.setText(String.valueOf(num_rows));
        Button apply=(Button) findViewById(R.id.apply_setting_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num_colums= Integer.parseInt(column.getText().toString());
                num_rows= Integer.parseInt(row.getText().toString());
                System.out.println(num_colums+" "+num_rows);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("Column",num_colums);
                editor.putInt("Row",num_rows);
                editor.commit();
                Intent intent=new Intent(Setting_activity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}