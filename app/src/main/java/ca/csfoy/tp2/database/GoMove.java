package ca.csfoy.tp2.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import ca.csfoy.tp2.mc.Coordinates;
import ca.csfoy.tp2.mc.Position;

public class GoMove implements Parcelable {
    private Position position;
    private String color;

    public GoMove(Position position, String color){
        this.position = position;
        this.color = color;
    }

    public GoMove(){};

    protected GoMove(Parcel in) {
        color = in.readString();
    }

    public static final Creator<GoMove> CREATOR = new Creator<GoMove>() {
        @Override
        public GoMove createFromParcel(Parcel in) {
            return new GoMove(in);
        }

        @Override
        public GoMove[] newArray(int size) {
            return new GoMove[size];
        }
    };

    public void setPosition(String position){

        Coordinates xPosition = null;
        Coordinates yPosition = null;

        for (int i = 0; i < Coordinates.values().length; i++) {
            if (Coordinates.values()[i] == Coordinates.valueOf(String.valueOf(position.charAt(0))))
                xPosition = Coordinates.values()[i];
            if (Coordinates.values()[i] == Coordinates.valueOf(String.valueOf(position.charAt(1))))
                yPosition = Coordinates.values()[i];
        }

        this.position = new Position(xPosition, yPosition);
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    public String getPositionString(){
        String positionString = "";

        positionString += position.getXPosition().toString();
        positionString += position.getYPosition().toString();

        return positionString;
    }

    public Position getPosition(){
        return this.position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(color);
    }
}
