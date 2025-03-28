package felyv527.tddc77.asciibikes.menu;

/**
 * Specifies identifiers to indicate the return value of a {@link MenuItem}
 *
 */
public enum MenuResult {
	/**
	 * Represents that the {@link MenuItem} has completed all its actions.
	 */
	COMPLETED,

	/**
	 * Represents that the parent {@link MenuItem} should close.
	 */
	GO_BACK,

	/**
	 * Represents that all {@link MenuItem} in the tree should close.
	 */
	EXIT
}
