package kz.rxample.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;
    SharedPreferences sPref;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        sPref = getSharedPreferences("myPref", MODE_PRIVATE);
        username = (String)sPref.getAll().get("username");
        TextInputEditText name = (TextInputEditText)findViewById(R.id.fullname_field);
        TextInputEditText number = (TextInputEditText)findViewById(R.id.number);
        TextInputEditText pass = (TextInputEditText)findViewById(R.id.password);
        TextView usernameView = findViewById(R.id.username_field);

        String apiUrl = "http://172.27.112.1:8080/user?username="+username;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject jsonObject = null;

                    System.out.println("on response");
                    assert response.body() != null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject finalJsonObject = jsonObject;
                    runOnUiThread(() -> {
                        try {
                            name.setText((String)finalJsonObject.get("name"), TextView.BufferType.EDITABLE);
                            number.setText((String)finalJsonObject.get("number"), TextView.BufferType.EDITABLE);
                            pass.setText((String)finalJsonObject.get("password"), TextView.BufferType.EDITABLE);
                            usernameView.setText(username, TextView.BufferType.EDITABLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.fullname_field);
                EditText number = findViewById(R.id.number);
                EditText pass = findViewById(R.id.password);


                OkHttpClient httpClient = new OkHttpClient.Builder().build();
                RequestBody formBody = new FormBody.Builder()
                        .add("name", name.getText().toString())
                        .add("number", number.getText().toString())
                        .add("password", pass.getText().toString())
                        .add("username", username)
                        .build();
                Request request = new Request.Builder()
                        .url("http://172.23.160.1:8080/updateUser")
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
                            runOnUiThread(() -> {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                                alertDialogBuilder.setMessage("Updated!");
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            });
                        }else{
                            runOnUiThread(() -> {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                                alertDialogBuilder.setMessage("Something went wrong...");
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            });
                        }
                    }
                });
            }
        });


        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentMain;
                Toast.makeText(ProfileActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "home" :
                        intentMain = new Intent(ProfileActivity.this,
                                MainActivity.class);
                        ProfileActivity.this.startActivity(intentMain);
                        break;
                    case "Search" :
                        intentMain = new Intent(ProfileActivity.this,
                                SearchActivity.class);
                        ProfileActivity.this.startActivity(intentMain);
                        break;
                }
                return true;
            }
        });

    }

}
