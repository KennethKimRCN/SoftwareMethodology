package chess;

/**
 * Coordinate object to keep track of the location of the pieces
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Coord {
    int x, y;

    /**
     * Constructor
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for int x
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for int y
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }
}
