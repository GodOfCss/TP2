package ca.csfoy.tp2.mct;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.csfoy.tp2.mc.Coordinates;
import ca.csfoy.tp2.mc.Position;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PositionTest {
    @Test
    public void positionCanBeInstantiated() {
        //Arrange
        new Position(Coordinates.a, Coordinates.a);
    }

    @Test
    public void positionCanReturnCoordinates() {
        //Arrange
        Position position = new Position(Coordinates.a, Coordinates.c);

        //Act
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        //Assert
        assertEquals(x, Coordinates.a);
        assertEquals(y, Coordinates.c);
    }

    @Test
    public void positionCanReceiveNewCoordinates() {
        //Arrange
        Position position = new Position(Coordinates.a, Coordinates.c);

        //Act
        position.setXPosition(Coordinates.d);
        position.setYPosition(Coordinates.e);

        //Assert
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        assertEquals(x, Coordinates.d);
        assertEquals(y, Coordinates.e);
    }

    @Test
    public void positionCanReturnSurroundingLeftPosition() {
        //Arrange
        Position position = new Position(Coordinates.b, Coordinates.c);

        //Act
        position = position.getPositionLeft();
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        //Assert
        assertEquals(Coordinates.a, x);
        assertEquals(Coordinates.c, y);
    }

    @Test
    public void positionCanReturnSurroundingRightPosition() {
        //Arrange
        Position position = new Position(Coordinates.b, Coordinates.c);

        //Act
        position = position.getPositionRight();
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        //Assert
        assertEquals(Coordinates.c, x);
        assertEquals(Coordinates.c, y);
    }

    @Test
    public void positionCanReturnSurroundingUpPosition() {
        //Arrange
        Position position = new Position(Coordinates.b, Coordinates.c);

        //Act
        position = position.getPositionUp();
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        //Assert
        assertEquals(Coordinates.b, x);
        assertEquals(Coordinates.b, y);
    }

    @Test
    public void positionCanReturnSurroundingDownPosition() {
        //Arrange
        Position position = new Position(Coordinates.b, Coordinates.c);

        //Act
        position = position.getPositionDown();
        Coordinates x = position.getXPosition();
        Coordinates y = position.getYPosition();

        //Assert
        assertEquals(Coordinates.b, x);
        assertEquals(Coordinates.d, y);
    }

    @Test
    public void positionReturnsNull_WhenAskedSurroundingPosition_ifDoesNotExist() {
        //Arrange
        Position topLeft = new Position(Coordinates.a, Coordinates.a);
        Position bottomRight = new Position(Coordinates.i, Coordinates.i);

        //Act
        Position positionUp = topLeft.getPositionUp();
        Position positionLeft = topLeft.getPositionLeft();
        Position positionDown = bottomRight.getPositionDown();
        Position positionRight = bottomRight.getPositionRight();

        //Assert
        assertEquals(positionLeft, null);
        assertEquals(positionUp, null);
        assertEquals(positionDown, null);
        assertEquals(positionRight, null);

    }
}