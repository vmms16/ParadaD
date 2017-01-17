package com.example.vinicius.paradad;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction= fragmentManager.beginTransaction();

        transaction.add(R.id.conteiner, new MapsFragment(),"MapsFragment");

        transaction.commitAllowingStateLoss();
    }
}
