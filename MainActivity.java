package com.mplus.uvish.tourist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void toadmin(View v)
    {
        Intent i=new Intent(this,AdminLogin.class);
        startActivity(i);
    }
    public void allcity(View v)
    {
        Intent i=new Intent(this,City.class);
        startActivity(i);
    }
}
