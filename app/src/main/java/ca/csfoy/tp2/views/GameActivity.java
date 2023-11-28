package ca.csfoy.tp2.views;

import androidx.annotation.NonNull;
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
    private ImageButton currentPlay;
    private GoModelController controller = new GoModelController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        this.playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this::OnPlay);

        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 1; j <= GAME_DIMENSIONS; j++) {
                String currentButtonString = "go_" + LETTERS[i] + Integer.toString(j);
                int id = getResources().getIdentifier(currentButtonString, "id", getPackageName());
                this.goBoard[i][j - 1] = (ImageButton) findViewById(id);
                goBoard[i][j - 1].setOnClickListener(this::OnClickBoard);
            }
        }

        if(savedInstanceState != null){
            controller.setNext(savedInstanceState.getBoolean("IS_BLACK_NEXT"));
            controller.setLastPlayed(savedInstanceState.getInt("LAST_PLAYED"));
            controller.setWhitePlayed(savedInstanceState.getIntegerArrayList("PLAYED_WHITE"));
            controller.setBlackPlayed(savedInstanceState.getIntegerArrayList("PLAYED_BLACK"));

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("IS_BLACK_NEXT",controller.isBlackNext());
        outState.putIntegerArrayList("PLAYED_WHITE", controller.getPlayedWhiteSpots());
        outState.putIntegerArrayList("PLAYED_BLACK", controller.getPlayedBlackSpots());
        outState.putInt("LAST_PLAYED", controller.getLastPlayed());
        super.onSaveInstanceState(outState);
    }

    private void OnPlay(View view) {
        if(currentPlay != null){
            controller.play(currentPlay.getId());
        }
        this.update();

    }

    private void OnClickBoard(View view) {
        this.update();
        ImageButton currentButton = (ImageButton) view;
        int id = currentButton.getId();
        if(controller.isBlackNext()){
            currentButton.setImageResource(R.mipmap.go_black_piece);
        }
        else{
            currentButton.setImageResource(R.mipmap.go_white_piece);
        }

        this.currentPlay = currentButton;
    }

    public void update() {
        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 0; j < GAME_DIMENSIONS; j++){
                int currentId = goBoard[i][j].getId();
                if(controller.isPlayed(currentId)) {
                    if(controller.isBlack(currentId)){
                        goBoard[i][j].setImageResource(R.mipmap.go_black_piece);
                    }
                    else{
                        goBoard[i][j].setImageResource(R.mipmap.go_white_piece);
                    }
                }
                else{
                    goBoard[i][j].setImageDrawable(null);
                }
            }
        }

        int lastID = controller.getLastPlayed();
        ImageButton lastPlayed = findViewById(lastID);
        if(lastPlayed != null) {
            if (controller.isBlack(lastID)) {
                lastPlayed.setImageResource(R.mipmap.go_black_piece_play);
            } else {
                lastPlayed.setImageResource(R.mipmap.go_white_piece_play);
            }
        }
    }
}