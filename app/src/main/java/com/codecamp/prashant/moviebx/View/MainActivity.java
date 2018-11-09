package com.codecamp.prashant.moviebx.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codecamp.prashant.moviebx.Adapter.MovieAdapter;
import com.codecamp.prashant.moviebx.Presenter.movieDataInteractor;
import com.codecamp.prashant.moviebx.Presenter.RecyclerItemClickListener;
import com.codecamp.prashant.moviebx.Presenter.mainActivityImp;
import com.codecamp.prashant.moviebx.Presenter.mainActivityPresenter;
import com.codecamp.prashant.moviebx.R;
import com.codecamp.prashant.moviebx.model.movie;
import android.app.SearchManager;

import java.util.ArrayList;
import java.util.List;

public class  MainActivity extends AppCompatActivity implements mainActivityPresenter.MainView{

    private RecyclerView recyclerView;
    public MovieAdapter adapter;
    public static List<movie> movieList;
    public static Context mContext;
    ProgressDialog progressDialog;
    private mainActivityPresenter.presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String TAG=MovieAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initializeRecyclerView();
        swipeRefreshLayout=findViewById(R.id.swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        presenter = new mainActivityImp(this, new movieDataInteractor());
        presenter.requestDataFromServer();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                    presenter.requestDataFromServer();
                }
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

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeRecyclerView() {

        recyclerView=findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setTitle("Popular Movies");
        movieList=new ArrayList<>();

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


//                newText=newText.toLowerCase();
//                List<movie> newList=new ArrayList<>();
//                for(movie m:movieList){
//                    String srchMovie=m.getOriginalTitle().toLowerCase();
//                    if(srchMovie.contains(newText)) {
//                        newList.add(m);
//                    }
//
//                }
//                adapter.updateList(newList);
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

    /**
     * RecyclerItem click event listener
     * */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(movie mov) {

            Intent intent=new Intent(mContext,DetailedActivity.class);
                        intent.putExtra("id",mov.getId());
                        intent.putExtra("poster_path",mov.getPosterPath());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
            Toast.makeText(MainActivity.this,
                    "List title:  " + mov.getOriginalTitle(),
                    Toast.LENGTH_LONG).show();

        }
    };

    /**
     * Initializing  progress Dialog programmatically
     * */

    @Override
    public void showProgress() {

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {

        progressDialog.hide();

    }

    @Override
    public void setDataToRecyclerView(List<movie> noticeArrayList) {

        MovieAdapter adapter = new MovieAdapter(mContext,noticeArrayList ,recyclerItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}
