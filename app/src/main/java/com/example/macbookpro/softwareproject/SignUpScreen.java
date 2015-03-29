package com.example.macbookpro.softwareproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class SignUpScreen extends Activity implements View.OnClickListener{

    EditText getUsername,getPassword;
    Button signUp;
    String givenUserName , givenPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Initialize();
    }

    public void Initialize()
    {
        getUsername = (EditText) findViewById(R.id.editSignUpUsername);
        getPassword = (EditText) findViewById(R.id.editSignUpPassword);
        signUp = (Button) findViewById(R.id.buttonSignUpScreen);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.buttonSignUpScreen)
        {
            setRequestValues();
            if(checkPasswordValid(givenPassword)){
                //create custom thread which connects with app engine servlet.
                ClientAsync client = new ClientAsync(this);
                Log.i("USER = ",givenUserName + givenPassword);
                client.execute(MainActivity.OPERATION_SIGNUP,givenUserName,givenPassword);
            }else{
                Toast.makeText(this,"Password has to include at least one Lower Case " +
                        ",Upper Case Letter and Special Char and its length has to greater than 6",Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean checkPasswordValid(String givenPassword) {
        if(givenPassword.length() >= 6){
            boolean scoreUpperCase = false;
            boolean scoreLowerCase = false;
            boolean scoreSpecial = false;
            for (int i=0 ; i < givenPassword.length(); i++){
                int asciiValue = (int) givenPassword.charAt(i);
                if (scoreLowerCase == false){
                    if (asciiValue >= 97 && asciiValue <= 122 )
                        scoreLowerCase = true;
                }
                if (scoreUpperCase == false){
                    if (asciiValue >= 65 && asciiValue <= 90)
                        scoreUpperCase = true;
                }
                if (scoreSpecial == false){
                    if ( (asciiValue >= 33 && asciiValue <= 47) || (asciiValue >= 58 && asciiValue <= 64) ){
                        scoreSpecial = true;
                    }
                }
                if (scoreLowerCase == true && scoreUpperCase ==true && scoreSpecial==true){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void setRequestValues() {
        givenUserName = getUsername.getText().toString().toLowerCase();
        givenPassword = getPassword.getText().toString();
    }
}
class ClientAsync extends AsyncTask<String,Void,String>{

    Context context;
    //constructor.
    public ClientAsync(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strs) {
        String response = "";
        try{
            //url of the server
            //dont forget to change project id
            URL url = new URL("https://xxxx.appspot.com/"+strs[0]);


            //encoding parameters !
            //UTF-8 help us to handle turkish chars.
            // for instance param = "username=sercansensulun&userpassword=123123"
            String param = User.USER_NAME+"="+ URLEncoder.encode(strs[1],"UTF-8")
                    +"&" +User.USER_PASSWORD+"="+URLEncoder.encode(strs[2],"UTF-8");

            //opening a connection
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            //set the connecton for UPLOADING data
            connection.setDoOutput(true);

            //set the connection for POST kind
            connection.setRequestMethod("POST");

            //set the length to be fixded according to Android documantation suggestion
            connection.setFixedLengthStreamingMode(param.getBytes().length);

            //for safety
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            //send the POST out
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.close();

            //parsing the response with the Scanner class
            Scanner inStream = new Scanner(connection.getInputStream());
            while (inStream.hasNextLine())
                response += ( inStream.nextLine() );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    //handle to return value which comes from server side.
    @Override
    protected void onPostExecute(String s) {
        Log.i("RESULT : ", s.toString());
        Toast.makeText(this.context,s,Toast.LENGTH_LONG).show();
    }
}
