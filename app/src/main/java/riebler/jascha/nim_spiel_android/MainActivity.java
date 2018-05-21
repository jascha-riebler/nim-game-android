package riebler.jascha.nim_spiel_android;

import android.content.Intent;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mainViewPager;
    private LinearLayout mainLinearLayout;
    private TextView[] mainDots;
    private SliderAdapter sliderAdapter;
    private Button mainNextButton;
    private Button mainBackButton;
    private int mainCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        mainNextButton = (Button) findViewById(R.id.nextBtn);
        mainBackButton = (Button) findViewById(R.id.backBtn);
        sliderAdapter = new SliderAdapter(MainActivity.this);
        mainViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mainViewPager.addOnPageChangeListener(viewListener);
        mainNextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                mainViewPager.setCurrentItem(mainCurrentPage + 1);
            }
        });
        mainBackButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                mainViewPager.setCurrentItem(mainCurrentPage - 1);
            }
        });

    }

    public void SelectionBtn(){
        final Intent MainToNimClassic = new Intent(this,Nimclassic_menu.class);
        final Intent MainToNimFun = new Intent(this,Nimfun_menu.class);
        final Intent MainToAbout = new Intent(this,About.class);
        if(mainCurrentPage == 0){
            startActivity(MainToNimClassic);
        }else if(mainCurrentPage == 1){
            startActivity(MainToNimFun);
        }else{
            startActivity(MainToAbout);
        }
    }

    public void addDotsIndicator(int position){
        mainDots = new TextView[3];
        mainLinearLayout.removeAllViews();
        for(int i = 0;i < mainDots.length; i++){
            mainDots[i] = new TextView(this);
            mainDots[i].setText(Html.fromHtml("&#8226;"));
            mainDots[i].setTextSize(35);
            mainDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mainLinearLayout.addView(mainDots[i]);
        }
        if(mainDots.length > 0){
            mainDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
        public void onPageScrolled(int i, float v, int i1){

        }
        public void onPageSelected(int i){
            addDotsIndicator(i);
            mainCurrentPage = i;
            if (i == 0){
                mainNextButton.setEnabled(true);
                mainBackButton.setEnabled(false);
                mainBackButton.setVisibility(View.INVISIBLE);
                mainNextButton.setVisibility(View.VISIBLE);
                mainNextButton.setText("Next");
                mainBackButton.setText("");
            } else if(i == mainDots.length - 1){
                mainNextButton.setEnabled(true);
                mainBackButton.setEnabled(true);
                mainBackButton.setVisibility(View.VISIBLE);
                mainNextButton.setVisibility(View.INVISIBLE);
                mainNextButton.setText("");
                mainBackButton.setText("Back");
            } else {
                mainNextButton.setEnabled(true);
                mainBackButton.setEnabled(true);
                mainBackButton.setVisibility(View.VISIBLE);
                mainNextButton.setVisibility(View.VISIBLE);
                mainNextButton.setText("Next");
                mainBackButton.setText("Back");
            }
        }
        public void onPageScrollStateChanged(int i){

        }

    };
}
