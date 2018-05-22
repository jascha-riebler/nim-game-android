package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Nimclassic_menu extends AppCompatActivity {

    private SeekBar seekbar;
    private TextView seekbar_info;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Button button_continue;
    private CheckBox checkBox_misere;
    private Spinner spinner_startingplayer;
    private int difficulty;
    private boolean misere;
    private int startingplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nimclassic_menu);
        seekbar = (SeekBar) findViewById(R.id.nimclassic_menu_seekBar);
        seekbar.setMax(10);
        seekbar_info = (TextView) findViewById(R.id.nimclassic_menu_seekbar_info);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        button_continue = (Button) findViewById(R.id.nimclassic_menu_continuebtn);
        checkBox_misere = (CheckBox) findViewById(R.id.nimclassic_menu_checkbox);
        spinner_startingplayer = (Spinner) findViewById(R.id.nimclassic_menu_spinner);
        difficulty = mPreferences.getInt("difficulty",0);
        misere = mPreferences.getBoolean("misere",false);
        startingplayer = mPreferences.getInt("startingplayer",0);

        seekbar_info.setText(""+difficulty);
        seekbar.setProgress(difficulty);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficulty = progress;
                mEditor.putInt("difficulty",difficulty);
                mEditor.commit();
                seekbar_info.setText(""+difficulty);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}
