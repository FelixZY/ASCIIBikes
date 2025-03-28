package felyv527.tddc77.asciibikes.game.config;

import felyv527.tddc77.asciibikes.game.arena.Arena;

/**
 * Cotains basic information about a game setup such as which arena to use,
 * player config etc.
 *
 */
public final class GameConfig {

	private Arena gameArena;

	private int numPlayers, gameRounds;

	private KeyboardPlayerInfo[] playerConfigs;

	protected GameConfig(int numPlayers) {
		this.numPlayers = numPlayers;
		this.playerConfigs = new KeyboardPlayerInfo[numPlayers];
	}

	public Arena getArena() {
		return gameArena;
	}

	public int getPlayerCount() {
		return numPlayers;
	}

	public int getGameRounds() {
		return gameRounds;
	}

	public KeyboardPlayerInfo[] getPlayerConfigs() {
		return playerConfigs;
	}

	protected void setArena(Arena arena) {
		this.gameArena = arena;
	}

	protected void setGameRounds(int numRounds) {
		this.gameRounds = numRounds;
	}

	protected void setPlayerConfig(int playerIndex, KeyboardPlayerInfo playerConfig) {
		this.playerConfigs[playerIndex] = playerConfig;
	}

}
