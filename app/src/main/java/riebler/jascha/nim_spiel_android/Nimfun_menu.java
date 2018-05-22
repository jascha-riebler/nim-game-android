package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Nimfun_menu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    private String[] spinner_choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nimfun_menu);
        seekbar = (SeekBar) findViewById(R.id.nimfun_menu_seekBar);
        seekbar.setMax(10);
        seekbar_info = (TextView) findViewById(R.id.nimfun_menu_seekbar_info);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        button_continue = (Button) findViewById(R.id.nimfun_menu_continuebtn);
        checkBox_misere = (CheckBox) findViewById(R.id.nimfun_menu_checkbox);
        spinner_startingplayer = (Spinner) findViewById(R.id.nimfun_menu_spinner);
        final Intent NimFunMenuToNimFun = new Intent(this,Nimfun.class);

        difficulty = mPreferences.getInt("difficulty",0);
        misere = mPreferences.getBoolean("misere",false);
        startingplayer = mPreferences.getInt("startingplayer",0);
        spinner_choices = new String[]{"Player","Computer","Random"};

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Nimfun_menu.this,R.layout.spinner_item,spinner_choices);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_startingplayer.setAdapter(adapter);
        spinner_startingplayer.setOnItemSelectedListener(this);
        spinner_startingplayer.setSelection(startingplayer);

        checkBox_misere.setChecked(misere);
        checkBox_misere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEditor.putBoolean("misere",isChecked);
                mEditor.commit();
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NimFunMenuToNimFun);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mEditor.putInt("startingplayer",0);
                mEditor.commit();
                break;
            case 1:
                mEditor.putInt("startingplayer",1);
                mEditor.commit();
                break;
            case 2:
                mEditor.putInt("startingplayer",2);
                mEditor.commit();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}

