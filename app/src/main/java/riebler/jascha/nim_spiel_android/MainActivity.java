package riebler.jascha.nim_spiel_android;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager mainViewPager;
    private LinearLayout mainLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
    }
}
