package com.example.listenar;

public class SongInfo {

    private int ID;
    private String name;
    private int length;
    private String artist;
    private final int[] allSongIDs = {
            2132082688,
            2132082689,
            2132082690,
            2132082691,
            2132082692,
            2132082693,
            2132082694,
            2132082695,
            2132082696,
            2132082697,
            2132082698,
            2132082699,
            2132082700
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
