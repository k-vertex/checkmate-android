package com.example.checkmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BoardActivity extends Fragment {

    private View view;
    private ImageView writeImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board, container, false);
        writeImage = view.findViewById(R.id.write_image);
        writeImage.setOnClickListener((view) -> {
            Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
