package com.example.checkmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    private RadioButton radioButtonStudent;
    private RadioButton radioButtonParent;
    private Button signupButton;
    private EditText inputName;
    private EditText inputRRNFront;
    private EditText inputRRNBack;
    private EditText inputID;
    private EditText inputPassword;
    private EditText verifyInputPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Spinner spinner = findViewById(R.id.spinner);
        radioButtonStudent = findViewById(R.id.radio_button_student);
        radioButtonParent = findViewById(R.id.radio_button_parent);
        signupButton = findViewById(R.id.singup_button);
        inputName = findViewById(R.id.input_name);
        inputRRNFront = findViewById(R.id.input_rrn_front);
        inputRRNBack = findViewById(R.id.input_rrn_back);
        inputID = findViewById(R.id.input_id);
        inputPassword = findViewById(R.id.input_password);
        verifyInputPassword = findViewById(R.id.input_password_verify);

        String[] items = new String[]{"CheckMate"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        verifyInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = inputPassword.getText().toString();
                String color = !password.equals(s.toString()) ? "#FF2424" : "#D5D5D5";
                Context context = inputPassword.getContext();
                LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.border_box).mutate();
                GradientDrawable borderLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.border);
                borderLayer.setColor(Color.parseColor(color));
                inputPassword.setBackground(layerDrawable);
            }
        });

        signupButton.setOnClickListener((view) -> {
            String userType;
            if(radioButtonParent.isChecked())
                userType = "학부모";
            else
                userType = "학생";
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://emperorchang.store:8888/register";
            RequestParams params = new RequestParams();
            params.put("name", inputName.getText().toString());
            String rrn = inputRRNFront.getText().toString() + "-" + inputRRNBack.getText().toString();
            params.put("rrn", rrn);
            params.put("id", inputID.getText().toString());
            params.put("password", inputPassword.getText().toString());
            params.put("userType", userType);

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(RegisterActivity.this, responseString, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
