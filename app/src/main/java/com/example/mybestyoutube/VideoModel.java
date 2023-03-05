package com.example.mybestyoutube;

public class VideoModel {

    String id;
    String path;
    String title;
    String size;
    String resolution;
    String duration;
    String displayName;
    String wh;

    public VideoModel(String id, String path, String title,
                      String size, String resolution, String duration,
                      String displayName, String wh) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.size = size;
        this.resolution = resolution;
        this.duration = duration;
        this.displayName = displayName;
        this.wh = wh;
    }
}
