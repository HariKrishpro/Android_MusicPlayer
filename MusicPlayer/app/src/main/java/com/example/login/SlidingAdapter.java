package com.example.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class SlidingAdapter extends RecyclerView.Adapter<SlidingAdapter.SlidingViewHolder>{

    List<SldingItem> list;
    private Context context;
    private View Background;
    private boolean flag[];
    public SlidingAdapter(List<SldingItem> list,View Background , boolean []flag) {
        this.list = list;
        this.Background = Background;
        this.flag = flag;
    }

    @NonNull
    @Override
    public SlidingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SlidingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slidingpager,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlidingViewHolder holder, int position) {
        holder.setImage(list.get(position),position,Background);
//        holder.setBackground();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SlidingViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        public SlidingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageItem);
        }
        void setImage(SldingItem sldingItem,int position,View Background){
//            imageView.setImageResource(sldingItem.getItem());

            Glide.with(context)
                    .asBitmap()
                    .load(sldingItem.getItem())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            sldingItem.setBitmap(resource);
                            if(position==0 && flag[0]){
                                new SetBackground(resource,Background).setBackground();
                                flag[0]  = false;
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }

    }



}
