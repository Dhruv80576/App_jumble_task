package com.example.sample2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz_Screen extends AppCompatActivity {
    String clue;
    String word;
    String result="";
    ArrayList<Character> letters = new ArrayList<>();
    Button check;
    Button reset;
    static int life;
    ImageView life1;
    ImageView life2;
    ImageView life3;
    Intent intent;
    int click_gridview=0;
    EditText answr_edittext;
    GridView gridView;
    boolean[] gridview_touch;
    int column_size;
    int row_size;
    int grid_lenght;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);
        intent= getIntent();
        clue = intent.getStringExtra("Clue");
        word = intent.getStringExtra("Word");
        SharedPreferences sharedPreferences1=getSharedPreferences("Setting",0);
        column_size=sharedPreferences1.getInt("Column",4);
        row_size=sharedPreferences1.getInt("Row",4);
        grid_lenght=column_size*row_size;
        gridview_touch=new boolean[grid_lenght];
        gridView = (GridView) findViewById(R.id.gridview_letter);
        gridView.setNumColumns(column_size);
        ImageView imageView = (ImageView) findViewById(R.id.hint_information_button);
        check = (Button) findViewById(R.id.check_button);
        reset=(Button)findViewById(R.id.reset_button);
        answr_edittext=(EditText)findViewById(R.id.Edittext_main_answr);
        life1=(ImageView) findViewById(R.id.life1_heart);
        life2=(ImageView) findViewById(R.id.life2_heart);
        life3=(ImageView) findViewById(R.id.life3_heart);
        life=3;
        answr_edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         vibrate();

                                         for(int i=0;i<grid_lenght;i++){
                                             gridview_touch[i]=false;
                                         }
                                         if(result.charAt(result.length()-1)!='_'){
                                             if(word.equals(result)){
                                                 Toast.makeText(Quiz_Screen.this, "Correct", Toast.LENGTH_SHORT).show();
                                                 show_result(life);
                                             }
                                             else{
                                                 Toast.makeText(Quiz_Screen.this, "Wrong", Toast.LENGTH_SHORT).show();
                                                 click_gridview=0;
                                                 result="";
                                                 for(int i=0;i<word.length();i++){
                                                     result+="_";
                                                     answr_edittext.setText(result);
                                                 }
                                                 letters=reshuffle_grid(letters);
                                                 Gridview_adapter gridview_adapter=new Gridview_adapter(Quiz_Screen.this,letters);
                                                 gridView.setAdapter(gridview_adapter);
                                                 reduce_star();
                                             }
                                         }
                                     }
                                 }
        );
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                result="";
                for(int i=0;i<grid_lenght;i++){
                    gridview_touch[i]=false;
                }
                for(int i=0;i<word.length();i++){
                    click_gridview=0;
                    result+="_";
                    answr_edittext.setText(result);
                }
                Gridview_adapter gridview_adapter=new Gridview_adapter(Quiz_Screen.this,letters);
                gridView.setAdapter(gridview_adapter);
            }
        });
        for(int i=0;i<word.length();i++){
            result+="_";
        }
        answr_edittext.setText(result);
        for (int i = 0; i < word.length(); i++) {
            letters.add(word.charAt(i));
        }
        while (letters.size() < grid_lenght) {
            int succs = 1;
            char temp = generate_letter();
            for (int j = 0; j < letters.size(); j++) {
                if (temp == letters.get(j)) {
                    succs = 0;
                    break;
                } else {
                    succs = 1;
                }
            }
            if (succs == 1) {
                letters.add(temp);
            }
        }
        letters=reshuffle_grid(letters);
        Gridview_adapter gridview_adapter=new Gridview_adapter(Quiz_Screen.this,letters);
        gridView.setAdapter(gridview_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(gridview_touch[position]==false){
                    if (result.charAt(result.length()-1)=='_'){
                        view=gridView.getChildAt(position);
                        view.setBackgroundColor(Color.TRANSPARENT);
                        vibrate();
                        click_gridview+=1;
                        result=result.substring(0,click_gridview-1)+letters.get(position)+result.substring(click_gridview);
                        answr_edittext.setText(result);
                        gridview_touch[position]=true;
                    }
                }

            }
        });
    }

    private void vibrate() {
        Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        show_dialog();
    }

    public void show_dialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_clue, viewGroup, false);
        TextView textView = dialogView.findViewById(R.id.message_textview_clue);
        textView.setText(clue);
        Button okay = dialogView.findViewById(R.id.dialog_okay);
        alert.setView(dialogView);
        AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    public static char generate_letter() {
        char lttr = ' ';
        Random r = new Random();
        lttr = (char) (r.nextInt(26) + 'A');
        return lttr;
    }
    public void reduce_star(){
        if(life==1){
            life--;
            show_result(life);
            life1.setImageResource(R.drawable.heart_vector_grey);
        }
        else if(life==2){
            life--;
            life2.setImageResource(R.drawable.heart_vector_grey);
        }
        else if(life==3){
            life--;
            life3.setImageResource(R.drawable.heart_vector_grey);
        }
    }
    private void show_result(int life) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        ViewGroup viewGroup=findViewById(android.R.id.content);
        View view=LayoutInflater.from(this).inflate(R.layout.dialog_result,viewGroup,false);
        TextView score=view.findViewById(R.id.score_textview_dialog);
        score.setText("Your Score:"+100*life);
        Button home=view.findViewById(R.id.result_home);
        Button play_again=view.findViewById(R.id.result_play_again);
        alert.setView(view);
        AlertDialog alertDialog=alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        SharedPreferences score_preference = getSharedPreferences("Score", Context.MODE_PRIVATE);
        int homeScore = score_preference.getInt("high_score", 0);
        if(homeScore<100*life){
            SharedPreferences.Editor editor = score_preference.edit();
            editor.putInt("high_score", 100*life);
            editor.commit();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Quiz_Screen.this,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                letters=reshuffle_grid(letters);
                click_gridview=0;
                result="";
                for(int i=0;i<word.length();i++){
                    result+="_";
                    answr_edittext.setText(result);
                }
                life_renew();
            }
        });
    }
    public void life_renew(){
        Gridview_adapter gridview_adapter=new Gridview_adapter(Quiz_Screen.this,letters);
        gridView.setAdapter(gridview_adapter);
        life=3;
        life1.setImageResource(R.drawable.heart_life);
        life2.setImageResource(R.drawable.heart_life);
        life3.setImageResource(R.drawable.heart_life);
    }
    public ArrayList<Character> reshuffle_grid(ArrayList<Character> arrayList){
        ArrayList<Character> new_list=new ArrayList<>();
        while(new_list.size()!=arrayList.size()){
            Random random=new Random();
            int temp=random.nextInt(grid_lenght);
            if(arrayList.get(temp)!='0'){
                new_list.add(arrayList.get(temp));
                arrayList.set(temp,'0');
            }
        }
        return new_list;
    }
}