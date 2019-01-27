package com.example.alex.upick.Adapters;

import android.annotation.SuppressLint;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAddMusicAdapter extends RecyclerView.Adapter<RecyclerAddMusicAdapter.MyViewHolder> {
    private List<Music> musicList;
    private OnItemClickListener listener;



    public RecyclerAddMusicAdapter(List<Music> musicList, OnItemClickListener listener) {
        this.musicList = musicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_music_add,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.bind(musicList.get(position),listener);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView img,fav;
        public TextView nome,autor,time;

        public MyViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.img);
            nome = view.findViewById(R.id.music);
            autor = view.findViewById(R.id.autor);
            time = view.findViewById(R.id.time);
            fav = view.findViewById(R.id.btn_fav_favorites);

        }
        public void bind(final Music music, final OnItemClickListener listener){

            Picasso.get().load(music.getImg()).into(img);
            autor.setText(music.getAutor());
            nome.setText(music.getNome());
            time.setText(music.getTime());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(music);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Music music);
    }
}