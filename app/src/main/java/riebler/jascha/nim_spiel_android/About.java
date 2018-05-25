package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class About extends AppCompatActivity {
    private CheckBox change_language;
    private TextView about_info;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about_info = (TextView) findViewById(R.id.about_info);
        change_language = (CheckBox) findViewById(R.id.change_language);
        mPreferences = getSharedPreferences("riebler.jascha.nim_spiel_android.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        language = mPreferences.getString("language","english");
        if(language.equals("english")){
            change_language.setChecked(true);
            about_info.setText(R.string.about_english);
        } else{
            change_language.setChecked(false);
            about_info.setText(R.string.about_german);
        }
        change_language.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    about_info.setText(R.string.about_english);
                }else{
                    about_info.setText(R.string.about_german);
                }
            }
        });
    }
}
