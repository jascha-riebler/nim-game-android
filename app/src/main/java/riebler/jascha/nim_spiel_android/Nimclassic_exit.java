package riebler.jascha.nim_spiel_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Nimclassic_exit extends AppCompatActivity {

    private TextView result;
    private TextView info1;
    private TextView info2;
    private Button again_btn;
    private Button menu_btn;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private int difficulty;
    private boolean misere;
    private String lastmove;
    private String recentscore;
    private String highscore_classic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nimfun_exit);
        result = (TextView) findViewById(R.id.nimfun_exit_result);
        info1 = (TextView) findViewById(R.id.nimfun_exit_info1);
        info2 = (TextView) findViewById(R.id.nimfun_exit_info2);
        again_btn = (Button) findViewById(R.id.nimfun_exit_again_btn);
        menu_btn = (Button) findViewById(R.id.nimfun_exit_menu_btn);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        difficulty = mPreferences.getInt("difficulty",1);
        misere = mPreferences.getBoolean("misere",false);
        lastmove = mPreferences.getString("lastmove","-");
        recentscore = mPreferences.getString("recentscore","-");
        highscore_classic = mPreferences.getString("highscore_classic","-");
        final Intent ExitToNim = new Intent(this,Nimclassic.class);
        final Intent ExitToMain = new Intent(this,MainActivity.class);

        if((lastmove.equals("player")&&!misere)||(lastmove.equals("computer")&&misere)){
            if(highscore_classic.equals("-")) {
                mEditor.putString("highscore_classic", recentscore+" (Difficulty: "+difficulty+")");
            }else{
                int minutes = Integer.parseInt(recentscore.substring(0,recentscore.indexOf(":")));
                int seconds = Integer.parseInt(recentscore.substring(recentscore.indexOf(":")+1,recentscore.length()));
                int newscore = minutes*60 + seconds;
                minutes = Integer.parseInt(highscore_classic.substring(0,highscore_classic.indexOf(":")));
                seconds = Integer.parseInt(highscore_classic.substring(highscore_classic.indexOf(":")+1,highscore_classic.indexOf("(")-1));
                int oldscore = minutes*60 + seconds;
                if(newscore<oldscore){
                    mEditor.putString("highscore_classic", recentscore+" (Difficulty: "+difficulty+")");
                }else if(oldscore==newscore && difficulty > Integer.parseInt(""+highscore_classic.charAt(highscore_classic.indexOf("y")+3))){
                    mEditor.putString("highscore_classic", recentscore+" (Difficulty: "+difficulty+")");
                }
            }
            mEditor.commit();
            result.setText("YOU WON");
            info1.setText("Your Score: \n"+recentscore+ " (Difficulty: "+difficulty+")");
            highscore_classic = mPreferences.getString("highscore_classic","-");
            info2.setText("Your Highscore: \n"+highscore_classic);
        }else{
            result.setText("YOU LOST");
            info1.setText("Need some help ? Take a look into \"ABOUT\" !");
            info2.setText("Your Highscore: \n"+highscore_classic);
        }
        again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExitToNim);
            }
        });
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExitToMain);
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed(){}
}
