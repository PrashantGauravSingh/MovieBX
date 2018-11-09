package com.codecamp.prashant.moviebx.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codecamp.prashant.moviebx.Presenter.RecyclerItemClickListener;
import com.codecamp.prashant.moviebx.R;
import com.codecamp.prashant.moviebx.model.movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    public static Context mContext;
    private List<movie> movieList;
    private RecyclerItemClickListener recyclerItemClickListener;

    public MovieAdapter(Context mContext, List<movie> movieList,RecyclerItemClickListener recyclerItemClickListener) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.recyclerItemClickListener=recyclerItemClickListener;
    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder myViewHolder, int i) {

        MovieAdapter.MyViewHolder myViewHolder1=myViewHolder;
        myViewHolder1.title.setText(movieList.get(i).getOriginalTitle());
        String vote=Double.toString(movieList.get(i).getVoteAverage());
        myViewHolder1.userrating.setText(vote);
        Glide.with(mContext)
                .load(movieList.get(i).getPosterPath()).into(myViewHolder1.thumbnail);
        myViewHolder1.MoviewDescription.setText(movieList.get(i).getOverview());
        myViewHolder1.languages.setText(movieList.get(i).getOriginalLanguage());
        myViewHolder1.releaseDate.setText(movieList.get(i).getRelaseDate());


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateList(List<movie> newList){

        movieList=new ArrayList<>();
        movieList.addAll(newList);
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title,userrating,MoviewDescription,languages,releaseDate;
        public ImageView thumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            thumbnail=itemView.findViewById(R.id.img_thumbnail);
            userrating=itemView.findViewById(R.id.rating);
            languages=itemView.findViewById(R.id.language);
            releaseDate=itemView.findViewById(R.id.Date);
            MoviewDescription=itemView.findViewById(R.id.movieDescription);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    recyclerItemClickListener.onItemClick(movieList.get(pos));
                }
            });
        }
    }
}
