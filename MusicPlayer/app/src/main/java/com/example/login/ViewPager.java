package com.example.login;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class ViewPager {
    ViewPager2 viewPager2;
    List<SldingItem> list;
    private View Background;
    private boolean flag[];
    public ViewPager(ViewPager2 viewPager2, List<SldingItem> list,View Background,boolean []flag) {
        this.viewPager2 = viewPager2;
        this.list = list;
        this.Background = Background;
        this.flag = flag;
    }
    void LikeMain(){
        viewPager2.setAdapter(new SlidingAdapter(list,Background,flag));
        viewPager2.setClipChildren(false);
        viewPager2.setClipChildren(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(80));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setScaleY(1);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }
}
