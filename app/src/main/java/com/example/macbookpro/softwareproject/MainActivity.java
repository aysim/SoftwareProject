package com.example.macbookpro.softwareproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{
    Button signIn,signUp;
    //operation names are coming from name of servlets.
    //look at the serverside project under the war>WEB-INF>web.xml path.
    public static final String OPERATION_SIGNIN = "login";
    public static final String OPERATION_SIGNUP = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();
    }

    public void Initialize()
    {
        signIn = (Button) findViewById(R.id.buttonSignIn);
        signUp = (Button) findViewById(R.id.buttonSignUp);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonSignIn)
        {
            Intent i = new Intent(MainActivity.this,SignInScreen.class);
            startActivity(i);
        }

        if(v.getId()==R.id.buttonSignUp)
        {
            Intent i = new Intent(MainActivity.this,SignUpScreen.class);
            startActivity(i);
        }
    }
}
