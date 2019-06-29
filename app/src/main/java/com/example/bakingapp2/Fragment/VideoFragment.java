package com.example.bakingapp2.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp2.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class VideoFragment extends Fragment {

    SimpleExoPlayer player;
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelection.Factory videoTrackSelectionFactory;
    TrackSelector trackSelector;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    long position;
    String videoURL;


    public VideoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            position = savedInstanceState.getLong("playerPosition");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_video, container, false);

        PlayerView playerView = view.findViewById(R.id.player_view);
        TextView videoDescriptionTV = view.findViewById(R.id.step_description);
        ImageView noVideoImage = view.findViewById(R.id.no_video_image);

        Bundle bundle = this.getArguments();
        playerView.setVisibility(View.GONE);

        if (bundle != null) {
            videoURL = bundle.getString("videoURLFragment");
            String videoDescription = bundle.getString("videoDescription");
            String imageURL = bundle.getString("imageURL");

            bandwidthMeter = new DefaultBandwidthMeter();
            videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            playerView.setPlayer(player);
            dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext()
                    , "BakingApp2"), bandwidthMeter);
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoURL));
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
            position = player.getContentPosition();
            if (position>0)
                player.seekTo(position);
            playerView.setVisibility(View.VISIBLE);
            if (videoURL.equals("")){
                noVideoImage.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.GONE);
                Picasso.get()
                            .load(Uri.parse(imageURL))
                            .placeholder(R.drawable.no_video)
                            .error(R.drawable.no_video)
                            .into(noVideoImage);
            }
            videoDescriptionTV.setText(videoDescription);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
    @Override
    public void onPause() {
        super.onPause();
        position = player != null ? player.getCurrentPosition() : 0;
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playerPosition" , position);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

}
