package kz.rxample.androidproject;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderOne>{
    List<String> imageList;

    public RecyclerViewAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolderOne onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view, parent, false);
        ViewHolderOne viewHolderOne = new ViewHolderOne(view);
        System.out.println("create adapter");
        return viewHolderOne;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOne holder, int position) {
        System.out.println(imageList.get(position));
        Animation animation = AnimationUtils.loadAnimation(holder.imageView.getContext(), R.anim.loading);
        animation.setDuration(800);
        animation.setRepeatCount(2);
        holder.imageView.setImageResource(R.drawable.loading);
        holder.imageView.startAnimation(animation);
            Picasso.get()
                    .load(imageList.get(position))
                    .into(holder.imageView);


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "starting");
//                progressBar.setVisibility(View.VISIBLE);
//                for(int i = 0; i < 10; i++){
//                    Log.d(TAG, "Main counting: " + i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
