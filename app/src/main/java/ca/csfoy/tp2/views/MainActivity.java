package ca.csfoy.tp2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    }

    public void onPlayButton(){

    }
    public void onReplayButton(){

    }

}