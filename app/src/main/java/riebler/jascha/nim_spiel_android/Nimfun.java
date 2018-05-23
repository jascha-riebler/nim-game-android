package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Nimfun extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Button finishturn_btn;
    private TextView row_messagebox;
    private int difficulty;
    private boolean misere;
    private int startingplayer;
    private CheckBox[][] dots;
    private boolean[][] board;
    private boolean[][] oldboard;
    private int numberofchangedrows;
    private int[] computerboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nim);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        difficulty = mPreferences.getInt("difficulty",0);
        misere = mPreferences.getBoolean("misere",false);
        startingplayer = mPreferences.getInt("startingplayer",0);
        finishturn_btn = (Button) findViewById(R.id.nim_finishturn_btn);
        row_messagebox = (TextView) findViewById(R.id.nim_row_messagebox);
        row_messagebox.setVisibility(View.INVISIBLE);
        computerboard = new int[4];

        dots = new CheckBox[5][8];
        board = new boolean[5][8];
        for(int a=1; a<5; a++){
            for(int b=1; b<8; b++){
                String checkboxid = "nim_game_"+a+"-"+b;
                int resid = Nimfun.this.getResources().getIdentifier(checkboxid,"id",Nimfun.this.getPackageName());
                dots[a][b] = (CheckBox) findViewById(resid);
                board[a][b] = false;
            }
        }
        oldboard = new boolean[5][8];
        for(int a=0; a<5; a++){
            System.arraycopy(board[a],0,oldboard[a],0,board[a].length);
        }


        finishturn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberofchangedrows = 0;
                for(int a=1; a<5; a++){
                    for(int b=1; b<8; b++){
                        if(dots[a][b].isChecked()){
                            board[a][b] = true;
                        }else {
                            board[a][b] = false;
                        }
                    }
                }
                for(int a=1;a<5;a++){
                    boolean c = false;
                    for(int b=1; b<8; b++){

                        if(!board[a][b] == oldboard[a][b]){
                            c = true;
                        }
                    }
                    if(c){
                        numberofchangedrows++;
                    }
                }

                if(numberofchangedrows == 1){
                    for(int a=1; a<5; a++){
                        for(int b=1; b<8; b++){
                            if(dots[a][b].isChecked()){
                                dots[a][b].setVisibility(View.INVISIBLE);
                                dots[a][b].setClickable(false);
                                row_messagebox.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                    getComputerMove();
                }else{
                    row_messagebox.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void getComputerMove(){
        int i = 0;
        for(int a=1;a<5;a++){
            i = 0;
            for(int b=1;b<8;b++){
                if(!board[a][b]){
                    i++;
                }
            }
            computerboard[a-1] = i;
        }

    }






}
