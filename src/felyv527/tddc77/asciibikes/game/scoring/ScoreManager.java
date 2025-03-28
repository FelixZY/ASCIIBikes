package felyv527.tddc77.asciibikes.game.scoring;

import java.util.Arrays;
import java.util.Comparator;

import felyv527.tddc77.asciibikes.game.config.GameConfig;

/**
 * {@link ScoreManager} is responsible for calculating and keeping track of the
 * score of the last game being played.
 *
 */
public class ScoreManager {

	private static ScoreInfo[] scoreInfoArray = new ScoreInfo[0];
	private static GameConfig config = null;
	private static int alivePlayers;

	private ScoreManager() {
		// Utility class. Should not be instantiated.

		/*
		 * There is reasoning to be done over whether this class should be
		 * static or not. In this case I have chosen to keep it static because
		 * it makes it easier for bikes to tend to themselves and because that
		 * the game is quite small.
		 */
	}

	/**
	 * Resets all score and prepares for a new game based on the provided
	 * configuration
	 */
	public static void newGame(GameConfig config) {
		ScoreManager.config = config;
		ScoreManager.scoreInfoArray = new ScoreInfo[config.getPlayerCount()];
		for (int i = 0; i < config.getPlayerCount(); i++) {
			scoreInfoArray[i] = new ScoreInfo(config.getPlayerConfigs()[i], 0);
		}
	}

	/**
	 * Prepares the {@link ScoreManager} for a new round.
	 */
	public static void newRound() {
		alivePlayers = config.getPlayerCount();
		for (ScoreInfo scoreInfo : scoreInfoArray)
			scoreInfo.applyRoundScore();
	}

	/**
	 * Gets an ordered array of the current standings, with index 0 being the
	 * lead.
	 */
	public static ScoreInfo[] getStandings() {
		/*
		 * We index scoreInfoArray based on the playerIndex. Therefore it cannot
		 * be sorted in any other way. Instead we clone it to another array
		 * return it instead.
		 */
		ScoreInfo[] score = scoreInfoArray.clone();

		Arrays.sort(score, new Comparator<ScoreInfo>() {

			@Override
			public int compare(ScoreInfo arg0, ScoreInfo arg1) {
				return arg1.getScore() - arg0.getScore();
			}

		});

		return score;
	}

	/**
	 * Returns the {@link ScoreInfo} associated with the player currently in the
	 * lead of the round.
	 * 
	 * <p>
	 * Note that score is not calculated until a bike dies and so any still
	 * living bikes are technically in the lead but not included in the result.
	 */
	public static ScoreInfo getRoundLeader() {
		int maxScore = -1, maxIndex = -1;
		for (int i = 0; i < scoreInfoArray.length; i++) {
			if (scoreInfoArray[i].getRoundScore() > maxScore) {
				maxScore = scoreInfoArray[i].getRoundScore();
				maxIndex = i;
			}
		}
		if (maxIndex > -1)
			return scoreInfoArray[maxIndex];
		else
			return null;
	}

	/**
	 * Returns the {@link ScoreInfo} associated with the player currently in the
	 * lead of the game.
	 * 
	 * <p>
	 * Note that score is not calculated until a bike dies and so any still
	 * living bikes might technically be in the lead but not included in the
	 * result.
	 */
	public static ScoreInfo getGameLeader() {
		ScoreInfo[] standings = getStandings();
		return standings.length > 0 ? standings[0] : null;
	}

	/**
	 * Calculates the score for a player based on their position and time alive.
	 * 
	 * @param playerIndex
	 *            The playerIndex of the player to calculate score for
	 * @param aliveTime
	 *            The time the player has been alive
	 */
	public static void score(int playerIndex, long aliveTime) {
		if (config != null && alivePlayers > 0 && scoreInfoArray[playerIndex].getRoundScore() == 0) {
			/*
			 * Score is calculated based on a curve with an initially steep
			 * slope that flattens off after a few seconds.
			 * 
			 * This means that survival for the first 5-15 seconds increase
			 * score substantially whereas survival beyond 30 seconds only has a
			 * mild effect.
			 */
			scoreInfoArray[playerIndex].setRoundScore((int) Math.min(800, Math.max(2000 / (2 + alivePlayers)
					+ 75 * Math.pow((aliveTime / 1000d + 2) / 2d, .2d) - 2500 / (aliveTime / 1000d + 8), 0)));
			alivePlayers--;
		}
	}
}
