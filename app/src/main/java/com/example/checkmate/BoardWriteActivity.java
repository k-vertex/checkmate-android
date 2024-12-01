package com.example.checkmate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.msebera.android.httpclient.Header;

public class BoardWriteActivity extends AppCompatActivity {

    private TextView writeBoard;
    private EditText titleView;
    private EditText contentView;
    private ImageView addVideo;
    private Uri URI;
    private String filePath;
    private ActivityResultLauncher<String> pickVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_write);

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        titleView = findViewById(R.id.write_board_title);
        contentView = findViewById(R.id.write_board_content);

        pickVideo = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            URI = uri;

                        } else {
                            Log.d("VideoPicker", "비디오가 선택되지 않았습니다");
                        }
                    }
                });


        addVideo = findViewById(R.id.add_video);
        addVideo.setOnClickListener((view) -> {
            pickVideo.launch("video/*");
        });

        writeBoard = findViewById(R.id.upload_board);
        writeBoard.setOnClickListener((view) -> {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://emperorchang.store:8888/upload";
            RequestParams params = new RequestParams();
            params.put("title", titleView.getText().toString());
            params.put("content", contentView.getText().toString());
            params.put("writer", sharedPref.getInt("userID", -1));
            params.put("userType", sharedPref.getString("userType", "null"));
            if(URI != null) {
                try {
                    params.put("file", getContentResolver().openInputStream(URI), "video.mp4");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Intent intent = new Intent(BoardWriteActivity.this, MainActivity.class);
                    intent.putExtra("게시글 작성 완료", true);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(BoardWriteActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void handleSelectedVideo(Uri uri) {
        Log.d("VideoPicker", "선택된 비디오 URI: " + uri);

        String[] projection = {MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                long duration = cursor.getLong(durationColumn);
                long size = cursor.getLong(sizeColumn);

                DocumentFile documentFile = DocumentFile.fromSingleUri(getApplicationContext(), uri);
                filePath = documentFile.getUri().getPath();

                Log.d("VideoPicker", "비디오 길이: " + duration + "ms, 크기: " + size + "bytes");
            }
        } catch (Exception e) {
            Log.e("VideoPicker", "비디오 메타데이터 추출 중 오류 발생", e);
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        String fileName = getFileName(uri);
        File destinationFile = new File(getFilesDir(), fileName);
        try (InputStream is = getContentResolver().openInputStream(uri);
             OutputStream os = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
        return destinationFile;
    }


    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
            Log.d("hi", "여기;");
        }
        return result;
    }
}
