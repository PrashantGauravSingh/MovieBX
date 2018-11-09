package com.codecamp.prashant.moviebx.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecamp.prashant.moviebx.R;

public class Splash_Activity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
    public ImageView logo;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.img_thumbnail);
        textView=findViewById(R.id.textView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash_Activity.this,
                        MainActivity.class);
                Splash_Activity.this.startActivity(mainIntent);
                Splash_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.startAnimation(myanim);
        textView.startAnimation(myanim);
    }
}
