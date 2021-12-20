package kz.rxample.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerTagAdapter extends RecyclerView.Adapter<RecyclerTagAdapter.ViewHolderOne>{
    List<String> tagList;

    public RecyclerTagAdapter(List<String> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public ViewHolderOne onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_view, parent, false);
        ViewHolderOne viewHolderOne = new ViewHolderOne(view);
        System.out.println("create adapter");
        return viewHolderOne;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOne holder, int position) {
        System.out.println(tagList.get(position));
        if(position % 2 ==0){
            holder.button.setText(tagList.get(position));
            holder.button.setWidth(250);
        }else{
            holder.button.setText(tagList.get(position));
        }
        holder.button.setText(tagList.get(position));
    }



    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        Button button;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonTag);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick (View v) {
                    Intent intentMain = new Intent(v.getContext(),
                            PressedActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("q", button.getText().toString());
                    intentMain.putExtras(bundle);
                    v.getContext().startActivity(intentMain);
                }
            });
        }
    }
}
