package com.example.bakingapp2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp2.Activity.VideoActiviy;
import com.example.bakingapp2.Fragment.VideoFragment;
import com.example.bakingapp2.Model.Step;
import com.example.bakingapp2.R;

import java.net.URI;
import java.util.ArrayList;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private final ArrayList<Step> stepArrayList;
    Context context;
    onItemClickListner onItemClickListner;

    public StepsAdapter(Context context, ArrayList<Step> items) {

        this.context = context;
        stepArrayList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String videoURL = stepArrayList.get(position).getVideoURL();
        final String videoDescription = stepArrayList.get(position).getDescription();
        final String imageURL = stepArrayList.get(position).getThumbnailURL();
        holder.stepName.setText(videoDescription);

        holder.stepName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListner.onClick(videoURL);

                if (isTablet(context)) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    VideoFragment videoFragment = new VideoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("videoDescription", videoDescription);
                    bundle.putString("videoURLFragment", videoURL);
                    bundle.putString("imageURL" , imageURL);
                    videoFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container_video_fragment, videoFragment);
                    fragmentTransaction.commit();
                } else {
                    Intent i = new Intent(context, VideoActiviy.class);
                    i.putExtra("videoURL", videoURL);
                    i.putExtra("videoDescription", videoDescription);
                    i.putExtra("imageURL" , imageURL);

                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public void setOnClickListener(StepsAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    public interface onItemClickListner {
        void onClick(String str);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView stepName;

        public ViewHolder(View view) {
            super(view);
            stepName = (TextView) view.findViewById(R.id.step_text);
        }
    }
}
