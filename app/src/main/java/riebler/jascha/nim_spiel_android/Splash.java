package riebler.jascha.nim_spiel_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
    private TextView TransitionTextView ;
    private ImageView TransitionImageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TransitionImageView = (ImageView) findViewById(R.id.TransitionImageView) ;
        TransitionTextView = (TextView) findViewById(R.id.TransitionTextView) ;
        Animation splash = AnimationUtils.loadAnimation(this,R.anim.transition) ;
        TransitionImageView.startAnimation(splash);
        TransitionTextView.startAnimation(splash);
        final Intent SplashToMain = new Intent(this,MainActivity.class) ;
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    startActivity(SplashToMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}
