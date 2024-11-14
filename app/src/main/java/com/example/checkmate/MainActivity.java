package com.example.checkmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        System.out.println(num);
        ft.commit();
    }
}