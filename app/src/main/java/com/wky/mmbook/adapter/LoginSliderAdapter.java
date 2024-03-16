package com.wky.mmbook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wky.mmbook.R;

public class LoginSliderAdapter extends PagerAdapter {
    private Context context;
    private int[] images = {R.drawable.log_1, R.drawable.log_2, R.drawable.log_3, R.drawable.log_4};
    private ViewPager viewPager;
    private int currentPage = 0;
    private Handler handler;
    private final int DELAY_MS = 10000; // 10s
    public LoginSliderAdapter(Context context,ViewPager viewPager){
        this.context = context;
        this.viewPager = viewPager;
        this.handler = new Handler();
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        container.addView(view);

        startAutoScroll();//自动滚动

        return view;
    }

    private void startAutoScroll() {
        handler.postDelayed(runnable, DELAY_MS);
    }
    private void stopAutoScroll() {
        handler.removeCallbacks(runnable);
    }
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            currentPage = (currentPage + 1) % images.length;
            viewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(this, DELAY_MS);
        }
    };

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        stopAutoScroll();
    }
}
