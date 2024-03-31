package problemdomain;

import java.util.Objects;

/**
 * The Coordinates class represents a pair of coordinates in a 2D space.
 *
 * Each instance of Coordinates has an x and y value, representing its position
 * in the 2D space.
 *
 * The class provides methods to get the x and y values, and overrides the
 * `equals` and `hashCode` methods from the Object class.
 * The `equals` method checks if two Coordinates objects represent the same
 * position, and the `hashCode` method returns a hash code for a Coordinates
 * object.
 */
public class Coordinates {
    /**
     * The x-coordinate of this object in a 2D space.
     */
    private final int x;

    /**
     * The y-coordinate of this object in a 2D space.
     */
    private final int y;

    /**
     * Constructs a new Coordinates object with the specified x and y values.
     *
     * @param x the x-coordinate of this object in a 2D space
     * @param y the y-coordinate of this object in a 2D space
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this object.
     *
     * @return the x-coordinate of this object
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this object.
     *
     * @return the y-coordinate of this object
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this Coordinates object is equal to another object.
     * Two Coordinates objects are equal if they represent the same position in the 2D space.
     *
     * @param o the object to compare this Coordinates object to
     * @return true if the specified object is equal to this Coordinates object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    /**
     * Returns a hash code for this Coordinates object.
     * The hash code is computed based on the x and y values of this object.
     *
     * @return a hash code for this Coordinates object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
