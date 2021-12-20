package kz.rxample.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    RecyclerView recyclerView;
    RecyclerTagAdapter recyclerAdapter;
    List<String> tagList = new ArrayList<>();
    SharedPreferences sPref;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewMain);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
//        initializer();
        sPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String username = (String)sPref.getAll().get("username");

        String apiUrl = "http://172.27.112.1:8080/tags?username="+username;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    System.out.println("on response");
                    assert response.body() != null;
                    jsonObject = new JSONObject(response.body().string());
                    JSONArray dataArray = jsonObject.getJSONArray("tags");
                    for (int i = 0; i < dataArray.length(); i++) {
                        String tag = (String)dataArray.get(i);
                        tagList.add(tag);
                    }
                    runOnUiThread(() -> {
                        recyclerAdapter = new RecyclerTagAdapter(tagList);
                        recyclerView.setAdapter(recyclerAdapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                String TAG = "An error has occurred " + e;
                Log.e(TAG, "An error has occurred " + e);
            }

        });


        findViewById(R.id.addTag).setOnClickListener(view -> {
            EditText mEdit = findViewById(R.id.inputTag);
            if (mEdit != null && !mEdit.getText().toString().isEmpty()) {
                tagList.add(mEdit.getText().toString());
                OkHttpClient httpClients = new OkHttpClient.Builder().build();
                RequestBody formBody = new FormBody.Builder()
                        .add("tag", mEdit.getText().toString())
                        .add("username", username)
                        .build();
                Request requests = new Request.Builder()
                        .url("http://172.27.112.1:8080/saveTag")
                        .post(formBody)
                        .build();
                httpClients.newCall(requests).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code()==200){
                            runOnUiThread(() -> {
                                recyclerAdapter.notifyItemInserted(tagList.size() - 1);
                                Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
                                mEdit.getText().clear();
                            });
                        }else{
                            runOnUiThread(() -> {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "Profile" :
                        intentMain = new Intent(MainActivity.this,
                                ProfileActivity.class);
                        MainActivity.this.startActivity(intentMain);
                        break;
                    case "Search" :
                        intentMain = new Intent(MainActivity.this,
                                SearchActivity.class);
                        MainActivity.this.startActivity(intentMain);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) { super.onSaveInstanceState(outState); }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

//    @SuppressLint("ClickableViewAccessibility")
//    public void initializer(){
//        // Initializing
//        // Buttons
//        Button button_wonder = findViewById(R.id.buttonTag);
//
//        button_wonder.setOnTouchListener(purrListener);
//
//    }
//
//    public void activitySwitcher(String q){
//        Intent intentMain = new Intent(MainActivity.this ,
//               PressedActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("q", q);
//        intentMain.putExtras(bundle);
//        MainActivity.this.startActivity(intentMain);
//    }
//
//    public void onButtonClick(View view){
//        Button button_wonder = findViewById(R.id.buttonTag);
//        if(view.getId() == R.id.buttonTag){
//            activitySwitcher(button_wonder.getText().toString());
//        }
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    View.OnTouchListener purrListener = (v, event) -> {
//        Button button = findViewById(v.getId());
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            button.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.purring));
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            button.clearAnimation();
//        }
//        return false;
//    };



}