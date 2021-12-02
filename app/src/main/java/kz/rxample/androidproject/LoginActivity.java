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

import java.util.Map;
import java.util.Objects;

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
                sPref = getSharedPreferences("myPref", MODE_PRIVATE);

                System.out.println(email);
                System.out.println(pass);
                System.out.println(sPref.getAll().get("ertaev@gmail.com"));
                System.out.println(sPref.getAll().get(email.getText().toString()));
                System.out.println(sPref.getAll().toString());
                System.out.println(sPref.getAll());

                if (sPref.getAll().get(email.getText().toString()) != null && sPref.getAll().get(email.getText().toString()).equals(pass.getText().toString())){
                    LoginActivity.this.startActivity(intentMain);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage("Login invalid");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }

}
