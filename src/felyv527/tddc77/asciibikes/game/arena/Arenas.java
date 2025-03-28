package felyv527.tddc77.asciibikes.game.arena;

/**
 * Provides enum values for all available arenas as well as access to the arenas
 * they represent via the static method {@link Arenas#getArena(Arenas)}
 * 
 * <p>
 * Originally created to make arena selection possible using a
 * {@link EnumSelectionMenu}
 *
 */
public enum Arenas {
	OPEN_CASTLE, THREE_PILLARS;

	/**
	 * Initializes and returns a new instance of the arena represented by an
	 * {@link Arenas} enum or null if no corresponding arena was found.
	 * 
	 * @param arena
	 *            The arena to retrieve an instance of.
	 * @return A new instance of the arena represented by {@code arena} or null
	 *         if no corresponding arena was found.
	 */
	public static Arena getArena(Arenas arena) {
		switch (arena) {
			case OPEN_CASTLE:
				return new OpenCastleArena();
			case THREE_PILLARS:
				return new ThreePillarsArena();
			default:
				return null;

		}
	}
}
