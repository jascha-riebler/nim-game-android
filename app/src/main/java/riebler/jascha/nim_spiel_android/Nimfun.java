package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Nimfun extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Button finishturn_btn;
    private TextView row_messagebox;
    private TextView computers_turn;
    private TextView stopwatch_textview;
    private int difficulty;
    private boolean misere;
    private int startingplayer;
    private CheckBox[][] dots;
    private boolean[][] board;
    private boolean[][] oldboard;
    private int numberofchangedrows;
    private int[] computerboard;
    private int[] oldcomputerboard;
    private long stopwatch_starttime;
    private Handler stopwatch_handler;
    private Runnable stopwatch_runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nim);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        difficulty = mPreferences.getInt("difficulty",1);
        misere = mPreferences.getBoolean("misere",false);
        startingplayer = mPreferences.getInt("startingplayer",0);
        finishturn_btn = (Button) findViewById(R.id.nim_finishturn_btn);
        stopwatch_textview = (TextView) findViewById(R.id.nim_stopwatch_textview);
        row_messagebox = (TextView) findViewById(R.id.nim_row_messagebox);
        computers_turn = (TextView) findViewById(R.id.nim_computers_turn);
        row_messagebox.setVisibility(View.INVISIBLE);
        computers_turn.setVisibility(View.INVISIBLE);
        computerboard = new int[4];
        oldcomputerboard = new int[4];
        Random rn = new Random();
        stopwatch_starttime = 0;
        stopwatch_handler = new Handler();
        stopwatch_runnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - stopwatch_starttime;
                int seconds = (int) (millis/1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                stopwatch_textview.setText(String.format("%d:%02d",minutes,seconds));
                stopwatch_handler.postDelayed(this, 500);
            }
        };
        stopwatch_starttime = System.currentTimeMillis();
        stopwatch_handler.postDelayed(stopwatch_runnable, 0);
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
            for(int a=0; a<5; a++){
                System.arraycopy(board[a],0,oldboard[a],0,board[a].length);
            }
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
        finishturn_btn.setClickable(false);
        finishturn_btn.setVisibility(View.INVISIBLE);
        computers_turn.setVisibility(View.VISIBLE);
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
        CheckBox[] computerchangeddots = new CheckBox[1];
        for(int row=0;row<4;row++){
            if(oldcomputerboard[row]!=computerboard[row]){
                computerchangeddots = new CheckBox[oldcomputerboard[row]-computerboard[row]];
                int dotschanged = 0;
                final int rowchanged = row;
                for(int dotposition = 7;dotposition>0;dotposition--){
                    if(!board[row+1][dotposition]){
                        computerchangeddots[dotschanged] = dots[row+1][dotposition];
                        dots[row+1][dotposition].setClickable(false);
                        dots[row+1][dotposition].setChecked(true);
                        board[rowchanged+1][dotposition] = true;
                        dotschanged++;
                    }
                    if(dotschanged == oldcomputerboard[row]-computerboard[row]){
                        break;
                    }

                }
            }
        }

        final CheckBox[] computerchangeddots2 = computerchangeddots.clone();
        final Intent NimfunToExit = new Intent(this,Nimfun_exit.class);

        for(int a=1; a<5; a++){
            for(int b=1; b<8; b++){
                if(!board[a][b]){
                    dots[a][b].setClickable(false);
                }
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int k=0;k<computerchangeddots2.length;k++){
                    computerchangeddots2[k].setVisibility(View.INVISIBLE);
                }
                if(computerboard[0]==0 && computerboard[1]==0 && computerboard[2]==0 && computerboard[3]==0){
                    mEditor.putString("lastmove","computer");
                    mEditor.commit();
                    startActivity(NimfunToExit);
                }
                for(int a=1; a<5; a++){
                    for(int b=1; b<8; b++){
                        if(!board[a][b]){
                            dots[a][b].setClickable(true);
                        }
                    }
                }
                finishturn_btn.setClickable(true);
                finishturn_btn.setVisibility(View.VISIBLE);
                computers_turn.setVisibility(View.INVISIBLE);
            }

        },2000);







    }
}
