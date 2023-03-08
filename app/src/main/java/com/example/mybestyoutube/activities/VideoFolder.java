package com.example.mybestyoutube.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.mybestyoutube.R;
import com.example.mybestyoutube.VideoModel;
import com.example.mybestyoutube.adapter.VideosAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class VideoFolder extends AppCompatActivity {


    private RecyclerView recyclerView;
    private String name;
    private ArrayList<VideoModel> videoModelArrayList = new ArrayList<>();
    private VideosAdapter videosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_folder);

        name = getIntent().getStringExtra("folderName");
        recyclerView = findViewById(R.id.video_recyclerview);

        loadVideos();
    }

    private void loadVideos() {
        videoModelArrayList = getAllVideoFromFolder(this, name);
        if (name != null && videoModelArrayList.size() > 0) {

            videosAdapter = new VideosAdapter(videoModelArrayList, this);
            recyclerView.setAdapter(videosAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false));
        } else {
            Toast.makeText(this, "Can't find any videos", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<VideoModel> getAllVideoFromFolder(Context context, String name) {
        ArrayList<VideoModel> list = new ArrayList<>();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String orderBy = MediaStore.Video.Media.DATE_ADDED + " DESC";

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.RESOLUTION
        };

        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%" + name + "ù%"};

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, orderBy);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                int size = cursor.getInt(2);
                String resolution = cursor.getString(4);
                int duration = cursor.getInt(5);
                String disName = cursor.getString(6);
                String bucket_display_name = cursor.getString(7);
                String width_height = cursor.getString(8);
                
                // Convert 1024B in 1MB
                String size_formatted = null;
                if (size < 1024) {
                    size_formatted = String.format(context.getString(R.string.size_in_b), (double) size);
                } else if (size < Math.pow(1024, 2)) {
                    size_formatted = String.format(context.getString(R.string.size_in_b), (double) (size / 1024));
                } else if (size < Math.pow(1024, 3)) {
                    size_formatted = String.format(context.getString(R.string.size_in_b), (double) (size / Math.pow(1024, 2)));
                } else {
                    size_formatted = String.format(context.getString(R.string.size_in_b), (double) (size / Math.pow(1024, 3)));
                }

                // Convert any random video duration like 3666 int 1:01:06
                String duration_formatted;
                int sec = (duration / 1000) % 60;
                int min = (duration / (1000 * 60)) % 60;
                int hrs = duration / (1000 * 60 * 60);

                if (hrs == 0) {
                    duration_formatted = String.valueOf(min)
                            .concat(":".concat(String.format(Locale.FRANCE, "%02d", sec)));
                } else {
                    duration_formatted = String.valueOf(hrs)
                            .concat(":".concat(String.format(Locale.FRANCE, "%02d", min)
                                .concat(":".concat(String.format(Locale.FRANCE, "%02d", sec)))));
                }

                VideoModel files = new VideoModel(id, path, title,
                        size_formatted, resolution, duration_formatted,
                        disName, width_height);
                if (name.endsWith(bucket_display_name))
                    list.add(files);

            }
            cursor.close();
        }
        return list;
    }
}