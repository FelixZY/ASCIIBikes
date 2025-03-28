package felyv527.tddc77.asciibikes.game.ui;

import java.util.LinkedList;
import java.util.List;

import felyv527.tddc77.asciibikes.drawing.DrawManager;

/**
 * A class managing all hud/ui elements in the game
 *
 */
public class UiManager {

	private List<UiManagable> uiElements = new LinkedList<UiManagable>();

	public UiManager() {
		uiElements.add(new RoundDisplay());
		uiElements.add(new RoundTimer());
		uiElements.add(new ScoreDisplay());
	}

	/**
	 * Registers all ui components for draw.
	 */
	public void showUi() {
		/*
		 * Since the ui is showing throughout the game and the DrawHandler
		 * session is cleared after the game is over, there is no need for a
		 * hideUi() method.
		 */
		for (UiManagable uiElement : uiElements)
			DrawManager.registerForOverlayDraw(uiElement);
	}

	/**
	 * Should be called on every game round start
	 */
	public void onRoundStart() {
		for (UiManagable uiElement : uiElements)
			uiElement.onRoundStart();
	}

	/**
	 * Updates the UI.
	 * 
	 * <p>
	 * Should be called on every game round start
	 * 
	 * @param deltaMillis
	 *            The number of milliseconds since the last update
	 */
	public void update(long deltaMillis) {
		for (UiManagable uiElement : uiElements)
			uiElement.update(deltaMillis);
	}

	/**
	 * Should be called on every game round end
	 */
	public void onRoundEnd() {
		for (UiManagable uiElement : uiElements)
			uiElement.onRoundEnd();
	}

}
