package com.example.mybestyoutube.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybestyoutube.R;
import com.example.mybestyoutube.VideoModel;

import java.util.ArrayList;

public class VideosAdapter  extends RecyclerView.Adapter<VideosAdapter.MyHolder> {

    private ArrayList<VideoModel> videoFolder = new ArrayList<>();
    private final Context context;

    public VideosAdapter(ArrayList<VideoModel> videoFolder, Context context) {
        this.videoFolder = videoFolder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        Glide.with(context).load(videoFolder.get(position).getPath()).into(holder.thumbnail);
        holder.title.setText(videoFolder.get(position).getTitle());
        holder.duration.setText(videoFolder.get(position).getDuration());
        holder.size.setText(videoFolder.get(position).getSize());
        holder.resolution.setText(videoFolder.get(position).getResolution());

    }

    @Override
    public int getItemCount() {
        return videoFolder.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, size, duration, resolution;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.iv_thumbnail);
            title = itemView.findViewById(R.id.tv_title);
            size = itemView.findViewById(R.id.tv_size);
            duration = itemView.findViewById(R.id.tv_duration);
            resolution = itemView.findViewById(R.id.tv_resolution);

        }
    }
}
