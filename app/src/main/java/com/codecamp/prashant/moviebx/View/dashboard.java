package com.codecamp.prashant.moviebx.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.codecamp.prashant.moviebx.R;

public class dashboard extends AppCompatActivity {

    GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridLayout=findViewById(R.id.gridView);

        for(int i=0;i<gridLayout.getChildCount();i++){

           //final int Value=gridLayout.getChildAt(i);
            final int Value=i;
            Log.e("card ","Value " + Value);
            gridLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Value==0||Value==1||Value==3) {
                        Intent i = new Intent(dashboard.this, MainActivity.class);
                        i.putExtra("selectedValue", Value);
                        startActivity(i);
                        Log.e("card ", "Clciked inside" + Value);
                    }else{

                        Snackbar.make(view, "Feature not available for now", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Feature not available for now", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }





    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void getPopularMovies(View view) {


    }
}
