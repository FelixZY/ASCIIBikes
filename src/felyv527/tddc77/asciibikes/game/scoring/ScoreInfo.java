package felyv527.tddc77.asciibikes.game.scoring;

import felyv527.tddc77.asciibikes.game.config.BikeInfo;

/**
 * Contains information on the score of a certain player as well as some basic
 * info about this player's bike.
 *
 */
public final class ScoreInfo {
	private BikeInfo bikeInfo;
	private int score;
	private int roundScore;

	public ScoreInfo(BikeInfo bikeInfo, int score) {
		this.bikeInfo = bikeInfo;
		this.score = score;
	}

	public BikeInfo getBikeInfo() {
		return this.bikeInfo;
	}

	/**
	 * Retrieves the total score gathered by the associated player
	 */
	public int getScore() {
		return this.score + roundScore;
	}

	/**
	 * Retrieves the score gathered by the associated player during the last
	 * round.
	 */
	public int getRoundScore() {
		return roundScore;
	}

	protected void setRoundScore(int score) {
		roundScore = score;
	}

	protected void applyRoundScore() {
		score += roundScore;
		roundScore = 0;
	}
}
