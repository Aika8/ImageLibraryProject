package kz.rxample.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ablanco.zoomy.Zoomy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PressedActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageView placeholderView;
    private Zoomy.Builder builder;
    private Bundle bundle;
    BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        setContentView(R.layout.activity_pressed);

        Image_Setter();
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentMain;
                Toast.makeText(PressedActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "home" :
                        intentMain = new Intent(PressedActivity.this,
                                MainActivity.class);
                        PressedActivity.this.startActivity(intentMain);
                        break;
                    case "Search" :
                        intentMain = new Intent(PressedActivity.this,
                                SearchActivity.class);
                        PressedActivity.this.startActivity(intentMain);
                        break;
                    case "Favourite" :
                        intentMain = new Intent(PressedActivity.this,
                                FavoritesActivity.class);
                        PressedActivity.this.startActivity(intentMain);
                }
                return true;
            }
        });
    }

    /*
        All functions described below are executed in order to maintain Text Setter to work properly

        Text_Setter - function that loads both upper and footer text
        Return_Random_Line - function that takes file name and start and end point if needed and then returns random line from given file
        copyListener - listens to long click on text that has benn set and then copying it to clipboard
     */



    private String Return_Random_Line(String fileName) {
        Scanner s = null;
        try {
            s = new Scanner(getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = new ArrayList<String>();
        try {
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
        } finally {
            s.close();
        }
        if (list.size() > 0)
            return list.get(new Random().nextInt(list.size()));
        else
            return "";
    }





    /*
        All functions described below are executed in order to maintain Image Setter to work properly

        fetch_API_Data - function that takes album ID and send a call to Imgur API in order to receive
            an album JSON file and extract id of every picture in album. Returns a random URL of picture in given album
        Image_Loader - gets an URL of a random picture from album and loads it to ImageView
            on Success takes loading animation down
     */

    private void Image_Setter() {
        imageView = (ImageView) findViewById(R.id.photo_view);
        builder = new Zoomy.Builder(this).target(imageView).enableImmersiveMode(false);
        fetch_API_Data(bundle.getString("albumID"));

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        animation.setDuration(1000);
        placeholderView = (ImageView) findViewById(R.id.placeholder_view);
        placeholderView.setImageResource(R.drawable.loading);
        placeholderView.startAnimation(animation);
    }

    private void fetch_API_Data(String albumUrl) {
        String apiUrl = "https://api.imgur.com/3/album/" + albumUrl + "/images";
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        ArrayList<String> imageIDs = new ArrayList<>();

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Client-ID 2878120ad1ed453")
                .header("User-Agent", "ainalaiyn")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    assert response.body() != null;
                    jsonObject = new JSONObject(response.body().string());
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String id = dataObject.getString("id");
                        imageIDs.add(id);
                    }
                    Image_Loader(imageIDs, albumUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                String TAG = "An error has occurred " + e;
                Log.e(TAG, "An error has occurred " + e);
                Image_Loader(imageIDs, albumUrl);
            }

        });
    }

    public void Image_Loader(ArrayList<String> imageIDs, String albumUrl) {
        String pictureUrl;

        if (imageIDs.size() > 0) {
            pictureUrl = imageIDs.get(new Random().nextInt(imageIDs.size()));
        } else {
            pictureUrl = Return_Random_Line("urls_" + albumUrl + ".txt");
        }

        this.runOnUiThread(() -> {
            System.out.println(pictureUrl);
            Picasso.get()
                    .load("https://i.imgur.com/" + pictureUrl + ".jpg")
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            builder.register();
                            placeholderView.setImageResource(0);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load("https://i.imgur.com/" + pictureUrl + ".jpg")
                                    .into(imageView, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            builder.register();
                                            placeholderView.setImageResource(0);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Picasso.get()
                                                    .load(R.drawable.pic_offline_nocache)
                                                    .into(imageView, new com.squareup.picasso.Callback() {
                                                        @SuppressLint("SetTextI18n")
                                                        @Override
                                                        public void onSuccess() {
                                                            builder.register();
                                                            placeholderView.setImageResource(0);
                                                        }

                                                        @SuppressLint("SetTextI18n")
                                                        @Override
                                                        public void onError(Exception e) {
                                                            placeholderView.setImageResource(0);
                                                        }
                                                    });
                                        }
                                    });
                        }

                    });
        });
    }
}