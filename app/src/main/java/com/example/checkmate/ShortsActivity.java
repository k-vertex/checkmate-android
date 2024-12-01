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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
        // 비디오 URL 설정
        String videoUrl = "http://www.emperorchang.store:8888/video";
        WebView webView = view.findViewById(R.id.videoView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(videoUrl);
//        String outputPath = "변환된_비디오_저장_경로";
//        String ffmpegCommand = String.format("-i %s -c:v libx264 -preset ultrafast -crf 23 -c:a aac -b:a 128k %s", videoUrl, outputPath);
//        FFmpegKit.executeAsync(ffmpegCommand, session -> {
//            if (ReturnCode.isSuccess(session.getReturnCode())) {
//                ExoPlayer player = new ExoPlayer.Builder(getContext()).build();
//                PlayerView playerView = view.findViewById(R.id.videoView);
//                playerView.setPlayer(player);
//                player.addAnalyticsListener(new EventLogger());
//
//                MediaItem mediaItem = MediaItem.fromUri(outputPath);
//                player.setMediaItem(mediaItem);
//
//                player.setPlayWhenReady(true);
//                player.seekTo(0, 0);
//                player.prepare();
//                player.play();
//
//            } else {
//                // 변환 실패 처리
//            }
//        });

        return view;
    }


}
