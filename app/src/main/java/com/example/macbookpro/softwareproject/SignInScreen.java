package com.example.macbookpro.softwareproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInScreen extends Activity implements View.OnClickListener{

    EditText getUsername,getPassword;
    Button signIn;
    String givenUserName , givenPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        Initialize();
    }

    public void Initialize()
    {
        getUsername = (EditText) findViewById(R.id.editSignInUsername);
        getPassword = (EditText) findViewById(R.id.editSignInPassword);
        signIn = (Button) findViewById(R.id.buttonSignInScreen);

        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonSignInScreen)
        {
            //create custom thread which connects with app engine servlet.
            ClientAsync client = new ClientAsync(this);
            setRequestValues();
            Log.i("USER = ", givenUserName + givenPassword);
            client.execute(MainActivity.OPERATION_SIGNIN,givenUserName,givenPassword);
        }
    }

    private void setRequestValues() {
        givenUserName = getUsername.getText().toString().toLowerCase();
        givenPassword = getPassword.getText().toString();
    }
}
