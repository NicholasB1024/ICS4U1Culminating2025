package com.example.listenar_mvp;

import java.util.*;

public class Playlist {

    private String name;
    private ArrayList<Integer> songList;
    private int songPosition;

    //constructor
    public Playlist(String name){

        this.name = name;
        this.songList = new ArrayList<>();
        this.songPosition = 0;

    }

    //accessors
    public String getName(){

        return name;

    }

    public ArrayList<Integer> getSongList(){

        return songList;

    }

    public int getSong(int pos){

        return songList.get(pos);

    }

    public int getSongPosition(){

        return songPosition;

    }

    public int getPlaylistSize(){

        return songList.size();

    }

    //mutators

    public void setSongPosition(int songPosition){

        this.songPosition = songPosition;

    }

    //helper methods
    public void addSong(int songID){

        songList.add(songID);

    }

}
