package com.example.player;


public class audioList {
    private String name;
    private String executor;
    private int audioImage;
    private int audio;

    public audioList(String name, String executor, int audioImage, int audio){

        this.name=name;
        this.executor=executor;
        this.audioImage=audioImage;
        this.audio=audio;
    }

    public int getAudio(){return  audio;}

    public String getName() {
        return name;
    }

    public String getExecutor() {
        return executor;
    }

    public int getAudioImage() {
        return audioImage;
    }

}
