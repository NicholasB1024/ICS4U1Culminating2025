package com.example.listenar_mvp;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    Button startButton, pauseButton, stopButton, prevButton, nextButton;    //creating button objects
    MediaPlayer mediaPlayer;                        //creating media player object
    final Field[] allSongs = R.raw.class.getDeclaredFields();
    int songPosition = 0;
    ArrayList<Integer> currentQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //UI initialization stuff that runs on startup
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initializing buttons using buttons created in the XML file
        startButton = findViewById(R.id.play);
        pauseButton = findViewById(R.id.pause);
        stopButton = findViewById(R.id.stop);
        prevButton = findViewById(R.id.previous);
        nextButton = findViewById(R.id.next);

        ArrayList<Integer> testQueue = new ArrayList<>();
        try {

            for (Field song : allSongs) {

                testQueue.add(song.getInt(song));

            }

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);

        }

        currentQueue = testQueue;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer == null){

                    playSong(songPosition);

                }
                else {

                    mediaPlayer.start();

                }

            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    mediaPlayer.pause();

                }
                else {

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
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
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    if (songPosition - 1 >= 0){

                        stopPlaying();
                        songPosition--;
                        playSong(songPosition);

                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Cannot Go Back Further", Toast.LENGTH_SHORT).show();
                        Log.d("Current Song Information", "Song Position: " + songPosition);

                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    if (songPosition + 1 < currentQueue.size()) {

                        stopPlaying();
                        songPosition++;
                        playSong(songPosition);

                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Nothing Next In Queue", Toast.LENGTH_SHORT).show();
                        Log.d("Current Song Information", "Song Position: " + songPosition);

                    }

                }
                else{

                    Toast.makeText(getApplicationContext(), "Nothing Currently Playing", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    //plays a song by creating a new instance of the mediaplayer
    public void playSong(int position){

        mediaPlayer = MediaPlayer.create(getApplicationContext(), currentQueue.get(position));
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (songPosition < currentQueue.size()) {

                    songPosition++;
                    playSong(songPosition);

                }
                else{

                    stopPlaying();

                }

            }
        });

    }

    public void stopPlaying(){

        mediaPlayer.release();
        mediaPlayer = null;

    }

}