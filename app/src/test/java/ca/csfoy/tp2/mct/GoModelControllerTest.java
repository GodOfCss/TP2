package ca.csfoy.tp2.mct;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import ca.csfoy.tp2.mc.GoModelController;
import ca.csfoy.tp2.mc.Teams;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GoModelControllerTest {

    private static final String randomPositionString = "a1";
    @Test
    public void goModelControllerCanBeInstantiated() {
        GoModelController controller = new GoModelController();
    }

    @Test
    public void goModelControllerCanReturnNextPlayer() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        boolean isBlackNext = controller.isBlackNext();
        //Assert
        assertTrue(isBlackNext);
    }

    @Test
    public void goModelController_ifAskedLastPlayed_ReturnsNullWhenNoPlayedPiece() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        String lastPlayed = controller.getLastPlayed();
        //Assert
        assertEquals(lastPlayed, null);
    }

    @Test
    public void goModelController_ifAskedLastPlayed_ReturnsLastWhenPlayedPiece() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.setLastPlayed(randomPositionString);
        //Assert
        String lastPlayed = controller.getLastPlayed();
        assertEquals(randomPositionString, lastPlayed);
    }

    @Test
    public void goModelController_CanGetAndSetBlackPlayedFromString() {
        //Arrange
        GoModelController controller = new GoModelController();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedBlack.add("a1");
        playedBlack.add("a2");
        //Act
        controller.setBlackPlayed(playedBlack);
        //Assert
        ArrayList<String> lastPlayed = controller.getPlayedBlackSpots();

        assertEquals(playedBlack, lastPlayed);
        assertEquals(2, lastPlayed.size());
    }

    @Test
    public void goModelController_CanGetAndSetWhitePlayedFromString() {
        //Arrange
        GoModelController controller = new GoModelController();
        ArrayList<String> playedWhite = new ArrayList<String>();
        playedWhite.add("a1");
        playedWhite.add("a2");
        //Act
        controller.setWhitePlayed(playedWhite);
        //Assert
        ArrayList<String> lastPlayed = controller.getPlayedWhiteSpots();

        assertEquals(playedWhite, lastPlayed);
        assertEquals(2, lastPlayed.size());
    }

    @Test
    public void goModelController_CanCheckIfPositionIsPlayed() {
        //Arrange
        GoModelController controller = new GoModelController();
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("a1");
        playedBlack.add("a2");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);

        //Act
        boolean isPlayedWhiteTrue = controller.isPlayed("a1");
        boolean isPlayedBlackTrue = controller.isPlayed("a2");
        boolean isPlayedFalse = controller.isPlayed("a3");
        //Assert
        assertTrue(isPlayedWhiteTrue);
        assertTrue(isPlayedBlackTrue);
        assertFalse(isPlayedFalse);
    }

    @Test
    public void goModelController_CanCheckIfPositionIsBlack() {
        //Arrange
        GoModelController controller = new GoModelController();
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("a1");
        playedBlack.add("a2");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        //Act
        boolean isPlayedWhite = controller.isBlack("a1");
        boolean isPlayedBlack = controller.isBlack("a2");
        boolean isPlayedFalse = controller.isBlack("a3");
        //Assert
        assertFalse(isPlayedWhite);
        assertTrue(isPlayedBlack);
        assertFalse(isPlayedFalse);
    }

    @Test
    public void goModelController_CanCheckIfPositionIsWhite() {
        //Arrange
        GoModelController controller = new GoModelController();
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("a1");
        playedBlack.add("a2");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        //Act
        boolean isPlayedWhite = controller.isWhite("a1");
        boolean isPlayedBlack = controller.isWhite("a2");
        boolean isPlayedFalse = controller.isWhite("a3");
        //Assert
        assertTrue(isPlayedWhite);
        assertFalse(isPlayedBlack);
        assertFalse(isPlayedFalse);
    }

    @Test
    public void goModelController_CanGetAndSetNextPlayer() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.setNext(false);
        //Assert
        assertFalse(controller.isBlackNext());
    }

    @Test
    public void goModelController_CanPlayBlackPiece() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.play(randomPositionString);
        //Assert
        String lastPlayed = controller.getLastPlayed();

        assertEquals(randomPositionString, lastPlayed);
        assertEquals(1, controller.getPlayedBlackSpots().size());
    }

    @Test
    public void goModelController_CanPlayWhitePiece() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        //Act
        controller.play(randomPositionString);
        //Assert
        String lastPlayed = controller.getLastPlayed();

        assertEquals(randomPositionString, lastPlayed);
        assertEquals(1, controller.getPlayedWhiteSpots().size());
    }

    @Test
    public void goModelController_BlackCanCaptureWhitePieceFromTop() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedBlack = new ArrayList<String>();
        ArrayList<String> playedWhite = new ArrayList<String>();
        playedBlack.add("e3");
        playedBlack.add("c3");
        playedBlack.add("d2");
        playedWhite.add("d3");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(true);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasBlackWon());
    }

    @Test
    public void goModelController_BlackCanCaptureWhitePieceFromBottom() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedBlack = new ArrayList<String>();
        ArrayList<String> playedWhite = new ArrayList<String>();
        playedBlack.add("c5");
        playedBlack.add("e5");
        playedBlack.add("d6");
        playedWhite.add("d5");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(true);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasBlackWon());
    }

    @Test
    public void goModelController_BlackCanCaptureWhitePieceFromLeft() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedBlack = new ArrayList<String>();
        ArrayList<String> playedWhite = new ArrayList<String>();
        playedBlack.add("e5");
        playedBlack.add("e3");
        playedBlack.add("f4");
        playedWhite.add("e4");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(true);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasBlackWon());
    }

    @Test
    public void goModelController_BlackCanCaptureWhitePieceFromRight() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedBlack = new ArrayList<String>();
        ArrayList<String> playedWhite = new ArrayList<String>();
        playedBlack.add("c5");
        playedBlack.add("c3");
        playedBlack.add("b4");
        playedWhite.add("c4");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(true);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasBlackWon());
    }

    @Test
    public void goModelController_WhiteCanCaptureBlackPieceFromTop() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("e3");
        playedWhite.add("c3");
        playedWhite.add("d2");
        playedBlack.add("d3");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(false);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_WhiteCanCaptureBlackPieceFromBottom() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("c5");
        playedWhite.add("e5");
        playedWhite.add("d6");
        playedBlack.add("d5");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(false);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_WhiteCanCaptureBlackPieceFromLeft() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("e5");
        playedWhite.add("e3");
        playedWhite.add("f4");
        playedBlack.add("e4");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(false);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_WhiteCanCaptureBlackPieceFromRight() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.setNext(false);
        ArrayList<String> playedWhite = new ArrayList<String>();
        ArrayList<String> playedBlack = new ArrayList<String>();
        playedWhite.add("c5");
        playedWhite.add("c3");
        playedWhite.add("b4");
        playedBlack.add("c4");
        controller.setWhitePlayed(playedWhite);
        controller.setBlackPlayed(playedBlack);
        controller.setNext(false);
        //Act
        controller.play("d4");
        //Assert
        assertTrue(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_CanReturnThatGameIsNotOver() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        boolean isGameOver = controller.isGameOver();
        //Assert
        assertFalse(isGameOver);
    }

    @Test
    public void goModelController_CanSetThatWhiteHasWon() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.setWinner(Teams.WHITE);
        //Assert
        assertTrue(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_CanSetThatBlackHasWon() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.setWinner(Teams.BLACK);
        //Assert
        assertTrue(controller.hasBlackWon());
    }

    @Test
    public void goModelController_CanSetThatNoOneHasWon() {
        //Arrange
        GoModelController controller = new GoModelController();
        //Act
        controller.setWinner(null);
        //Assert
        assertFalse(controller.hasBlackWon());
        assertFalse(controller.hasWhiteWon());
    }

    @Test
    public void goModelController_CanCancelLastMove() {
        //Arrange
        GoModelController controller = new GoModelController();
        controller.play("a1");
        //Act
        controller.cancel();
        //Assert
        assertFalse(controller.isPlayed("a1"));
    }
}