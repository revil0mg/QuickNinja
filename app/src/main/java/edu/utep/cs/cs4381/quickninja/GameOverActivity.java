package edu.utep.cs.cs4381.quickninja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        int score = intent.getExtras().getInt("score");
        long hiScore = intent.getExtras().getLong("hiScore");

        TextView scoreTextView = (TextView) findViewById(R.id.scoreText);
        TextView hiScoreTextView = (TextView) findViewById(R.id.hiScoreText);

        Log.e("======", String.valueOf(score));
        scoreTextView.setText(String.valueOf(score));
        hiScoreTextView.setText(String.valueOf(hiScore));

        // Action for Play Again button
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener (view -> {
            finish();
        });

        // Action for Home button
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener (view -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });


    }
}
