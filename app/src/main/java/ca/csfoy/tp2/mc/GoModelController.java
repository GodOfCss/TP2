package ca.csfoy.tp2.mc;

import java.util.ArrayList;

public class GoModelController {
    private boolean isBlackNext;
    private ArrayList<String> playedSpots;

    public GoModelController(){
        this.isBlackNext = true;
    }

    public void play(){

        if(isBlackNext) this.isBlackNext = false;
        else this.isBlackNext = true;
    }

    public boolean isBlackNext() {
        return isBlackNext;
    }

}
