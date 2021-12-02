package kz.rxample.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoritesActivity extends AppCompatActivity {

    BottomNavigationView bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intentMain;
                Toast.makeText(FavoritesActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "home" :
                        intentMain = new Intent(FavoritesActivity.this,
                                MainActivity.class);
                        FavoritesActivity.this.startActivity(intentMain);
                        break;
                    case "Search" :
                        intentMain = new Intent(FavoritesActivity.this,
                                SearchActivity.class);
                        FavoritesActivity.this.startActivity(intentMain);
                        break;
                }
                return true;
            }
        });
    }
}
