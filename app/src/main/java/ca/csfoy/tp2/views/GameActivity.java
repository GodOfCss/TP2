package ca.csfoy.tp2.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import ca.csfoy.tp2.R;
import ca.csfoy.tp2.mc.Coordinates;
import ca.csfoy.tp2.mc.GoModelController;
import ca.csfoy.tp2.mc.Teams;

public class GameActivity extends AppCompatActivity implements GoView {
    public final int GAME_DIMENSIONS = 9;
    private ImageButton[][] goBoard = new ImageButton[GAME_DIMENSIONS][GAME_DIMENSIONS];

    private boolean isWinnerAnnounced = false;
    private Button resignButton;
    private Button cancelButton;
    private Button playButton;
    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageButton currentPlay;
    private GoModelController controller = new GoModelController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        this.resignButton = findViewById(R.id.resignButton);
        this.playButton = findViewById(R.id.playButton);
        this.cancelButton = findViewById(R.id.cancelButton);
        this.forwardButton = findViewById(R.id.forwardButton);
        this.backButton = findViewById(R.id.backButton);

        playButton.setOnClickListener(this::OnPlay);
        resignButton.setOnClickListener(this::OnResign);
        cancelButton.setOnClickListener(this::OnCancel);
        backButton.setOnClickListener(this::OnBack);
        forwardButton.setOnClickListener(this::OnForward);

        for(int i = 0; i < GAME_DIMENSIONS; i++){
            for(int j = 1; j <= GAME_DIMENSIONS; j++) {
                String currentButtonString = "go_" + Coordinates.values()[i].toString() + Integer.toString(j);
                int id = getResources().getIdentifier(currentButtonString, "id", getPackageName());
                this.goBoard[i][j - 1] = (ImageButton) findViewById(id);
                goBoard[i][j - 1].setOnClickListener(this::OnClickBoard);
            }
        }

        if(savedInstanceState != null){
            this.isWinnerAnnounced = savedInstanceState.getBoolean("WINNER_ANNOUNCED");
            controller.setNext(savedInstanceState.getBoolean("IS_BLACK_NEXT"));
            controller.setLastPlayed(savedInstanceState.getString("LAST_PLAYED"));
            controller.setWhitePlayed(savedInstanceState.getStringArrayList("PLAYED_WHITE"));
            controller.setBlackPlayed(savedInstanceState.getStringArrayList("PLAYED_BLACK"));
            controller.setOffset(savedInstanceState.getInt("CURRENT_HISTORY_OFFSET"));
            controller.updatePlayedSpots();

            if(savedInstanceState.getBoolean("HAS_BLACK_WON")){
                controller.setWinner(Teams.BLACK);
            }
            else if(savedInstanceState.getBoolean("HAS_WHITE_WON")){
                controller.setWinner(Teams.WHITE);
            }
            else{
                controller.setWinner(null);
            }

        }
        this.update();

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("HAS_WHITE_WON", controller.hasWhiteWon());
        outState.putBoolean("HAS_BLACK_WON", controller.hasBlackWon());
        outState.putBoolean("IS_BLACK_NEXT",controller.isBlackNext());
        outState.putStringArrayList("PLAYED_WHITE", controller.getPlayedWhiteSpots());
        outState.putStringArrayList("PLAYED_BLACK", controller.getPlayedBlackSpots());
        outState.putString("LAST_PLAYED", controller.getLastPlayed());
        outState.putInt("CURRENT_HISTORY_OFFSET", controller.getOffset());
        outState.putBoolean("WINNER_ANNOUNCED", this.isWinnerAnnounced);
        super.onSaveInstanceState(outState);
    }

    private void OnPlay(View view) {
        if(controller.getOffset() == 0) {
            if (currentPlay != null) {
                controller.play(getPositionFromResource(currentPlay.getId()));
                currentPlay = null;
            }
        } else {;
            controller.play("nullPos");
            playButton.setText("Play");
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
        if(!controller.isGameOver() && controller.getOffset() == 0){
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
    }

    public void update() {
        if(controller.getOffset() != 0) playButton.setText("Back To Play");
        else  playButton.setText("Play");

        if(controller.isGameOver() && !isWinnerAnnounced){
            this.isWinnerAnnounced = true;
            if(controller.hasBlackWon()){
                blackWin();
            } else {
                whiteWin();
            }
            resignButton.setText("New Game");
            resignButton.setOnClickListener(this::OnNewGame);
            cancelButton.setOnClickListener(null);
        }
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

        String lastPosition = controller.getLastPlayedInHistory();
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

    private void OnNewGame(View view) {
        this.controller = new GoModelController();
        resignButton.setOnClickListener(this::OnResign);
        cancelButton.setOnClickListener(this::OnCancel);
        resignButton.setText("Resign");
        this.update();
        this.isWinnerAnnounced = false;
    }

    private void OnResign(View view) {
        if(!controller.isBlackNext()){
            controller.setWinner(Teams.BLACK);
        }
        else {
            controller.setWinner(Teams.WHITE);
        }
        this.update();
    }

    private void blackWin() {
        Snackbar snackbar = Snackbar.make(this, resignButton, "Black has won!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void whiteWin() {
        Snackbar snackbar = Snackbar.make(this, resignButton, "White has won!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void OnCancel(View view) {
        controller.cancel();
        this.update();
    }

    private void OnForward(View view) {
        controller.forward();
        this.update();

    }

    private void OnBack(View view) {
        controller.back();
        this.update();
    }


}