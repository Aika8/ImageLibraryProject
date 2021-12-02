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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

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
        Button button_happiness = findViewById(R.id.button_6);
        Button button_heavy = findViewById(R.id.button_2);
        Button button_miss = findViewById(R.id.button_5);
        Button button_sadness = findViewById(R.id.button_3);
        Button button_tiredness = findViewById(R.id.button_4);

        button_wonder.setOnTouchListener(purrListener);
        button_happiness.setOnTouchListener(purrListener);
        button_heavy.setOnTouchListener(purrListener);
        button_miss.setOnTouchListener(purrListener);
        button_sadness.setOnTouchListener(purrListener);
        button_tiredness.setOnTouchListener(purrListener);

        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentMain;
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getTitle().toString()) {
                    case "Favourite" :
                        intentMain = new Intent(MainActivity.this,
                                FavoritesActivity.class);
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

    public void activitySwitcher(String albumID){
        Intent intentMain = new Intent(MainActivity.this ,
               PressedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("albumID", albumID);
        intentMain.putExtras(bundle);
        MainActivity.this.startActivity(intentMain);
    }

    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.button_1:
                activitySwitcher( "axpkkpZ");
                break;
            case R.id.button_2:
                activitySwitcher( "bjtGGNs");
                break;
            case R.id.button_3:
                activitySwitcher("MpAYelh");
                break;
            case R.id.button_4:
                activitySwitcher("M7Apxvf");
                break;
            case R.id.button_5:
                activitySwitcher("Qqzo086");
                break;
            case R.id.button_6:
                activitySwitcher("hYmnbY8");
                break;
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