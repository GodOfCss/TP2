package ca.csfoy.tp2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.csfoy.tp2.R;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private Button replayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.playButton = (Button) findViewById(R.id.playButton);
        this.replayButton = (Button) findViewById(R.id.replayButton);

        playButton.setOnClickListener(this::onPlayButton);
        replayButton.setOnClickListener(this::onReplayButton);
    }

    public void onPlayButton(View view){
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }
    public void onReplayButton(View view){
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }

}