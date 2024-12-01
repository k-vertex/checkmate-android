package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class BoardActivity extends Fragment {

    private View view;
    private ImageView writeImage;
    private BoardAdapter boardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board, container, false);
        writeImage = view.findViewById(R.id.write_image);
        writeImage.setOnClickListener((view) -> {
            Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
            startActivity(intent);
        });
        boardAdapter = null;
        loadArticle();
        return view;
    }

    private void loadArticle() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://emperorchang.store:8888/board";
        RequestParams params = new RequestParams();
        if(boardAdapter != null) {
            List<Article> articleList = boardAdapter.getArticleList();
            params.put("lastBoardID", articleList.get(articleList.size()-1).getArticleID());
        }
        List<Article> articleList = new ArrayList<Article>();

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int articleID = object.getInt("board_id");
                        String title = object.getString("title");
                        String content = object.getString("content");
                        String videoPath = object.getString("video");
                        String name = object.getString("name");
                        String userType;
                        int writerID ;
                        if(!object.getString("student_id").equals("null")) {
                            writerID = object.getInt("student_id");
                            userType = "학생";
                        }
                        else {
                            writerID = object.getInt("manager_id");
                            userType = "관계자";
                            name = "관계자";
                        }
                        LocalDateTime dateTime = LocalDateTime.parse(object.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        Article article = new Article(articleID, writerID, userType, title, content, dateTime, name, videoPath);
                        articleList.add(article);
                    }
                    catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(boardAdapter == null) {
                    boardAdapter = new BoardAdapter(articleList);
                    RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
                    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(boardAdapter);
                }
                else
                    boardAdapter.addItems(articleList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if(statusCode == 200) {

                }
                else {

                }
            }
        });
    }
}
