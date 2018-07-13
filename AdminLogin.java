package com.mplus.uvish.tourist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    EditText uid,upass;
    SharedPreferences sp;
    SharedPreferences.Editor se;
    String etunn,etupp,etun,etup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        uid=(EditText)findViewById(R.id.editText);
        upass=(EditText)findViewById(R.id.editText2);
        sp=getSharedPreferences("admin",MODE_PRIVATE);
        se=sp.edit();
        se.putString("uname","admin");
        se.putString("upass","admin");
        se.commit();
        etunn=sp.getString("uname",null);
        etupp=sp.getString("upass",null);
        hideKeyboard(uid);
        hideKeyboard(upass);
    }
    public void login(View v)
    {
       etun=uid.getText().toString();
         etup=upass.getText().toString();

        if(etun.equals(etunn)  && etup.equals(etupp))
        {
            Toast.makeText(this, "LOGGED IN", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AdminScreen.class);
            startActivity(i);
        }


    }
    public void hideKeyboard(EditText et) {
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

    }
}
