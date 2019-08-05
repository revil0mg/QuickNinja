package edu.utep.cs.cs4381.quickninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
