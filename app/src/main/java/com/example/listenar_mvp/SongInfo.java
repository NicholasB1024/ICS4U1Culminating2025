package com.example.listenar_mvp;

import java.util.*;

public class SongInfo {

    private int ID;
    private String name;
    private int length;
    private String artist;
    private final int[] allSongIDs = {
            2131951624,
            2131951625,
            2131951617,
            2131951616,
            2131951618,
            2131951626,
            2131951619,
            2131951627,
            2131951620,
            2131951621,
            2131951622,
            2131951623,
            2131951628
    };
    private final String[] allSongNames = {
            "A Little Less Conversation",
            "Belissima",
            "Blade Crystal Method - Bloodbath Dance",
            "Blade Fight Theme",
            "Blade Trinity 07",
            "Connection",
            "Dancin Queen (Club Mix)",
            "Rock This Town",
            "Song 2",
            "Staying Alive (Dance Traxx Remix)",
            "Sugar Sugar",
            "Toxic",
            "Video Killed the Radio Star"
    };
    private final int[] allSongLengths = {
            215,
            231,
            611,
            267,
            299,
            140,
            205,
            397,
            120,
            232,
            172,
            167,
            252
    };
    private final String[] allSongArtists = {
            "Elvis Presly, JXL",
            "DJ Quicksilver",
            "Blade Techno",
            "Blade Trinity Soundtrack",
            "Blade Trinity Soundtrack",
            "Elastica",
            "ABBA",
            "Brian Setzer Orchestra",
            "Blur",
            "Bee Gees",
            "Archies",
            "Crazytown",
            "The Buggels"
    };

    public SongInfo(int songID){

        for (int i = 0; i < allSongIDs.length; i++){

            if (songID == allSongIDs[i]){

                this.ID = allSongIDs[i];
                this.name = allSongNames[i];
                this.length = allSongLengths[i];
                this.artist = allSongArtists[i];
                break;

            }

        }

    }

    //accessors
    public int getID(){
        return this.ID;
    }

    public String getName(){

        return this.name;

    }

    public int getLength(){

        return this.length;

    }

    public String getFormattedLength(){

        return this.length / 60 + ":" + this.length % 60;

    }

    public String getArtist(){

        return this.artist;

    }

}
