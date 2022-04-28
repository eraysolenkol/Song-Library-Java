package com.holeng;

public class Song {
    private final String name,singer,albume;
    private final int duration;

    public Song(String name, String singer, String albume, int duration) {
        this.name = name;
        this.singer = singer;
        this.albume = albume;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getAlbume() { return albume; }

    public int getDuration() {
        return duration;
    }

}
