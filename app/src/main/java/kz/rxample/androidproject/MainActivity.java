package kz.rxample.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    RecyclerView recyclerView;
    RecyclerTagAdapter recyclerAdapter;
    List<String> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) { super.onSaveInstanceState(outState); }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

    @SuppressLint("ClickableViewAccessibility")
    public void initializer(){
        // Initializing
        // Buttons
        Button button_wonder = findViewById(R.id.button_1);

        button_wonder.setOnTouchListener(purrListener);

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

    public void activitySwitcher(String q){
        Intent intentMain = new Intent(MainActivity.this ,
               PressedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("q", q);
        intentMain.putExtras(bundle);
        MainActivity.this.startActivity(intentMain);
    }

    public void onButtonClick(View view){
        Button button_wonder = findViewById(R.id.button_1);
        if(view.getId() == R.id.button_1){
            activitySwitcher(button_wonder.getText().toString());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener purrListener = (v, event) -> {
        Button button = findViewById(v.getId());
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            button.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.purring));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            button.clearAnimation();
        }
        return false;
    };



}