package com.pcschool.mygooglemap;


import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 10;
    private TextView textView,textView2;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        //  Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button) findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


        // ATTENTION: This was auto-generated to handle app links.
      //  Intent appLinkIntent = getIntent();
      //  String appLinkAction = appLinkIntent.getAction();
      //  Uri appLinkData = appLinkIntent.getData();
    }

    private void validate(String userName, String userPassword) {
        if((userName.equals("D12345")&&(userPassword.equals("12345")))){
         //   Intent intent = new Intent(LoginActivity.this,OtherActivity.class);
         //   startActivity(intent);
        }else{
            counter--;

            Info.setText("No of attempts remaining:"+String.valueOf(counter));

            if(counter==0){
                Login.setEnabled(false);
            }
        }
    }


}
