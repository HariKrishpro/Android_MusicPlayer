package com.example.login;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;


public class Progress {
    private ProgressBar progressBar = null;
    private Handler handler = null;

    public Progress(ProgressBar progressBar, Handler handler) {
        this.progressBar = progressBar;
        this.handler = handler;
    }

    public void Load(boolean flag){
        if(flag) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
        if(!flag){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }
}
