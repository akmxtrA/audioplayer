package com.example.player;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.media.MediaPlayer;
import java.util.ArrayList;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private audioList selectedAudio;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private boolean isPlaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();

        ArrayList<audioList> audio = new ArrayList<>();
        audio.add(new audioList("город", "вышел покурить", R.drawable.city, R.raw.city));
        audio.add(new audioList("Wasted (Nightcore)", "Murkish, Huken", R.drawable.wasted, R.raw.wasted));
        audio.add(new audioList("Gassed Up", "Nebu Kiniza", R.drawable.gassed_up, R.raw.gassed_up));
        audio.add(new audioList("Lucid Dreams", "Juice WRLD", R.drawable.jcwrld, R.raw.lucid_dreams));
        audio.add(new audioList("METAMORPHOSIS", "INTERWORLD", R.drawable.metamorphosis, R.raw.metamorphosis));
        audio.add(new audioList("никому не нужны","вышел покурить", R.drawable.nobodyneeds, R.raw.nobodyneeds));
        audio.add(new audioList("рокмальчик","morphy", R.drawable.rockboy, R.raw.rockboy));
        audio.add(new audioList("All Girls Are The Same", "Juice WRLD", R.drawable.jcwrld, R.raw.all_girls_are_the_same));
        audio.add(new audioList("уличная жизнь", "вышел покурить", R.drawable.streetlife, R.raw.streetlife));

        audioAdapter adapter = new audioAdapter(this, audio);
        ListView listAudio = findViewById(R.id.audioList);
        listAudio.setAdapter(adapter);
        TextView name = findViewById(R.id.name);
        TextView executor = findViewById(R.id.executor);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        listAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAudio = audio.get(position);
                if (mediaPlayer != null) {
                        try {
                            mediaPlayer.reset();
                            mediaPlayer = MediaPlayer.create(MainActivity.this, selectedAudio.getAudio());
                            name.setText(selectedAudio.getName());
                            executor.setText(selectedAudio.getExecutor());
                            mediaPlayer.start();
                            seekBar.setMax(mediaPlayer.getDuration());
                            isPlaying = true;
                            updateSeekBar();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
        });

        ImageButton playButton = findViewById(R.id.playbtn);
        ImageButton pauseButton = findViewById(R.id.pausebtn);
        ImageButton stopButton = findViewById(R.id.stopbtn);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAudio != null) {
                    if ( !mediaPlayer.isPlaying()) {
                        try {
                            name.setText(selectedAudio.getName());
                            executor.setText(selectedAudio.getExecutor());
                            mediaPlayer.seekTo(seekBar.getProgress());
                            mediaPlayer.start();
                            isPlaying = true;
                            updateSeekBar();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Выберите песню", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPlaying = false;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    name.setText("");
                    executor.setText("");
                    mediaPlayer = null;
                    isPlaying = false;
                    seekBar.setProgress(0);
                }
            }
        });
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.isPlaying()) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar();
                    }
                };
                seekBar.postDelayed(runnable, 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}