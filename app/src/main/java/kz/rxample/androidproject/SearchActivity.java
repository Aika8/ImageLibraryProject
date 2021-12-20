package kz.rxample.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ablanco.zoomy.Zoomy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerAdapter;
    private List<ImageView> imageView;
    private ImageView placeholderView;
    private Zoomy.Builder builder;
    ImageButton imageButton;
    String search = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentMain;
                Toast.makeText(SearchActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "Profile" :
                        intentMain = new Intent(SearchActivity.this,
                                ProfileActivity.class);
                        SearchActivity.this.startActivity(intentMain);
                        break;
                    case "home" :
                        intentMain = new Intent(SearchActivity.this,
                                MainActivity.class);
                        SearchActivity.this.startActivity(intentMain);
                        break;
                }
                return true;
            }
        });

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText s = findViewById(R.id.search_panel);
                search = s.getText().toString();
                createRecycleView();
            }
        });

     if(search == null) {
         createRecycleView();
     }

    }

    private void createRecycleView(){
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        fetch_API_Data();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    private ArrayList<String> fetch_API_Data() {
        String apiUrl = "http://172.27.112.1:8080/photos?q="+search;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        ArrayList<String> imageUrl = new ArrayList<>();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    assert response.body() != null;
                    jsonObject = new JSONObject(response.body().string());
                    JSONArray dataArray = jsonObject.getJSONArray("hits");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String url = dataObject.getString("webformatURL");
                        imageUrl.add(url);
                    }
                    runOnUiThread(() -> {
                        recyclerAdapter = new RecyclerViewAdapter(imageUrl);
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

        return imageUrl;
    }
}
