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

    public static String token;
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
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("0", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
                        Log.d("0", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
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
        return false;
//        return sharedPref.getBoolean("login", false);
    }
}