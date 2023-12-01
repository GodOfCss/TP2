package ca.csfoy.tp2.mc;

public class Position {
    private Coordinates xPosition;
    private Coordinates yPosition;

    public Position(Coordinates xPosition, Coordinates yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    public Coordinates getYPosition() {
        return yPosition;
    }

    public Coordinates getXPosition() {
        return xPosition;
    }

    public void setXPosition(Coordinates xPosition){
        this.xPosition = xPosition;
    }

    public void setYPosition(Coordinates yPosition){
        this.yPosition = yPosition;
    }

    public Position getPositionLeft(){
        if(xPosition.ordinal() == 0) return null;
        return new Position(Coordinates.values()[xPosition.ordinal() - 1] , yPosition);
    }

    public Position getPositionRight(){
        if(xPosition.ordinal() == 8) return null;
        return new Position(Coordinates.values()[xPosition.ordinal() + 1] , yPosition);
    }

    public Position getPositionUp(){
        if(yPosition.ordinal() == 0) return null;
        return new Position(xPosition , Coordinates.values()[yPosition.ordinal() - 1]);
    }
    public Position getPositionDown(){
        if(yPosition.ordinal() == 8) return null;
        return new Position(xPosition , Coordinates.values()[yPosition.ordinal() + 1]);
    }

    public boolean equals(Position position){
        if(this.yPosition == position.yPosition && this.xPosition == position.xPosition) return true;

        return false;
    }
}
