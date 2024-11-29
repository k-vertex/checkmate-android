package com.example.checkmate;

import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.EventLogger;

public class ShortsActivity extends Fragment {

    private View view;
    private GestureDetector mDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shorts, container, false);
//        VideoView videoView = view.findViewById(R.id.videoView);
        mDetector = new GestureDetector(getActivity(), new MyGestureListener());
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
//        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                System.out.println("새로고침");
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        // MediaController 설정
//        MediaController mediaController = new MediaController(getActivity());
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//
//        // 비디오 URI 설정 (예: raw 폴더의 video 파일)
//        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.test;
////        String videoPath = "http://www.emperorchang.store:8888/video";
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);
//
//        videoView.requestFocus();
//
//        videoView.start();
        ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
        PlayerView playerView = view.findViewById(R.id.videoView);
        playerView.setPlayer(player);
        player.addAnalyticsListener(new EventLogger());

        // 비디오 URL 설정
        String videoUrl = "http://www.emperorchang.store:8888/video";
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);

        player.setPlayWhenReady(true);
        player.seekTo(0, 0);
        player.prepare();
        return view;
    }


}
