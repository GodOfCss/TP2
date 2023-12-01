package ca.csfoy.tp2.mc;

import java.util.ArrayList;

public class GoModelController {
    private boolean isBlackNext;

    private ArrayList<Position> playedBlackSpots;
    private ArrayList<Position> playedWhiteSpots;
    private ArrayList<Position> playedSpots;
    private Position lastPlayed;


    public GoModelController() {
        this.isBlackNext = true;
        this.playedWhiteSpots = new ArrayList<Position>();
        this.playedBlackSpots = new ArrayList<Position>();
        this.playedSpots = new ArrayList<Position>();

    }

    public void play(String position) {
        if (isBlackNext) {
            this.isBlackNext = false;
            this.playedBlackSpots.add(stringToPosition(position));
            this.playedSpots.add(stringToPosition(position));
            this.lastPlayed = stringToPosition(position);
            this.captureBlack();
        } else {
            this.isBlackNext = true;
            this.playedWhiteSpots.add(stringToPosition(position));
            this.playedSpots.add(stringToPosition(position));
            this.lastPlayed = stringToPosition(position);
        }

    }

    private Position stringToPosition(String position) {
        Coordinates yPosition = null;
        Coordinates xPosition = null;

        yPosition = Coordinates.values()[Character.getNumericValue(position.charAt(1)) - 1];

        for (int i = 0; i < Coordinates.values().length; i++) {
            if (Coordinates.values()[i] == Coordinates.valueOf(String.valueOf(position.charAt(0))))
                xPosition = Coordinates.values()[i];
        }

        return new Position(xPosition, yPosition);
    }

    public boolean isBlackNext() {
        return isBlackNext;
    }

    public boolean isPlayed(String id) {
        if (isBlack(id) || isWhite(id)) {
            return true;
        }
        return false;
    }

    private boolean isWhite(String id) {
        Position currentPosition = stringToPosition(id);
        for (int i = 0; i < playedWhiteSpots.size(); i++) {
            if (playedWhiteSpots.get(i).getXPosition() == currentPosition.getXPosition() && playedWhiteSpots.get(i).getYPosition() == currentPosition.getYPosition()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlack(String id) {
        Position currentPosition = stringToPosition(id);
        for (int i = 0; i < playedBlackSpots.size(); i++) {
            if (playedBlackSpots.get(i).getXPosition() == currentPosition.getXPosition() && playedBlackSpots.get(i).getYPosition() == currentPosition.getYPosition()) {
                return true;
            }
        }
        return false;
    }

    public String getLastPlayed() {
        if (this.lastPlayed == null) return null;
        return positionToString(this.lastPlayed);
    }

    public ArrayList<String> getPlayedWhiteSpots() {
        ArrayList<String> playedWhite = new ArrayList<String>();

        for (int i = 0; i < playedWhiteSpots.size(); i++) {
            playedWhite.add(positionToString(playedWhiteSpots.get(i)));
        }

        return playedWhite;
    }

    private String positionToString(Position position) {
        String positionString = "";

        positionString += position.getXPosition().toString();
        positionString += position.getYPosition().ordinal() + 1;

        return positionString;
    }

    public ArrayList<String> getPlayedBlackSpots() {
        ArrayList<String> playedWhite = new ArrayList<String>();

        for (int i = 0; i < playedWhiteSpots.size(); i++) {
            playedWhite.add(positionToString(playedWhiteSpots.get(i)));
        }

        return playedWhite;
    }

    public void setNext(boolean isBlackNext) {
        this.isBlackNext = isBlackNext;
    }

    public void setLastPlayed(String lastPlayed) {
        this.lastPlayed = stringToPosition(lastPlayed);
    }

    public void setWhitePlayed(ArrayList<String> playedWhite) {
        for (int i = 0; i < playedWhite.size(); i++) {
            playedWhiteSpots.add(stringToPosition(playedWhite.get(i)));
        }
    }

    public void setBlackPlayed(ArrayList<String> playedBlack) {
        for (int i = 0; i < playedBlack.size(); i++) {
            playedBlackSpots.add(stringToPosition(playedBlack.get(i)));
        }
    }

    public void captureBlack() {
        if(lastPlayed != null) {
            ArrayList<Position> positionCapturedPieces = new ArrayList<Position>();

            Coordinates currentY = lastPlayed.getYPosition();
            Coordinates currentX = lastPlayed.getXPosition();

            for (int i = 0; i < playedWhiteSpots.size(); i++) {
                Position pieceToCheck = playedWhiteSpots.get(i);

                if (lastPlayed.getPositionLeft() != null) {
                    if (lastPlayed.getPositionLeft().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }
                if (lastPlayed.getPositionRight() != null) {
                    if (lastPlayed.getPositionRight().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }
                if (lastPlayed.getPositionUp() != null) {
                    if (lastPlayed.getPositionUp().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }
                if (lastPlayed.getPositionDown() != null) {
                    if (lastPlayed.getPositionDown().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }

            }
        }
    }

    private void capturePieces(ArrayList<Position> positionCapturedPieces) {
        if(positionCapturedPieces != null){
            for(int i = 0; i < positionCapturedPieces.size(); i++){
                Position currentPiece = positionCapturedPieces.get(i);
                playedSpots.removeIf(playedSpots -> playedSpots.equals(currentPiece));
            }

            for(int i = 0; i < positionCapturedPieces.size(); i++){
                Position currentPiece = positionCapturedPieces.get(i);
                playedWhiteSpots.removeIf(playedWhiteSpots -> playedWhiteSpots.equals(currentPiece));
            }

            for(int i = 0; i < positionCapturedPieces.size(); i++){
                Position currentPiece = positionCapturedPieces.get(i);
                playedBlackSpots.removeIf(playedBlackSpots -> playedBlackSpots.equals(currentPiece));
            }
        }
    }

    private ArrayList<String> getStringsFromPositions(ArrayList<Position> positions) {
        ArrayList<String> strings = new ArrayList<String>();

        for(int i = 0; i < positions.size(); i++){
            strings.add(positionToString(positions.get(i)));
        }

        return strings;
    }

    private ArrayList<Position> getCapturedList(Position pieceToCheck, ArrayList<Position> checkedSpots) {
        boolean isBlack = false;
        if (checkedSpots == null) checkedSpots = new ArrayList<Position>();
        if (isBlack(positionToString(pieceToCheck))) isBlack = true;

        Coordinates currentY = pieceToCheck.getYPosition();
        Coordinates currentX = pieceToCheck.getXPosition();

        checkedSpots.add(pieceToCheck);

        for (int i = 0; i < playedSpots.size(); i++) {
            Position nextPosition = playedSpots.get(i);
            if (pieceToCheck.getPositionRight().equals(nextPosition)) {
                    if(!checkedSpots.contains(nextPosition)) {
                        if (isBlack == isBlack(positionToString(nextPosition))) {
                            if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                        } else {
                            if (isBlack) getCapturedList(nextPosition, checkedSpots);
                        }
                    }
            }
            else if (pieceToCheck.getPositionLeft().equals(nextPosition)) {
                if(!checkedSpots.contains(nextPosition)) {
                    if (isBlack == isBlack(positionToString(nextPosition))) {
                        if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                    } else {
                        if (isBlack) getCapturedList(nextPosition, checkedSpots);
                    }
                }
            }
            else if (pieceToCheck.getPositionUp().equals(nextPosition)) {
                if(!checkedSpots.contains(nextPosition)) {
                    if (isBlack == isBlack(positionToString(nextPosition))) {
                        if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                    } else {
                        if (isBlack) getCapturedList(nextPosition, checkedSpots);
                    }
                }
                }
            else if (pieceToCheck.getPositionDown().equals(nextPosition)) {
                if(!checkedSpots.contains(nextPosition)) {
                    if (isBlack == isBlack(positionToString(nextPosition))) {
                        if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                    } else {
                        if (isBlack) getCapturedList(nextPosition, checkedSpots);
                    }
                }
            }
        }
        return checkedSpots;
    }

    private boolean checkCapturedWhite(Position piece, ArrayList<Position> checkedSpots) {
        if(checkedSpots == null) checkedSpots = new ArrayList<Position>();
        checkedSpots.add(piece);

        boolean isLeftOccupied = false;
        boolean isRightOccupied = false;
        boolean isUpOccupied = false;
        boolean isDownOccupied = false;

        Coordinates currentY = lastPlayed.getYPosition();
        Coordinates currentX = lastPlayed.getXPosition();

        boolean isCaptured;


        for (int i = 0; i < playedSpots.size(); i++) {
            Position positionToCheck = playedSpots.get(i);
            if(piece.getPositionRight() != null) {
                if (piece.getPositionRight().equals(positionToCheck)) {
                    isRightOccupied = true;
                    if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                    }
                }
            } else if (piece.getPositionLeft() != null) {
                if (piece.getPositionLeft().equals(positionToCheck)) {
                    isLeftOccupied = true;
                    if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
                else if(piece.getPositionUp() != null) {
                    if (piece.getPositionUp().equals(positionToCheck)) {
                        isUpOccupied = true;
                        if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                            if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                        }
                    }
                }
                else if(piece.getPositionDown() != null) {
                    if (piece.getPositionDown().equals(positionToCheck)) {
                        isDownOccupied = true;
                        if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                            if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                        }
                    }
                }
            }
        if(!isUpOccupied || !isDownOccupied || !isLeftOccupied || !isRightOccupied) return false;
        return true;
    }
}
