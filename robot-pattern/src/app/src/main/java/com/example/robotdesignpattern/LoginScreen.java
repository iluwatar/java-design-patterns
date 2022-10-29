package com.example.robotdesignpattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class
LoginScreen extends AppCompatActivity {

    EditText Email, Password;
    Button Login;
    TextView Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = findViewById(R.id.edt_email);
        Password = findViewById(R.id.edt_pass);
        Result = findViewById(R.id.tv_result);
        Login = findViewById(R.id.btn_login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Email.getText().toString().equalsIgnoreCase("navdeepgill@anu.edu.au")
                && Password.getText().toString().equalsIgnoreCase("sunflowers")) {
                    Result.setText("LOGIN SUCCESS!");
                } else {
                    Result.setText("LOGIN FAILED");
                }

            }
        });
    }
}
