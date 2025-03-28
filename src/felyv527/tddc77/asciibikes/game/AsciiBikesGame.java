package felyv527.tddc77.asciibikes.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.collision.CollisionManager;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.BackgroundSprite;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.game.arena.Arena;
import felyv527.tddc77.asciibikes.game.arena.SpawnLocation;
import felyv527.tddc77.asciibikes.game.bike.Bike;
import felyv527.tddc77.asciibikes.game.bike.KeyboardControlledBike;
import felyv527.tddc77.asciibikes.game.config.GameConfig;
import felyv527.tddc77.asciibikes.game.scoring.ScoreManager;
import felyv527.tddc77.asciibikes.game.scoring.ScoreInfo;
import felyv527.tddc77.asciibikes.game.ui.CountDownOverlay;
import felyv527.tddc77.asciibikes.game.ui.MessageOverlay;
import felyv527.tddc77.asciibikes.game.ui.UiManager;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.Keys;
import felyv527.tddc77.asciibikes.menu.ScoreboardMenu;

/**
 * The main game driver for ASCIIBikes
 *
 */
public class AsciiBikesGame extends Game {

	private static Random rand = new Random();

	private GameState gameState = GameState.GAME_START;
	private GameConfig config;

	private UiManager uiManager = new UiManager();

	private Arena arena;
	private Bike[] players;

	private int playedRounds = 0;

	private boolean drewLastUpdate = false;

	/**
	 * Constructs and initializes a game with the supplied configuration.
	 */
	public AsciiBikesGame(GameConfig config) {
		super(30);

		this.config = config;

		players = new KeyboardControlledBike[config.getPlayerCount()];

		arena = config.getArena();

		for (int i = 0; i < config.getPlayerCount(); i++) {
			players[i] = new KeyboardControlledBike(i, config.getPlayerConfigs()[i]);
		}
	}

	@Override
	protected void prepare() {
		CollisionManager.unRegisterAll();
		DrawManager.startSession();
		DrawManager.registerForDraw(new BackgroundSprite(Arena.arenaBackground));
		ScoreManager.newGame(config);

		// Add the white background behind the in-game statistics area.
		String[] statBackgroundArray = new String[GameWindowAreas.StatsArea.getHeight()];
		for (int i = 0; i < statBackgroundArray.length; i++) {
			statBackgroundArray[i] = StringTools.repeat(' ', GameWindowAreas.StatsArea.getWidth());
		}
		Colorizer.colorize(statBackgroundArray, TerminalBackgroundColor.WHITE);

		AsciiSprite uiBgSprite = new AsciiSprite(
				new Point(GameWindowAreas.StatsArea.getLeft(), GameWindowAreas.StatsArea.getTop()),
				statBackgroundArray);

		DrawManager.registerForDraw(uiBgSprite);

		arena.loadArena();

		uiManager.showUi();

		gameState = GameState.GAME_START;
	}

	@Override
	protected void update(long deltaMillis) {
		Set<Keys> pressedKeys = InputManager.getPressedKeys();

		switch (gameState) {
			case GAME_START:
				prepareRound();
				(new MessageOverlay("Round " + (playedRounds + 1))).show(1000);
				(new CountDownOverlay()).show(3000);
				// Clear any key presses that may have happened while the
				// overlays were showing.
				InputManager.getPressedKeys();
				gameState = GameState.WARMUP;
				break;

			case WARMUP:
				/*
				 * In GAME_START we show overlays that blocks the execution of
				 * this class.
				 * 
				 * This offsets deltaMillis for one tick, making game components
				 * believe more time has passed than it actually has.
				 * 
				 * By having a single WARMUP update before starting the game,
				 * deltaMillis has time to reset.
				 */
				gameState = GameState.IN_GAME;
				break;

			case IN_GAME:
				uiManager.update(deltaMillis);

				int alivePlayers = 0;
				for (Bike player : players) {
					player.update(pressedKeys, deltaMillis);
					if (player.isAlive()) {
						alivePlayers++;
					}

				}

				CollisionManager.runCollisionCheck();

				if (alivePlayers <= 1) {
					if (alivePlayers == 1)
						for (Bike player : players) {
							if (player.isAlive())
								ScoreManager.score(player.getControllingPlayerIndex(), player.getAliveTime());
						}

					gameState = GameState.ROUND_OVER;
				}
				break;

			case ROUND_OVER:
				uiManager.update(deltaMillis);
				uiManager.onRoundEnd();

				playedRounds++;

				this.drewLastUpdate = false;
				this.draw();

				ScoreInfo roundWinner = ScoreManager.getRoundLeader();

				(new MessageOverlay("Round " + playedRounds + " over")).show(1500);
				(new MessageOverlay("Round winner\n" + roundWinner.getBikeInfo().getName(),
						TerminalForegroundColor.WHITE, roundWinner.getBikeInfo().getBackgroundColor())).show(2700);
				endRound();

				// Clear any key presses that may have happened while the
				// overlays were showing.
				InputManager.getPressedKeys();

				if (playedRounds < config.getGameRounds()) {
					gameState = GameState.GAME_START;
				} else
					gameState = GameState.GAME_OVER;
				break;

			case GAME_OVER:
				uiManager.update(deltaMillis);
				uiManager.onRoundEnd();

				this.drewLastUpdate = false;
				this.draw();

				ScoreInfo gameWinner = ScoreManager.getGameLeader();
				(new MessageOverlay("Round " + playedRounds + " over")).show(1500);
				(new MessageOverlay("Game winner\n" + gameWinner.getBikeInfo().getName(),
						TerminalForegroundColor.WHITE, gameWinner.getBikeInfo().getBackgroundColor())).show(2700);
				(new ScoreboardMenu("Game Over")).show();

				// Clear any key presses that may have happened while the
				// overlays were showing.
				InputManager.getPressedKeys();

				this.exit();
				break;

			default:
				this.exit();
				return;
		}
	}

	private void prepareRound() {
		ScoreManager.newRound();
		uiManager.onRoundStart();
		SpawnLocation[] spawnLocations = arena.getSpawnLocations(config.getPlayerCount());

		/*
		 * Note that certain spawn locations may contain multiple spawnable
		 * directions. It is therefore important that the actual spawn {@link
		 * Point} is saved to this set.
		 */
		Set<Point> selectedSpawns = new HashSet<>();

		/*
		 * Assigns spawn locations to all players, making sure no overlap
		 * happens.
		 */
		for (Bike player : players) {
			int spawnIndex;
			do {
				spawnIndex = rand.nextInt(spawnLocations.length);
				/*
				 * If selectedSpawns contains the location someone is already
				 * spawning here.
				 */
			} while (!selectedSpawns.add(spawnLocations[spawnIndex].startPoint));
			player.spawn(spawnLocations[spawnIndex]);
		}
	}

	private void endRound() {
		for (Bike player : players) {
			player.unSpawn();
		}
	}

	@Override
	protected void onUnload() {
		CollisionManager.unRegisterAll();
		DrawManager.endSession();
	}

	@Override
	protected void draw() {
		/*
		 * Since the console is slow at drawing, we reduce the number of draw
		 * calls to 50% of the update frequency. The difference is unnoticable
		 * as for lag but reduces draw flicker substantially.
		 */
		if (!drewLastUpdate)
			DrawManager.drawFrame();
		drewLastUpdate = !drewLastUpdate;
	}

}
