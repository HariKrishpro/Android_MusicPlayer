package com.example.login;

import android.graphics.Bitmap;

public class SldingItem {
    String item;
    Bitmap bitmap;
    public SldingItem(String item) {
        this.item = item;
    }

    public SldingItem(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getItem() {
        return item;
    }

}
