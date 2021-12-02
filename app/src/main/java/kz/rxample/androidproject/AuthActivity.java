package kz.rxample.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Button button_login = findViewById(R.id.button2);
        Button button_reg = findViewById(R.id.button3);

        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intentMain = new Intent(AuthActivity.this,
                        LoginActivity.class);
                AuthActivity.this.startActivity(intentMain);
            }
        });
        button_reg.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                Intent intentMain = new Intent(AuthActivity.this,
                        RegActivity.class);
                AuthActivity.this.startActivity(intentMain);
            }
        });
    }
}
