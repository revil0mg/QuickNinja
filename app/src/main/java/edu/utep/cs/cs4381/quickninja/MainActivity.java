package edu.utep.cs.cs4381.quickninja;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Action for Play button
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener (view -> {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });

        // Prepare to load fastest time
        SharedPreferences prefs;
        //SharedPreferences.Editor editor;
        prefs = getSharedPreferences("HiScores", MODE_PRIVATE);

        // Load fastest time
        // if not available our high score = 1000000
        long farthestReached = prefs.getLong("hiScore", 0);

        // Get a reference to the TextView in our layout
        TextView textFastestTime = findViewById(R.id.textHiScore);
        // Put the high score in our TextView
        textFastestTime.setText("Farthest Reached: " + farthestReached);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
