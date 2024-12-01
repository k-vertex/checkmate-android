package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ArticleActivity extends AppCompatActivity {

    private TextView writerView;
    private TextView timestampView;
    private TextView titleView;
    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        writerView = findViewById(R.id.article_writer);
        timestampView = findViewById(R.id.article_timestamp);
        titleView = findViewById(R.id.article_title);
        contentView = findViewById(R.id.article_content);

        int articleID = getIntent().getIntExtra("articleID", -1);
        if(articleID > -1) {
            loadArticle(articleID);
            loadComments(articleID);
            Log.d("geeg", articleID + "");
        }
    }

    private void loadArticle(int articleID) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://emperorchang.store:8888/board";
        RequestParams params = new RequestParams();
        params.put("articleID", articleID);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);
                    String title = object.getString("title");
                    String content = object.getString("content");
                    String name;
                    if(!object.getString("student_id").equals("null")) {
                        name = object.getString("name");
                    }
                    else {
                        name = "관계자";
                    }
                    LocalDateTime dateTime = LocalDateTime.parse(object.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    writerView.setText(name);
                    titleView.setText(title);
                    contentView.setText(content);
                    timestampView.setText(dateTime.getYear() + "." + dateTime.getMonthValue() + " " + dateTime.getHour() + ":" + dateTime.getMinute());
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ArticleActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments(int articleID) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://emperorchang.store:8888/board/comment";
        RequestParams params = new RequestParams();
        params.put("articleID", articleID);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Comment> commentList = new ArrayList<Comment>();
                try {
                    for(int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        int articleID = object.getInt("board_id");
                        int commentID = object.getInt("cmt_id");
                        String content = object.getString("content");
                        String name;
                        int writerID;
                        if(!object.getString("student_id").equals("null")) {
                            writerID = object.getInt("student_id");
                            name = object.getString("name");
                        }
                        else {
                            writerID = object.getInt("manager_id");
                            name = "관계자";
                        }
                        LocalDateTime dateTime = LocalDateTime.parse(object.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        Comment comment = new Comment(commentID, articleID, writerID, name, content, dateTime);
                        commentList.add(comment);
                    }
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                CommentAdapter commentAdapter = new CommentAdapter(commentList);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
                RecyclerView recyclerView = findViewById(R.id.article_recycler_view);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }
}
