package com.codecamp.prashant.moviebx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codecamp.prashant.moviebx.Api.Client;
import com.codecamp.prashant.moviebx.Api.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity {

    TextView nameOfMovie,plots,userRatings,releaseDate,vote_average,Language,BudgetLabelValue,RevenueLabelValue,Duration;
    ImageView imageView;
    String ID;
    String[] movieDetails;
    String body;
    JSONObject newObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // initCollapsToolBar();
        nameOfMovie=findViewById(R.id.movieDesc);
        plots=findViewById(R.id.plot);
        userRatings=findViewById(R.id.userRating);
        releaseDate=findViewById(R.id.ReleaseDate);
        imageView=findViewById(R.id.img_thumbnail);
        Language=findViewById(R.id.languaGE);
        BudgetLabelValue=findViewById(R.id.BudgetLabelValue);
        RevenueLabelValue=findViewById(R.id.RevenueLabelValue);
        Duration=findViewById(R.id.Duration);


        Intent intent=getIntent();
        if(intent.hasExtra("id")){

             ID=getIntent().getExtras().getString("id");
            loadJSON();

        }
    }

    private void loadDataOnView(JSONObject moviedetails) {

        String thumbnail=getIntent().getExtras().getString("poster_path");
            Glide.with(this).load(thumbnail).into(imageView);

        try {
            Integer budget= (Integer) moviedetails.get("budget");
            Integer revenue= (Integer) moviedetails.get("revenue");
            String lang= (String) moviedetails.get("original_language");
            String  title= (String) moviedetails.get("original_title");
            String  release= (String) moviedetails.get("release_date");
            Integer  runTime= (Integer) moviedetails.get("runtime");
            Double  vote_avg= (Double) moviedetails.get("vote_average");
            String overview=(String) moviedetails.get("overview");

            nameOfMovie.setText(overview);
            getSupportActionBar().setTitle(title);
            releaseDate.setText(release);
            userRatings.setText(String.valueOf(vote_avg));
            Language.setText(lang);
            Duration.setText(String.valueOf(runTime+" min"));
            BudgetLabelValue.setText("$"+String.valueOf(budget));
            RevenueLabelValue.setText("$"+String.valueOf(revenue));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadJSON(){

        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please obtain the Api Key",Toast.LENGTH_LONG);
                return;
            }
            Client client=new Client();
            Service service=client.getClient().create(Service.class);

            Log.e("Movie ID: ",""+Integer.parseInt(ID));

            Call<ResponseBody> call=service.getMoviesWithID(Integer.parseInt(ID),BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                         body = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                         newObject = new JSONObject(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadDataOnView(newObject);


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Log.e("Error"," "+t.getMessage());
                }


            });

        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

//    private void initCollapsToolBar() {
//        final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapseToolbar);
//        collapsingToolbarLayout.setTitle("");
//
//        AppBarLayout appBarLayout=findViewById(R.id.bar);
//        appBarLayout.setExpanded(true);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow=false;
//            int scrollrange=-1;
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//               if(scrollrange==-1){
//                   scrollrange=appBarLayout.getTotalScrollRange();
//               }
//               if(scrollrange+i==0){
//                   collapsingToolbarLayout.setTitle("Movie Details");
//                   isShow=true;
//               }else if(isShow){
//                    collapsingToolbarLayout.setTitle("");
//                    isShow=false;
//               }
//
//            }
//        });
//
//    }
}
