package ca.csfoy.tp2.mc;

public class GoModelController {
    private boolean isBlackNext;

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
