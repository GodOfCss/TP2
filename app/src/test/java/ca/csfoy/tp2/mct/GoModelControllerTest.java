package ca.csfoy.tp2.mct;

import org.junit.Test;

import static org.junit.Assert.*;

import ca.csfoy.tp2.mc.GoModelController;

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
    public void goModelController_CanPlayPiece() {
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
    public void goModelController_CanReturnBlackPlayedArray() {

    }
}