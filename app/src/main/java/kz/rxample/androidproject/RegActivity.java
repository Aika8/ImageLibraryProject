package kz.rxample.androidproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Button button_reg = findViewById(R.id.buttonAcount);
        button_reg.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick (View v) {
                Intent intentMain = new Intent(RegActivity.this,
                        LoginActivity.class);
                EditText email = findViewById(R.id.editEmail);
                EditText pass = findViewById(R.id.editPass);
                EditText number = findViewById(R.id.editNumber);
                EditText name = findViewById(R.id.editName);
//                sPref = getSharedPreferences("myPref", MODE_PRIVATE);
                OkHttpClient httpClient = new OkHttpClient.Builder().build();
                RequestBody formBody = new FormBody.Builder()
                        .add("email", email.getText().toString())
                        .add("password", pass.getText().toString())
                        .add("number", number.getText().toString())
                        .add("name", name.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url("http://172.27.112.1:8080/reg")
                        .post(formBody)
                        .build();
                httpClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code()==200){
                            RegActivity.this.startActivity(intentMain);
                        }
                        else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegActivity.this);
                            alertDialogBuilder.setMessage("User exist");
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                });

            }
        });

    }
}
