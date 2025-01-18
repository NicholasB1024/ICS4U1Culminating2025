package com.example.listenar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.listenar.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button startButton, pauseButton, stopButton, prevButton, nextButton, switchPlaylistButton;
    TextView currentPlaylistText, currentSongText;
    MediaPlayer mediaPlayer;
    final Field[] allSongs = R.raw.class.getDeclaredFields();
    final ArrayList<SongInfo> allSongInfo = new ArrayList<>();
    Playlist currentQueue;
    SongInfo currentSong;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //initializing buttons using buttons created in the XML file
        startButton = findViewById(R.id.start);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);
        prevButton = findViewById(R.id.previous);
        nextButton = findViewById(R.id.next);
        switchPlaylistButton = findViewById(R.id.switchPlaylist);

        //initializing text view objects
        currentPlaylistText = findViewById(R.id.currentPlaylistName);
        currentPlaylistText.setText("");
        currentSongText = findViewById(R.id.currentSongName);
        currentSongText.setText("");

        //temporary stuff
        ArrayList<Integer> rawIDs = new ArrayList<>();
        for (Field song : allSongs) {

            try {

                rawIDs.add(song.getInt(song));

            } catch (IllegalAccessException e) {

                throw new RuntimeException(e);

            }

        }
        Log.d("song res IDs", rawIDs.toString());

        for (Field song : allSongs) {

            SongInfo s;
            try {

                s = new SongInfo(song.getInt(song));

            } catch (IllegalAccessException e) {

                throw new RuntimeException(e);

            }
            allSongInfo.add(s);

        }

        //test playlists
        Playlist p1 = new Playlist("test1");
        Playlist p2 = new Playlist("test2");
        try {

            for (int i = 0; i < allSongs.length / 2; i++) {

                p1.addSong(allSongs[i].getInt(allSongs[i]));

            }
            for (int i = allSongs.length / 2; i < allSongs.length; i++) {

                p2.addSong(allSongs[i].getInt(allSongs[i]));

            }

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        }

        currentQueue = p1;

        //UI object listeners
        //starts the media player, or resumes it if it's paused
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer == null){

                    playSong();

                }
                else {

                    Toast.makeText(getApplicationContext(), "Player Already Active", Toast.LENGTH_SHORT).show();

                }

            }
        });
        //pauses/resumes the media player
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    if (mediaPlayer.isPlaying()){

                        mediaPlayer.pause();

                    }
                    else{

                        mediaPlayer.start();

                    }

                }
                else {

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
        //stops the media player
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    stopPlaying();

                }
                else{

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
        //plays the previous song when pressed, if there is one
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    if (currentQueue.getSongPosition() - 1 >= 0){

                        stopPlaying();
                        currentQueue.setSongPosition(currentQueue.getSongPosition() - 1);
                        playSong();

                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Cannot Go Back Further", Toast.LENGTH_SHORT).show();

                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
        //plays the next song when pressed, if there is one
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    if (currentQueue.getSongPosition() + 1 < currentQueue.getPlaylistSize()) {

                        stopPlaying();
                        currentQueue.setSongPosition(currentQueue.getSongPosition() + 1);
                        playSong();

                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Nothing Next In Queue", Toast.LENGTH_SHORT).show();

                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
        //this will eventually be removed and replaced with a proper playlist selection system
        switchPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQueue == p1){

                    currentQueue = p2;

                }
                else{

                    currentQueue = p1;

                }
                currentQueue.setSongPosition(0);
                updateSongInfo();
                if (mediaPlayer != null) {

                    stopPlaying();
                    playSong();

                }


            }
        });

    }

    //plays a song by creating a new instance of the mediaplayer
    public void playSong(){

        mediaPlayer = MediaPlayer.create(getApplicationContext(), currentQueue.getSong(currentQueue.getSongPosition()));
        mediaPlayer.start();
        updateSongInfo();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (currentQueue.getSongPosition() < currentQueue.getPlaylistSize()) {

                    currentQueue.setSongPosition(currentQueue.getSongPosition() + 1);
                    playSong();

                }
                else{

                    stopPlaying();

                }

            }
        });

    }

    //method to stop the media player
    public void stopPlaying(){

        mediaPlayer.release();
        mediaPlayer = null;
        currentPlaylistText.setText("");
        currentSongText.setText("");

    }

    //updates and displays information about the current song
    public void updateSongInfo(){

        int curSongID = currentQueue.getSong(currentQueue.getSongPosition());
        for (int i = 0; i < allSongInfo.size(); i++){

            if (curSongID == allSongInfo.get(i).getID()){

                currentSong = allSongInfo.get(i);
                break;

            }

        }

        currentPlaylistText.setText(currentQueue.getName());
        currentSongText.setText(currentSong.getName());

    }

}