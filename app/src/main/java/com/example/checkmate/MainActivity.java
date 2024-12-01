package com.example.checkmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private BoardActivity frag1;
    private ShortsActivity frag2;
    private AttendanceActivity frag3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        boolean board = getIntent().getBooleanExtra("게시글 작성 완료", false);

        if(board)
            Toast.makeText(this, "게시글 작성 완료", Toast.LENGTH_SHORT).show();

        frag1 = new BoardActivity();
        frag2 = new ShortsActivity();
        frag3 = new AttendanceActivity();

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            int num = 0;
            if(id == R.id.menu_board)
                num = 0;
            else if(id == R.id.menu_shorts)
                num = 1;
            else if(id == R.id.menu_attendance)
                num = 2;
            setFreg(num);
            return true;
        });
        setFreg(0);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Toast.makeText(this, sharedPref.getInt("familyID", 0) + " " + sharedPref.getString("deviceToken", "null") + " " + sharedPref.getInt("userID", -1), Toast.LENGTH_SHORT).show();
    }

    private void setFreg(int num) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(num) {
            case 0:
                ft.replace(R.id.mainFrame, frag1);
                break;
            case 1:
                ft.replace(R.id.mainFrame, frag2);
                break;
            case 2:
                ft.replace(R.id.mainFrame, frag3);
                break;
        }
        ft.commit();
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        return sharedPref.getBoolean("login", false);
    }
}