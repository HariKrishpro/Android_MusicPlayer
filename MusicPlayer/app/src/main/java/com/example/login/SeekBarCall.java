package com.example.login;


import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.google.firebase.database.DataSnapshot;
import android.os.Handler;
import java.io.IOException;


public class SeekBarCall {


    protected void ForSeekBarHandle(SeekBar current, MediaPlayer mediaPlayer, Handler rootHandler, ImageButton next){
        current.setMax(mediaPlayer.getDuration());
        Runnable Update = new Runnable() {
            @Override
            public void run() {
                current.setProgress(mediaPlayer.getCurrentPosition());
                rootHandler.postDelayed(this,50);

            }
        };
        new Thread(Update).start();

        current.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
