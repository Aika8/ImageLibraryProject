package kz.rxample.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button_login = findViewById(R.id.singIn);
        button_login.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick (View v) {
                Intent intentMain = new Intent(LoginActivity.this,
                        MainActivity.class);
                EditText email = findViewById(R.id.editEmail);
                EditText pass = findViewById(R.id.editPass);
//                sPref = getSharedPreferences("myPref", MODE_PRIVATE);
                OkHttpClient httpClient = new OkHttpClient.Builder().build();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", email.getText().toString())
                        .add("password", pass.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://localhost:8080/login")
                        .post(formBody)
                        .build();
                Call call = httpClient.newCall(request);
                try {
                    Response response = call.execute();
                    if (response.code()==200){
                        LoginActivity.this.startActivity(intentMain);
                    }
                    else if(response.code()==201){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage("Password invalid");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage("User not found");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
