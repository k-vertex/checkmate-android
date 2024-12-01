package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText passwordEditText;
    private TextView singUpEditText;
    private TextView findpasswordEditText;
    private Button loginButton;
    private RadioButton radioButtonStudent;
    private RadioButton radioButtonParent;
    private RadioButton radioButtonAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idEditText = findViewById(R.id.input_id);
        passwordEditText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.login_button);

        radioButtonStudent = findViewById(R.id.radio_button_student);
        radioButtonParent = findViewById(R.id.radio_button_parent);
        radioButtonAdmin = findViewById(R.id.radio_button_admin);

        loginButton.setOnClickListener((view) -> {
            String userType;
            if(radioButtonAdmin.isChecked())
                userType = "관계자";
            else if(radioButtonParent.isChecked())
                userType = "학부모";
            else
                userType = "학생";
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w("0", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    requestLogin(idEditText.getText().toString(), passwordEditText.getText().toString(), userType, token);
                }
            });
        });

        singUpEditText = findViewById(R.id.singup_text);
        singUpEditText.setOnClickListener((view) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void requestLogin(String id, String password, String userType, String fcmToken) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://emperorchang.store:8888";
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("password", password);
        params.put("userType", userType);
        params.put("fcmToken", fcmToken);

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                try {
                    JSONObject object = response.getJSONObject(0);
                    int fid = object.getInt("fid");
                    editor.putBoolean("login", true);
                    editor.putInt("familyID", fid);
                    if(!object.isNull("device_token")) {
                        String deviceToken = object.getString("device_token");
                        if(deviceToken != null)
                            editor.putString("deviceToken", deviceToken);
                    }
                    if(userType.equals("학생")) {
                        editor.putInt("userID", object.getInt("student_id"));
                    }
                    else if(userType.equals("학부모")) {
                        editor.putInt("userID", object.getInt("parent_id"));
                    }
                    editor.putString("userType", userType);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
