package com.example.usuari.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.usuari.myapplication.services.MusicPlayerService;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Intent i;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        i = new Intent(this, MusicPlayerService.class);
        i.setAction(MusicPlayerService.Actions.INIT+"");
        startService(i);

    }

    public void onBtnPlay(View view) {
        i.setAction(MusicPlayerService.Actions.PLAY+"");
        startService(i);

    }

    public void onBtnPause(View view) {
        i.setAction(MusicPlayerService.Actions.PAUSE+"");
        startService(i);
    }

}
