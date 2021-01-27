package com.example.login;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.palette.graphics.Palette;

import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Songpic {
    private DataSnapshot snapshot = null;
    private ImageView imageView = null;
    private Handler handler = null;
    private Bitmap bitmap = null;
    private View Background = null;
    public Songpic(DataSnapshot snapshot, ImageView imageView, Handler handler, View Background) {
        this.snapshot = snapshot;
        this.imageView = imageView;
        this.handler = handler;
        this.Background = Background;
    }
//    Pal
    public void LikeMain(){
        Picture picture = new Picture(imageView,bitmap,Background,handler);

        picture.execute(snapshot.child("Image").getValue(String.class));
//        imageView.get
    }
    private class Picture extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView = null;
        Bitmap bitMap = null;
        View Background = null;
        Handler handler = null;
        public Picture(ImageView imageView,Bitmap bitMap,View Background,Handler handler) {
            this.imageView = imageView;
            this.bitMap = bitMap;
            this.Background = Background;
            this.handler = handler;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            InputStream inputStream = null;
            try {
                inputStream = new URL(strings[0]).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);






        }
    }   
}