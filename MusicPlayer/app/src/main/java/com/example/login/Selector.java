package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Selector extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ArrayList<String> imageHolder = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> artists = new ArrayList<>();
    private ListView listView;
    static MediaPlayer mediaPlayer = new MediaPlayer();
    private CardView cardView;
    private ImageButton homeButton;
    private ImageButton searchButton;
    private ImageButton queueButton;
    private int position = 0;
    static FloatingActionButton floatPlay,floatNext,floatPrev;
    private Animation close,open,clockWise,antiClockWise;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        cardView = (CardView)findViewById(R.id.subCardView);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Transparent));

        floatPlay = (FloatingActionButton) findViewById(R.id.floatPlay);
        floatNext = (FloatingActionButton) findViewById(R.id.floatNext);
        floatPrev = (FloatingActionButton) findViewById(R.id.floatPrev);

        close = AnimationUtils.loadAnimation(this,R.anim.close);
        open = AnimationUtils.loadAnimation(this,R.anim.open);
        clockWise = AnimationUtils.loadAnimation(this,R.anim.clockwise);
        antiClockWise = AnimationUtils.loadAnimation(this,R.anim.anticlockwise);

        floatPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    floatNext.startAnimation(open);
                    floatPrev.startAnimation(open);
                    floatPlay.startAnimation(clockWise);
                    floatPrev.setClickable(true);
                    floatPrev.setClickable(true);
                    flag = true;
                    try{
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            floatPlay.setImageResource(R.drawable.ic_play_arrow_floating);
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
                else if(flag){
                    floatNext.startAnimation(close);
                    floatPrev.startAnimation(close);
                    floatPlay.startAnimation(antiClockWise);
                    floatPrev.setClickable(false);
                    floatPrev.setClickable(false);
                    flag = false;
                    try{

                            mediaPlayer.start();
                            floatPlay.setImageResource(R.drawable.ic_pause_float);

                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }



            }
        });

        floatPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inside.PreviousButton.callOnClick();
            }
        });
        floatNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inside.NextButton.callOnClick();

            }
        });


        listView = (ListView) findViewById(R.id.listView);





        homeButton = (ImageButton) findViewById(R.id.homeButton);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        queueButton = (ImageButton) findViewById(R.id.queueButton);





        //DB Reference



        databaseReference = FirebaseDatabase.getInstance().getReference().child("Songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    artists.add(snap.getKey());
                    imageHolder.add(snap.child("TitleCard").getValue(String.class));
                    title.add(snap.child("Title").getValue(String.class));
                    description.add(snap.child("Description").getValue(String.class));
                }

                ListViewAdapter listViewAdapter = new ListViewAdapter(Selector.this, imageHolder, title, description, listView, artists,mediaPlayer);
                listView.setAdapter(listViewAdapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        //FAB

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){
                      if(position==1){
                          searchButton.setImageResource(R.drawable.ic_baseline_search_24);
                      }
                      else if(position==2){
                          queueButton.setImageResource(R.drawable.ic_baseline_queue_music_24);
                      }
                      homeButton.setImageResource(R.drawable.ic_baseline_home_selected);
                    position = 0;
                }

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=1){
                    if(position==0){
                        homeButton.setImageResource(R.drawable.ic_baseline_home_24);
                    }
                    else if(position==2){
                        queueButton.setImageResource(R.drawable.ic_baseline_queue_music_24);
                    }
                    searchButton.setImageResource(R.drawable.ic_baseline_search_selected);
                }
                position = 1;
            }
        });
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=2){
                    if(position==0){
                        homeButton.setImageResource(R.drawable.ic_baseline_home_24);
                    }
                    else if(position==1){
                        searchButton.setImageResource(R.drawable.ic_baseline_search_24);
                    }
                    queueButton.setImageResource(R.drawable.ic_baseline_queue_music_selected);
                }
                position = 2;
            }
        });



    }
}