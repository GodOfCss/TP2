package ca.csfoy.tp2.mc;

import java.util.ArrayList;

public class GoModelController {
    private boolean isBlackNext;
    private ArrayList<Integer> playedBlackSpots;
    private ArrayList<Integer> playedWhiteSpots;
    private int lastPlayed;


    public GoModelController(){
        this.isBlackNext = true;
        this.playedWhiteSpots = new ArrayList<Integer>();
        this.playedBlackSpots = new ArrayList<Integer>();
    }

    public void play(int id){
        if(isBlackNext) {
            this.isBlackNext = false;
            this.playedBlackSpots.add(id);
            this.lastPlayed = id;
        }
        else {
            this.isBlackNext = true;
            this.playedWhiteSpots.add(id);
            this.lastPlayed = id;
        }


    }

    public boolean isBlackNext() {
        return isBlackNext;
    }

    public boolean isPlayed(int id) {
        if(playedWhiteSpots.contains(id) || playedBlackSpots.contains(id)){
            return true;
        }
        return false;
    }

    public boolean isBlack(int id){
        if(playedBlackSpots.contains(id)){
            return true;
        }
        return false;
    }

    public int getLastPlayed(){
        return this.lastPlayed;
    }

    public ArrayList<Integer> getPlayedWhiteSpots() {
        return this.playedWhiteSpots;
    }

    public ArrayList<Integer> getPlayedBlackSpots() {
        return this.playedBlackSpots;
    }

    public void setNext(boolean isBlackNext) {
        this.isBlackNext = isBlackNext;
    }

    public void setLastPlayed(int lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public void setWhitePlayed(ArrayList<Integer> playedWhite) {
        this.playedWhiteSpots = playedWhite;
    }

    public void setBlackPlayed(ArrayList<Integer> playedBlack) {
        this.playedBlackSpots = playedBlack;
    }
}
