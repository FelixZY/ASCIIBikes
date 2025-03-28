package felyv527.tddc77.asciibikes;

import java.util.Set;

import felyv527.tddc77.asciibikes.CountDownOverlay.CountDownCompletedListener;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.common.BackgroundSprite;
import felyv527.tddc77.asciibikes.common.Direction;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.debug.Console;
import felyv527.tddc77.asciibikes.debug.FpsCounter;
import felyv527.tddc77.asciibikes.debug.KeyEcho;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.Keys;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class AsciiBikesGame extends Game {

	private int spawnX = TerminalInfo.getTerminalCols() / 2,
			spawnY = TerminalInfo.getTerminalLines() / 2;

	private GameState gameState = GameState.PREPARING;
	private CountDownOverlay cdo;

	private boolean isShowingCountDown = false;

	private Arena arena = new Arena(new StaticObstacle(new Rectangle(10, 10,
			15, 5)), new StaticObstacle(new Rectangle(40, 4, 1, 1)));

	private Player p1, p2;

	@Override
	protected void prepare() {
		CollisionHandler.clean();
		DrawHandler.startSession();
		DrawHandler
				.registerForDraw(new BackgroundSprite(UIInfo.arenaBackground));

		arena.createArena();

		p1 = new Player("YELLOW", TerminalForegroundColor.YELLOW);
		p2 = new Player("BLUE", TerminalForegroundColor.BLUE);

		p1.setKeys(Keys.ARROW_LEFT, Keys.ARROW_RIGHT);
		p2.setKeys(Keys.A, Keys.D);

		p1.createBike(
				new Point(spawnX, spawnY),
				Direction.RIGHT,
				(int) (TerminalInfo.getTerminalCols()
						* TerminalInfo.getTerminalLines() * .075f));
		p2.createBike(
				new Point(3, 3),
				Direction.DOWN,
				(int) (TerminalInfo.getTerminalCols()
						* TerminalInfo.getTerminalLines() * .075f));

		gameState = GameState.PREPARING;

		KeyEcho.init();
		FpsCounter.init();
		Console.init();
	}

	@Override
	protected void Update(long deltaMillis) {
		Set<Keys> keys = InputManager.getPressedKeys();
		KeyEcho.update(keys);
		FpsCounter.update(deltaMillis);
		Console.update(deltaMillis);
		if (keys.contains(Keys.CTRL_C)) {
			this.Exit();
		}

		if (gameState == GameState.PREPARING) {
			if (!isShowingCountDown) {
				isShowingCountDown = true;
				cdo = new CountDownOverlay(3000);
				cdo.start(new CountDownCompletedListener() {

					@Override
					public void onCountDownCompleted() {
						gameState = GameState.IN_GAME;
					}

				});
			} else {
				cdo.update(deltaMillis);
			}
		} else if (gameState == GameState.IN_GAME) {
			p1.update(keys, deltaMillis);
			p2.update(keys, deltaMillis);
			CollisionHandler.runCollisionCheck();
		}
	}

	@Override
	protected void Exit() {
		arena.unloadArena();
		p1.unload();
		p2.unload();
		KeyEcho.unload();
		FpsCounter.unload();
		Console.unload();
		DrawHandler.endSession();
		super.Exit();
	}

	@Override
	protected void Draw() {
		DrawHandler.drawFrame();
	}

}
