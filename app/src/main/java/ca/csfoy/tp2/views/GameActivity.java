package ca.csfoy.tp2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.csfoy.tp2.R;
import ca.csfoy.tp2.mc.GoModelController;

public class GameActivity extends AppCompatActivity implements GoView {
    public final int GAME_DIMENSIONS = 9;
    private final char[] LETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
    private ImageButton[][] goBoard = new ImageButton[GAME_DIMENSIONS][GAME_DIMENSIONS];

    private Button playButton;
    private GoModelController controller = new GoModelController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        this.playButton = findViewById(R.id.playButton);

        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 1; j <= GAME_DIMENSIONS; j++){
                String currentButtonString = "go_" + LETTERS[i] + Integer.toString(j);
                int id = getResources().getIdentifier(currentButtonString, "id", getPackageName());
                this.goBoard[i][j-1] = (ImageButton)findViewById(id);
                goBoard[i][j-1].setOnClickListener(this::OnClickBoard);
            }
        }

    }

    private void OnClickBoard(View view) {

    }

    public void update() {
        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 1; j <= GAME_DIMENSIONS; j++){

            }
        }
    }
}