package ca.csfoy.tp2.mc;

import java.util.ArrayList;

public class GoModelController {
    private boolean isBlackNext;
    private boolean hasBlackWon;
    private boolean hasWhiteWon;

    private ArrayList<Position> playedBlackSpots;
    private ArrayList<Position> playedWhiteSpots;
    private ArrayList<Position> playedSpots;
    private Position lastPlayed;
    private int playedOffset;


    public GoModelController() {
        this.isBlackNext = true;
        this.playedWhiteSpots = new ArrayList<Position>();
        this.playedBlackSpots = new ArrayList<Position>();
        this.playedSpots = new ArrayList<Position>();
        this.playedOffset = 0;

    }

    public void play(String position) {
        int random = getBlackOffset();
        int random2 = getWhiteOffset();
        if(this.playedOffset != 0){
            this.playedOffset = 0;
        }
        else if(!hasBlackWon && !hasWhiteWon) {
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
                this.captureWhite();
            }
        }

    }

    private Position stringToPosition(String position) {
        Coordinates xPosition = null;
        Coordinates yPosition = null;

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

    public boolean isWhite(String id) {
        Position currentPosition = stringToPosition(id);
        for (int i = 0; i < playedWhiteSpots.size() + getWhiteOffset(); i++) {
            if (playedWhiteSpots.get(i).getXPosition() == currentPosition.getXPosition() && playedWhiteSpots.get(i).getYPosition() == currentPosition.getYPosition()) {
                return true;
            }

        }
        return false;
    }

    public boolean isBlack(String id) {
        Position currentPosition = stringToPosition(id);
        for (int i = 0; i < playedBlackSpots.size() + getBlackOffset(); i++) {
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
        ArrayList<String> playedBlack = new ArrayList<String>();

        for (int i = 0; i < playedBlackSpots.size(); i++) {
            playedBlack.add(positionToString(playedBlackSpots.get(i)));
        }

        return playedBlack;
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
        updatePlayedSpots();
    }


    public void setBlackPlayed(ArrayList<String> playedBlack) {
        for (int i = 0; i < playedBlack.size(); i++) {
            playedBlackSpots.add(stringToPosition(playedBlack.get(i)));
        }
        updatePlayedSpots();
    }

    public void updatePlayedSpots() {
        this.playedSpots = new ArrayList<Position>(this.playedBlackSpots);
        this.playedSpots.addAll(this.playedWhiteSpots);
    }

    /**
     * Regarde quelle piece sont blanches a ses alentours et tente de les capturer
     */
    private void captureBlack() {
        if(lastPlayed != null) {
            ArrayList<Position> positionCapturedPieces = new ArrayList<Position>();

            Coordinates currentY = lastPlayed.getYPosition();
            Coordinates currentX = lastPlayed.getXPosition();

            for (int i = 0; i < playedWhiteSpots.size(); i++) {
                Position pieceToCheck = playedWhiteSpots.get(i);

                if (lastPlayed.getPositionLeft() != null) {
                    if (lastPlayed.getPositionLeft().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) { //Regarde si la piece peut etre capturer
                            hasBlackWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null)); //Ajouter piece(s) a la liste de capture
                            capturePieces(positionCapturedPieces); //Capture les pieces
                        }
                    }
                }
                if (lastPlayed.getPositionRight() != null) {
                    if (lastPlayed.getPositionRight().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            hasBlackWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }
                if (lastPlayed.getPositionUp() != null) {
                    if (lastPlayed.getPositionUp().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            hasBlackWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }
                if (lastPlayed.getPositionDown() != null) {
                    if (lastPlayed.getPositionDown().equals(pieceToCheck)) {
                        if (checkCapturedWhite(pieceToCheck, null)) {
                            hasBlackWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                        }
                    }
                }

            }
        }
    }

    /**
     * Regarde quelle piece sont noires a ses alentours et tente de les capturer
     */
    private void captureWhite() {
        if(lastPlayed != null) {
            ArrayList<Position> positionCapturedPieces = new ArrayList<Position>();

            Coordinates currentY = lastPlayed.getYPosition();
            Coordinates currentX = lastPlayed.getXPosition();

            for (int i = 0; i < playedBlackSpots.size(); i++) {
                Position pieceToCheck = playedBlackSpots.get(i);

                if (lastPlayed.getPositionLeft() != null) {
                    if (lastPlayed.getPositionLeft().equals(pieceToCheck)) {
                        if (checkCapturedBlack(pieceToCheck, null)) {
                            hasWhiteWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                            i = 0;
                        }
                    }
                }
                if (lastPlayed.getPositionRight() != null) {
                    if (lastPlayed.getPositionRight().equals(pieceToCheck)) {
                        if (checkCapturedBlack(pieceToCheck, null)) {
                            hasWhiteWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                            i = 0;
                        }
                    }
                }
                if (lastPlayed.getPositionUp() != null) {
                    if (lastPlayed.getPositionUp().equals(pieceToCheck)) {
                        if (checkCapturedBlack(pieceToCheck, null)) {
                            hasWhiteWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                            i = 0;

                        }
                    }
                }
                if (lastPlayed.getPositionDown() != null) {
                    if (lastPlayed.getPositionDown().equals(pieceToCheck)) {
                        if (checkCapturedBlack(pieceToCheck, null)) {
                            hasWhiteWon = true;
                            positionCapturedPieces.addAll(getCapturedList(pieceToCheck, null));
                            capturePieces(positionCapturedPieces);
                            i = 0;
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

    private ArrayList<Position> getCapturedList(Position pieceToCheck, ArrayList<Position> checkedSpots) {
        boolean isBlack = false;
        if (checkedSpots == null) checkedSpots = new ArrayList<Position>();
        if (isBlack(positionToString(pieceToCheck))) isBlack = true;

        Coordinates currentY = pieceToCheck.getYPosition();
        Coordinates currentX = pieceToCheck.getXPosition();

        checkedSpots.add(pieceToCheck);

        for (int i = 0; i < playedSpots.size(); i++) {
            Position nextPosition = playedSpots.get(i);
            if(pieceToCheck.getPositionRight() != null) {
                if (pieceToCheck.getPositionRight().equals(nextPosition)) {
                    if (!checkedSpots.contains(nextPosition)) {
                        if (isBlack == isBlack(positionToString(nextPosition))) {
                            if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                            else getCapturedList(nextPosition, checkedSpots);
                        }
                    }
                }
            }
            if(pieceToCheck.getPositionLeft() != null) {
                if (pieceToCheck.getPositionLeft().equals(nextPosition)) {
                    if (!checkedSpots.contains(nextPosition)) {
                        if (isBlack == isBlack(positionToString(nextPosition))) {
                            if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                            else getCapturedList(nextPosition, checkedSpots);
                        }
                    }
                }
            }
            if(pieceToCheck.getPositionUp() != null) {
                if (pieceToCheck.getPositionUp().equals(nextPosition)) {
                    if (!checkedSpots.contains(nextPosition)) {
                        if (isBlack == isBlack(positionToString(nextPosition))) {
                            if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                            else getCapturedList(nextPosition, checkedSpots);
                        }
                    }
                }
            }
            if (pieceToCheck.getPositionDown() != null) {
                if (pieceToCheck.getPositionDown().equals(nextPosition)) {
                    if (!checkedSpots.contains(nextPosition)) {
                        if (isBlack == isBlack(positionToString(nextPosition))) {
                            if (!isBlack) getCapturedList(nextPosition, checkedSpots);
                            else getCapturedList(nextPosition, checkedSpots);
                        }
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

        if(piece.getPositionLeft() == null) isLeftOccupied = true;
        if(piece.getPositionRight() == null) isRightOccupied = true;
        if(piece.getPositionUp() == null) isUpOccupied = true;
        if(piece.getPositionDown() == null) isDownOccupied = true;

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
            }
            if (piece.getPositionLeft() != null) {
                if (piece.getPositionLeft().equals(positionToCheck)) {
                    isLeftOccupied = true;
                    if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
                if(piece.getPositionUp() != null) {
                    if (piece.getPositionUp().equals(positionToCheck)) {
                        isUpOccupied = true;
                        if (!isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                            if (!checkCapturedWhite(positionToCheck, checkedSpots)) return false;
                        }
                    }
                }
                if(piece.getPositionDown() != null) {
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

    private boolean checkCapturedBlack(Position piece, ArrayList<Position> checkedSpots) {
        if(checkedSpots == null) checkedSpots = new ArrayList<Position>();
        checkedSpots.add(piece);

        boolean isLeftOccupied = false;
        boolean isRightOccupied = false;
        boolean isUpOccupied = false;
        boolean isDownOccupied = false;

        if(piece.getPositionLeft() == null) isLeftOccupied = true;
        if(piece.getPositionRight() == null) isRightOccupied = true;
        if(piece.getPositionUp() == null) isUpOccupied = true;
        if(piece.getPositionDown() == null) isDownOccupied = true;


        Coordinates currentY = lastPlayed.getYPosition();
        Coordinates currentX = lastPlayed.getXPosition();

        boolean isCaptured;


        for (int i = 0; i < playedSpots.size(); i++) {
            Position positionToCheck = playedSpots.get(i);
            if(piece.getPositionRight() != null) {
                if (piece.getPositionRight().equals(positionToCheck)) {
                    isRightOccupied = true;
                    if (isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedBlack(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
            if (piece.getPositionLeft() != null) {
                if (piece.getPositionLeft().equals(positionToCheck)) {
                    isLeftOccupied = true;
                    if (isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedBlack(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
            if(piece.getPositionUp() != null) {
                if (piece.getPositionUp().equals(positionToCheck)) {
                    isUpOccupied = true;
                    if (isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedBlack(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
            if(piece.getPositionDown() != null) {
                if (piece.getPositionDown().equals(positionToCheck)) {
                    isDownOccupied = true;
                    if (isBlack(positionToString(positionToCheck)) && !checkedSpots.contains(positionToCheck)) {
                        if (!checkCapturedBlack(positionToCheck, checkedSpots)) return false;
                    }
                }
            }
        }
        if(!isUpOccupied || !isDownOccupied || !isLeftOccupied || !isRightOccupied) return false;
        return true;
    }

    public boolean hasBlackWon() {
        return hasBlackWon;
    }

    public boolean hasWhiteWon() {
        return hasWhiteWon;
    }

    public boolean isGameOver() {
        if(!hasBlackWon && !hasWhiteWon) return false;
        else return true;
    }

    public void cancel() {
        if (lastPlayed != null) {
            this.playedSpots.removeIf(playedSpots -> playedSpots.equals(lastPlayed));
            this.playedWhiteSpots.removeIf(playedSpots -> playedSpots.equals(lastPlayed));
            this.playedBlackSpots.removeIf(playedSpots -> playedSpots.equals(lastPlayed));
            this.lastPlayed = null;
            if(this.isBlackNext) this.isBlackNext = false;
            else this.isBlackNext = true;
        }
    }

    public void setWinner(Teams winningTeam) {
        if(winningTeam != null){
            switch(winningTeam) {
                case WHITE:
                    this.hasWhiteWon = true;
                    break;
                case BLACK:
                    this.hasBlackWon = true;
                    break;
                default:
                    this.hasBlackWon = false;
                    this.hasWhiteWon = false;
                    break;
            }
        } else {
            this.hasBlackWon = false;
            this.hasWhiteWon = false;
        }
    }


    public void forward() {
        if(this.playedOffset < 0) {
            this.playedOffset++;

        }
    }

    public void back() {
        if(this.playedOffset + this.playedSpots.size() > 0) {
            this.playedOffset--;
        }
    }

    public String getLastPlayedInHistory() {
        if (this.lastPlayed == null) return null;
        if (this.playedSpots.size() + playedOffset == 0) return null;
        return positionToString(playedSpots.get(playedSpots.size()+playedOffset-1));
    }

    private int getWhiteOffset(){
        int whiteOffset = 0;
        if(this.playedOffset < 0) {
            if(playedOffset % 2 == -1 && isBlackNext) {
                whiteOffset = this.playedOffset / 2 - 1;
            } else {
                whiteOffset = this.playedOffset / 2;
            }
        }
        return whiteOffset;
    }

    private int getBlackOffset(){
        int blackOffset = 0;
        if(this.playedOffset < 0) {
            if(playedOffset % 2 == -1 && !isBlackNext) {
                blackOffset = this.playedOffset / 2 - 1;
            } else {
                blackOffset = this.playedOffset / 2;
            }
        }
        return blackOffset;
    }

    public int getOffset() {
        return playedOffset;
    }

    public void setOffset(int currentHistoryOffset) {
        playedOffset = currentHistoryOffset;
    }
}
