package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Random;

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
    private int[] oldcomputerboard;

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
        oldcomputerboard = new int[4];
        Random rn = new Random();

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

        if(startingplayer==2 && rn.nextBoolean()){
            startingplayer = 1;
        }
        if(startingplayer==1){
            getComputerMove();
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
                    for(int a=0; a<5; a++){
                        System.arraycopy(board[a],0,oldboard[a],0,board[a].length);
                    }
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
        if(computerboard[0]==0 && computerboard[1]==0 && computerboard[2]==0 && computerboard[3]==0){
            mEditor.putString("lastmove","computer");
            mEditor.commit();
            final Intent NimfunToExit = new Intent(this,Nimfun_exit.class);
            startActivity(NimfunToExit);
            return;
        }
        System.arraycopy(computerboard,0,oldcomputerboard,0,computerboard.length);
        computerboard = NimAlgorithmus.getSpielzug(computerboard,misere,difficulty);
        for(int row=0;row<4;row++){
            if(oldcomputerboard[row]!=computerboard[row]){
                int dotschanged = 0;
                for(int dotposition = 7;dotposition>0;dotposition--){
                    if(!board[row+1][dotposition]){
                        dots[row+1][dotposition].setVisibility(View.INVISIBLE);
                        dots[row+1][dotposition].setClickable(false);
                        dots[row+1][dotposition].setChecked(true);
                        board[row+1][dotposition] = true;
                        dotschanged++;
                    }
                    if(dotschanged == oldcomputerboard[row]-computerboard[row]){
                        break;
                    }

                }

            }
        }
        if(computerboard[0]==0 && computerboard[1]==0 && computerboard[2]==0 && computerboard[3]==0){
            mEditor.putString("lastmove","computer");
            mEditor.commit();
            final Intent NimfunToExit = new Intent(this,Nimfun_exit.class);
            startActivity(NimfunToExit);
        }
    }
}
