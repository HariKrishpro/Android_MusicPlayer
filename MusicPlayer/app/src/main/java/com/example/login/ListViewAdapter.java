package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewAdapter extends ArrayAdapter<String> {
    private ArrayList<String> imageHolder = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> artists = new ArrayList<>();
    private Activity context;
    private ListView listView;
    private MediaPlayer mediaPlayer;

    public ListViewAdapter(Activity context, ArrayList<String> imageHolder , ArrayList<String> title , ArrayList<String> description , ListView listView,ArrayList<String> artists, MediaPlayer mediaPlayer){
        super(context,R.layout.container,title);
        this.imageHolder = imageHolder;
        this.title = title;
        this.description = description;
        this.context = context;
        this.listView = listView;
        this.artists = artists;
        this.mediaPlayer = mediaPlayer;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.container,null,true);



        TextView textView = (TextView) view.findViewById(R.id.textViewId);
        textView.setEnabled(false);
        textView.setText(title.get(position));

        EditText editText = (EditText) view.findViewById(R.id.editTextDescription);
        editText.setEnabled(false);
        editText.setText(description.get(position));

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Glide.with(context)
                .asBitmap()
                .load(imageHolder.get(position))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        Palette palette = Palette.from(resource).generate();
                        ArrayList<Palette.Swatch> swatchArray = new ArrayList<>();
                        swatchArray.add(palette.getVibrantSwatch());
                        swatchArray.add(palette.getLightVibrantSwatch());
                        swatchArray.add(palette.getDarkMutedSwatch());

                        ArrayList<Integer> rgbArray = new ArrayList<>();
                        for(Palette.Swatch i : swatchArray){
                            if(i!=null){
                                rgbArray.add(i.getRgb());
                            }
                        }
                        int Backgroundrgb[] = new int[rgbArray.size()];
                        for(int i=0;i<Backgroundrgb.length;i++){

                            Backgroundrgb[i] = rgbArray.get(i);
                        }

                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,Backgroundrgb);
                        gradientDrawable.setCornerRadius(18f);
                        cardView.setBackgroundDrawable(gradientDrawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = (TextView) v.findViewById(R.id.textViewId);
                Toast.makeText(context,textView.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Inside.class);
                intent.putExtra("Data",artists.get(position));
                intent.putExtra("Start",true);
                context.startActivity(intent);
                

            }
        });
        return view;
    }
}
