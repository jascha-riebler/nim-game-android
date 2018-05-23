package riebler.jascha.nim_spiel_android;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Nimclassic_exit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nimclassic_exit);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed(){}
}
