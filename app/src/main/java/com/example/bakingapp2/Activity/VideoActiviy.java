package com.example.bakingapp2.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp2.Fragment.VideoFragment;
import com.example.bakingapp2.R;

public class VideoActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activiy);

        String videoURL = getIntent().getStringExtra("videoURL");
        String imageURL = getIntent().getStringExtra("imageURL");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("videoURLFragment", videoURL);
        bundle.putString("imageURL" , imageURL);
        videoFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.video_frag_container, videoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}