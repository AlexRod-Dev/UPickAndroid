package com.example.alex.upick.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;

import java.util.List;

public class RecyclerListMusicAdapter extends RecyclerView.Adapter<RecyclerListMusicAdapter.MyViewHolder> {
private List<Music> musicList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView music,autor,time,likes;

        public MyViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.img);
            music = view.findViewById(R.id.music);
            autor = view.findViewById(R.id.autor);
            time = view.findViewById(R.id.time);
            likes = view.findViewById(R.id.likes);

        }
    }

    public RecyclerListMusicAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_music,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Music music = musicList.get(position);
        holder.img.setImageResource(music.getImg());
        holder.autor.setText(music.getAutor());
        holder.music.setText(music.getNome());
        holder.time.setText(music.getTime());
        holder.likes.setText(music.getLikes());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }




}
