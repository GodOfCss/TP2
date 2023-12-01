package ca.csfoy.tp2.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.csfoy.tp2.R;
import ca.csfoy.tp2.mc.Coordinates;
import ca.csfoy.tp2.mc.GoModelController;

public class GameActivity extends AppCompatActivity implements GoView {
    public final int GAME_DIMENSIONS = 9;
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
                String currentButtonString = "go_" + Coordinates.values()[i].toString() + Integer.toString(j);
                int id = getResources().getIdentifier(currentButtonString, "id", getPackageName());
                this.goBoard[i][j - 1] = (ImageButton) findViewById(id);
                goBoard[i][j - 1].setOnClickListener(this::OnClickBoard);
            }
        }

        if(savedInstanceState != null){
            controller.setNext(savedInstanceState.getBoolean("IS_BLACK_NEXT"));
            controller.setLastPlayed(savedInstanceState.getString("LAST_PLAYED"));
            controller.setWhitePlayed(savedInstanceState.getStringArrayList("PLAYED_WHITE"));
            controller.setBlackPlayed(savedInstanceState.getStringArrayList("PLAYED_BLACK"));

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("IS_BLACK_NEXT",controller.isBlackNext());
        outState.putStringArrayList("PLAYED_WHITE", controller.getPlayedWhiteSpots());
        outState.putStringArrayList("PLAYED_BLACK", controller.getPlayedBlackSpots());
        outState.putString("LAST_PLAYED", controller.getLastPlayed());
        super.onSaveInstanceState(outState);
    }

    private void OnPlay(View view) {
        if(currentPlay != null){
            controller.play(getPositionFromResource(currentPlay.getId()));
            currentPlay = null;
        }
        this.update();

    }

    private String getPositionFromResource(int id) {
        ImageButton currentButton = findViewById(id);
        String position = currentButton.getTag().toString();
        return position;
    }

    private ImageButton getButtonFromPosition(String position){
        int xPosition = -1;
        int yPosition = Character.getNumericValue(position.charAt(1)) - 1;
        for(int i = 0; i < Coordinates.values().length; i++){
            if(Coordinates.values()[i] == Coordinates.valueOf(String.valueOf(position.charAt(0)))) xPosition = i;
        }
        return goBoard[xPosition][yPosition];
    }


    private void OnClickBoard(View view) {
        this.update();
        ImageButton currentButton = (ImageButton) view;
        int id = currentButton.getId();
        if(!controller.isPlayed(getPositionFromResource(id))) {
            if (controller.isBlackNext()) {
                currentButton.setImageResource(R.mipmap.go_black_piece);
                currentButton.setImageAlpha(150);
            } else {
                currentButton.setImageResource(R.mipmap.go_white_piece);
                currentButton.setImageAlpha(150);
            }

            this.currentPlay = currentButton;
        }
        else {
            currentPlay = null;
        }
    }

    public void update() {
        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 0; j < GAME_DIMENSIONS; j++){
                int currentId = goBoard[i][j].getId();
                if(controller.isPlayed(getPositionFromResource(currentId))) {
                    if(controller.isBlack(getPositionFromResource(currentId))){
                        goBoard[i][j].setImageResource(R.mipmap.go_black_piece);
                        goBoard[i][j].setImageAlpha(255);
                    }
                    else{
                        goBoard[i][j].setImageResource(R.mipmap.go_white_piece);
                        goBoard[i][j].setImageAlpha(255);
                    }
                }
                else{
                    goBoard[i][j].setImageDrawable(null);
                }
            }
        }

        String lastPosition = controller.getLastPlayed();
        if(lastPosition != "" && lastPosition != null){
            ImageButton lastPlayed = getButtonFromPosition(lastPosition);
            if(lastPlayed != null) {
                if (controller.isBlack(lastPosition)) {
                    lastPlayed.setImageResource(R.mipmap.go_black_piece_play);
                } else {
                    lastPlayed.setImageResource(R.mipmap.go_white_piece_play);
                }
            }
        }
    }
}