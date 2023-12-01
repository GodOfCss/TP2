package ca.csfoy.tp2.mc;

public class Position {
    private Coordinates YPosition;
    private Coordinates XPosition;

    public Position(Coordinates XPosition, Coordinates YPosition){
        this.YPosition = YPosition;
        this.XPosition = XPosition;
    }
    public Coordinates getYPosition() {
        return YPosition;
    }

    public Coordinates getXPosition() {
        return XPosition;
    }

    public void setXPosition(Coordinates xPosition){
        this.XPosition = xPosition;
    }

    public void setYPosition(Coordinates yPosition){
        this.YPosition = yPosition;
    }

    public Position getPositionLeft(){
        if(XPosition.ordinal() == 0) return null;
        return new Position(Coordinates.values()[XPosition.ordinal() - 1] , YPosition);
    }

    public Position getPositionRight(){
        if(XPosition.ordinal() == 8) return null;
        return new Position(Coordinates.values()[XPosition.ordinal() + 1] , YPosition);
    }

    public Position getPositionUp(){
        if(YPosition.ordinal() == 0) return null;
        return new Position(XPosition, Coordinates.values()[YPosition.ordinal() - 1]);
    }
    public Position getPositionDown(){
        if(YPosition.ordinal() == 8) return null;
        return new Position(XPosition, Coordinates.values()[YPosition.ordinal() + 1]);
    }

    public boolean equals(Position position){
        if(this.XPosition == position.XPosition && this.YPosition == position.YPosition) return true;

        return false;
    }
}
