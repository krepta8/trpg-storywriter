/**
 *
 */
package games.closetmonster.trpg.storywriter;

/**
 * @author Jonathan Radliff
 *
 */
public enum Direction {

	NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST;

	public static Direction opposite(Direction direction) {
		return direction.getOpposite();
	}

	public Direction getOpposite() {
		return Direction.values()[(Direction.values().length / 2 + ordinal()) % Direction.values().length];
	}

}
