package com.example.checkmate;

import android.net.Uri;
import android.os.Bundle;
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

public class ShortsActivity extends Fragment {

    private View view;
    private GestureDetector mDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shorts, container, false);
        VideoView videoView = view.findViewById(R.id.videoView);
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
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // 비디오 URI 설정 (예: raw 폴더의 video 파일)
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.test_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // 비디오 재생 준비
        videoView.requestFocus();

        // 비디오 재생 시작
        videoView.start();
        return view;
    }


}
