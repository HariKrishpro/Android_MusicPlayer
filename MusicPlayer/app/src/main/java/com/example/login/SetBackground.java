package com.example.login;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.palette.graphics.Palette;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SetBackground {
    private Bitmap bitmap;
    private View background;


    public SetBackground(View background) {
        this.background = background;
    }

    public SetBackground(Bitmap bitmap, View background) {
        this.bitmap = bitmap;
        this.background = background;
    }

    public void SetBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    void setBackground(){
        Palette palette = Palette.from(bitmap).generate();
        ArrayList<Palette.Swatch> swatchArray = new ArrayList<>();
        swatchArray.add(palette.getVibrantSwatch());
        swatchArray.add(palette.getLightVibrantSwatch());
        swatchArray.add(palette.getDarkVibrantSwatch());
        swatchArray.add(palette.getLightMutedSwatch());
        swatchArray.add(palette.getDarkMutedSwatch());
        swatchArray.add(palette.getMutedSwatch());
        ArrayList<Integer> rgbArray = new ArrayList<>();
        int curr=0;
        for(Palette.Swatch i : swatchArray){

            if(i!=null && curr<=2){
                rgbArray.add(i.getRgb());
            }
            curr++;
        }
        int One = 0,Two = 0;
        if(rgbArray.size()==0){
            rgbArray.add(-7337976);
            rgbArray.add(-1048568);
        }
        else if(rgbArray.size()==1){
            rgbArray.add(-1048568);
        }
        else if(rgbArray.size()>2){
            One = rgbArray.get(0);
            Two = rgbArray.get(1);
            rgbArray.clear();
            rgbArray.add(One);
            rgbArray.add(Two);
        }

        int Backgroundrgb[] = new int[rgbArray.size()];
        int current[] = {-3631088, -16758736};
        for(int i=0;i<Backgroundrgb.length;i++){

            Backgroundrgb[i] = rgbArray.get(i);
        }
        GradientDrawable gradientDrawable = null;
        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,Backgroundrgb);


        gradientDrawable.setCornerRadius(0f);
        background.setBackgroundDrawable(gradientDrawable);

    }
}
