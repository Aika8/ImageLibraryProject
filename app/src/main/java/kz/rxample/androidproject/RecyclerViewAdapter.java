package kz.rxample.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        Animation animation = AnimationUtils.loadAnimation(holder.imageView.getContext(), R.anim.pulse);
        animation.setDuration(800);
        animation.setRepeatCount(2);
        holder.imageView.setImageResource(R.drawable.loading);
        holder.imageView.startAnimation(animation);
            Picasso.get()
                    .load(imageList.get(position))
                    .into(holder.imageView);
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
