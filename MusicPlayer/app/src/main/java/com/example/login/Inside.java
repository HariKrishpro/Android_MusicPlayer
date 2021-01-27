package com.example.login;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.login.Play;
import com.example.login.R;
import com.example.login.Songpic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Inside extends AppCompatActivity {

    private ImageButton imageButtonPlay;
    private SeekBar seekBarPlaying;

    private DatabaseReference db;
    private ProgressBar progressBarPlay ;
    private  Handler handler = new Handler();
    private  Handler seekBarHandler = new Handler();
    private ImageView imageViewTitleCard;
    private  Bitmap bitmap;
    private View Background;
    private List<SldingItem> sldingItem = new ArrayList<>();
    private ViewPager2 viewPager2;
    private List<String> songTitle = new ArrayList<>();
    private List<String> songAlbum = new ArrayList<>();
    private TextView textViewAlbum;
    private TextView textViewTitle;
    private List<String> songList = new ArrayList<>();
    private boolean Playing = true;
    static ImageButton NextButton,PreviousButton;
    private SeekBarCall seekBarCall = new SeekBarCall();
    private MediaPlayer mediaPlayer1 = Selector.mediaPlayer;
    private FloatingActionButton playFab = Selector.floatPlay;
//    private Play play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.Transparent));
        Intent intent = getIntent();
        final boolean[] flag = {intent.getBooleanExtra("Start", false)};
        Background = (View)findViewById(R.id.Background);

        imageButtonPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
        progressBarPlay = (ProgressBar) findViewById(R.id.progressBarPlay);
        seekBarPlaying = (SeekBar) findViewById(R.id.seekBarPlaying);
        viewPager2 = (ViewPager2)findViewById(R.id.viewPager);
        textViewAlbum = (TextView)findViewById(R.id.textViewAlbum);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        NextButton = (ImageButton)findViewById(R.id.NextButton);
        PreviousButton = (ImageButton)findViewById(R.id.PreviousButton);
        int play = R.drawable.ic_baseline_play_arrow_24;
        int pause = R.drawable.ic_baseline_pause_24;
        progressBarPlay.setVisibility(View.INVISIBLE);
        if(flag[0]){
            moveTaskToBack(false);
        }
        db = FirebaseDatabase.getInstance().getReference().child("Songs").child(intent.getStringExtra("Data"))  ;
        Random random = new Random();
        String End[] ={"Full Stop","THat's It","End Of Story","Nothing More","Is All"};
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.child("Image").getValue(String.class)!=null){
                    sldingItem.add(new SldingItem(snap.child("Image").getValue(String.class)));}
                    if(snap.child("Album").getValue(String.class)!=null){
                    songAlbum.add(snap.child("Album").getValue(String.class));}
                    if(snap.child("Title").getValue(String.class)!=null){
                    songTitle.add(snap.child("Title").getValue(String.class));}
                    if(snap.child("Song").getValue(String.class)!=null){
                    songList.add(snap.child("Song").getValue(String.class));}
                }


                new ViewPager(viewPager2,sldingItem,Background,new boolean[]{true}).LikeMain();
                try{
                    if(mediaPlayer1.isPlaying()){}
                    else if(!mediaPlayer1.isPlaying()){
                        try {
                            mediaPlayer1.setDataSource(songList.get(0));
                            mediaPlayer1.prepareAsync();
                            mediaPlayer1.setOnPreparedListener(mp -> {
                                mediaPlayer1.start();
                                seekBarCall.ForSeekBarHandle(seekBarPlaying,mediaPlayer1,seekBarHandler,NextButton);
                                imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                                Playing = false;
                            });
                        } catch (Exception e) {}
                    }
                }
                catch (Exception e){
                    try {
                        mediaPlayer1.setDataSource(songList.get(0));
                        mediaPlayer1.prepareAsync();
                        mediaPlayer1.setOnPreparedListener(mp -> {
                            mediaPlayer1.start();
                            seekBarCall.ForSeekBarHandle(seekBarPlaying,mediaPlayer1,seekBarHandler,NextButton);
                            imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                            Playing = false;
                        });
                    } catch (Exception e1) {}
                }





                boolean clickOrNot[] = {true};
                NextButton.setOnClickListener(v -> {
                    if(viewPager2.getCurrentItem()==songList.size()-1){
                        Toast.makeText(getApplicationContext(),End[random.nextInt(End.length)],Toast.LENGTH_SHORT).show();
                    }
                    if(viewPager2.getCurrentItem()<songList.size() || !clickOrNot[0]){
                        if (clickOrNot[0]) {
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                        }
                        else if(!clickOrNot[0]){
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem());
                            clickOrNot[0] = true;
                        }
                        try{
                            if(mediaPlayer1.isPlaying() || Playing){
                                mediaPlayer1.reset();
                            }
                        }
                        catch (Exception e){}
                        try {
                            mediaPlayer1.setDataSource(songList.get(viewPager2.getCurrentItem()));
                            mediaPlayer1.prepareAsync();
                            mediaPlayer1.setOnPreparedListener(mp -> {
                            mediaPlayer1.start();
                            seekBarCall.ForSeekBarHandle(seekBarPlaying,mediaPlayer1,seekBarHandler,NextButton);
                            imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                            Playing = false;
                            });
                        } catch (Exception e) {
                            mediaPlayer1.reset();
                            clickOrNot[0] = false;
                            NextButton.callOnClick();
                        }
                        Toast.makeText(Inside.this,songTitle.get(viewPager2.getCurrentItem()),Toast.LENGTH_SHORT).show();
                    }
                });

                PreviousButton.setOnClickListener(v -> {
                    if(viewPager2.getCurrentItem()==0){
                        Toast.makeText(getApplicationContext(),End[random.nextInt(End.length)],Toast.LENGTH_SHORT).show();
                    }
                    if(viewPager2.getCurrentItem()>0 || !clickOrNot[0]){
                        try {
                            if (mediaPlayer1.isPlaying() || Playing) {
                                mediaPlayer1.reset();
                            }
                        }
                        catch (Exception e){}
                        if(clickOrNot[0]) {
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                        }
                        else if(!clickOrNot[0]){
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem());
                            clickOrNot[0] = true;
                        }
                        try {
                            mediaPlayer1.setDataSource(songList.get(viewPager2.getCurrentItem()));

                            mediaPlayer1.prepareAsync();
                            mediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mediaPlayer1.start();
                                    seekBarCall.ForSeekBarHandle(seekBarPlaying,mediaPlayer1,seekBarHandler,NextButton);
                                    imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                                    Playing = false;
                                }
                            });
                        } catch (Exception e) {
                            mediaPlayer1.reset();
                            clickOrNot[0] = false;
                            PreviousButton.callOnClick();
                        }
                        Toast.makeText(Inside.this,songTitle.get(viewPager2.getCurrentItem()),Toast.LENGTH_SHORT).show();

                    }
                });

                imageButtonPlay.setOnClickListener(v->{

                    if(Playing == false){
                        mediaPlayer1.pause();
                        imageButtonPlay.setImageResource(play);playFab.setImageResource(play);
                        Playing = true;
                    }
                    else if(Playing){
                        mediaPlayer1.start();
                        imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                        Playing = false;
                    }
                    try {
                        if(flag[0]){
                            mediaPlayer1.reset();
                            try {
                                mediaPlayer1.setDataSource(songList.get(viewPager2.getCurrentItem()));
                                mediaPlayer1.prepareAsync();
                                mediaPlayer1.setOnPreparedListener(mp -> {
                                    mediaPlayer1.start();
                                    seekBarCall.ForSeekBarHandle(seekBarPlaying,mediaPlayer1,seekBarHandler,NextButton);
                                    imageButtonPlay.setImageResource(pause);playFab.setImageResource(pause);
                                    Playing = false;
                                });
                            } catch (Exception e1) {}
                            flag[0] = false;
                        }
                    }
                    catch (Exception e){}

                });


                SetBackground setBackground = new SetBackground(Background);
                final int[] pos = {0};
                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                        if(sldingItem.get(position).getBitmap()!=null){
                        new SetBackground(sldingItem.get(position).getBitmap(),Background).setBackground();
                        setBackground.SetBitmap(sldingItem.get(position).getBitmap());
                        setBackground.setBackground();
                        }
                        init(position);
                        if(viewPager2.getCurrentItem()> pos[0]){
                            clickOrNot[0] = false;
                            NextButton.callOnClick();
                            pos[0] = viewPager2.getCurrentItem();
                        }
                        if(viewPager2.getCurrentItem()<pos[0]){
                            clickOrNot[0] = false;
                            PreviousButton.callOnClick();
                            pos[0] = viewPager2.getCurrentItem();
                        }
                    }

                });
                mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        NextButton.callOnClick();
                    }
                });


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });//Database End


    }//Oncreate End



    void init(int position){
        textViewAlbum.setText(songAlbum.get(position));
        textViewTitle.setText(songTitle.get(position));
    }



}


