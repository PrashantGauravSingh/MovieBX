package com.codecamp.prashant.moviebx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codecamp.prashant.moviebx.Adapter.MovieAdapter;
import com.codecamp.prashant.moviebx.Api.Client;
import com.codecamp.prashant.moviebx.Api.Service;
import com.codecamp.prashant.moviebx.model.MovieResponse;
import com.codecamp.prashant.moviebx.model.movie;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public MovieAdapter adapter;
    public static List<movie> movieList;
    private List<String> movieLists;
    public static Context mContext;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String TAG=MovieAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        getSupportActionBar().setTitle("Popular Movies");
        initView();
        swipeRefreshLayout=findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
            }
        });

    }

    public Activity getActivity(){
        Context context=this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity)
                return (Activity) context;
            context=((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void initView(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView=findViewById(R.id.recyclerview);

        movieList=new ArrayList<>();
        adapter= new MovieAdapter(mContext,movieList);

       // if(MainActivity.this,getResource().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            loadJSON();

    }

    private void loadJSON(){

        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please obtain the Api Key",Toast.LENGTH_LONG);
                progressDialog.dismiss();
                return;
            }
            Client client=new Client();
            Service service=client.getClient().create(Service.class);

            Call<MovieResponse>call=service.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<movie> movies=response.body().getResults();
                    for(int i=0;i<movies.size();i++){
                        Log.e("MovieValue: ",""+movies.get(i));
                    }
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);

                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    Log.e("Error"," "+t.getMessage());
                    Toast.makeText(MainActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            e.getLocalizedMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem mSearch = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setQueryHint("Search movies");
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText=newText.toLowerCase();
                List<movie> newList=new ArrayList<>();
                for(movie m:movieList){
                    String srchMovie=m.getOriginalTitle().toLowerCase();
                    if(srchMovie.contains(newText)) {
                        newList.add(m);
                    }

                }
                adapter.updateList(newList);
                return false;

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
