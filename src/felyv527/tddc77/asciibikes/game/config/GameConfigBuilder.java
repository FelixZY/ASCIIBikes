package felyv527.tddc77.asciibikes.game.config;

import java.util.HashSet;
import java.util.Set;

import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.game.Direction;
import felyv527.tddc77.asciibikes.game.arena.Arena;
import felyv527.tddc77.asciibikes.game.arena.Arenas;
import felyv527.tddc77.asciibikes.io.Keys;
import felyv527.tddc77.asciibikes.menu.EnumSelectionMenu;
import felyv527.tddc77.asciibikes.menu.InputMenu;
import felyv527.tddc77.asciibikes.menu.KeyReaderMenu;
import felyv527.tddc77.asciibikes.menu.SimpleMenu;
import felyv527.tddc77.asciibikes.menu.MenuResult;
import felyv527.tddc77.asciibikes.menu.NavigationMenuItem;

/**
 * Creates {@link GameConfig}s based on user input.
 *
 */
public class GameConfigBuilder {

	private static Set<TerminalForegroundColor> selectedColors = new HashSet<>();

	/**
	 * Requests as little information as possible from the player and returns a
	 * {@link GameConfig}.
	 * 
	 * <p>
	 * This method has support for up to 6 players.
	 */
	public static GameConfig requestQuickplayConfig() {
		// First we get the number of players and initialize a new GameConfig.
		GameConfig gameConfig = new GameConfig(getNumPlayers(6));

		// Round count selection
		gameConfig.setGameRounds(getRoundCount());

		// Arena selection
		gameConfig.setArena(getArena(gameConfig.getPlayerCount()));

		for (int i = 0; i < gameConfig.getPlayerCount(); i++) {
			KeyboardPlayerInfo playerConfig = new KeyboardPlayerInfo();
			// To minimize the setup requirements this method uses default
			// settings for player names and controls. This also limits the
			// number of players to the hard coded amount, 6
			switch (i) {
				case 0:
					playerConfig = new KeyboardPlayerInfo("Blueberry", TerminalForegroundColor.BLUE,
							TrailLength.DEFAULT.length, Keys.ARROW_LEFT, Keys.ARROW_RIGHT);
					break;
				case 1:
					playerConfig = new KeyboardPlayerInfo("Hank", TerminalForegroundColor.GREEN,
							TrailLength.DEFAULT.length, Keys.Z, Keys.X);
					break;
				case 2:
					playerConfig = new KeyboardPlayerInfo("Chili", TerminalForegroundColor.RED,
							TrailLength.DEFAULT.length, Keys.COMMA, Keys.PERIOD);
					break;
				case 3:
					playerConfig = new KeyboardPlayerInfo("Mr. Baninja", TerminalForegroundColor.YELLOW,
							TrailLength.DEFAULT.length, Keys.NUM_2, Keys.NUM_3);
					break;
				case 4:
					playerConfig = new KeyboardPlayerInfo("Mag. Enta", TerminalForegroundColor.MAGENTA,
							TrailLength.DEFAULT.length, Keys.T, Keys.Y);
					break;
				case 5:
					playerConfig = new KeyboardPlayerInfo("Cyanide", TerminalForegroundColor.CYAN,
							TrailLength.DEFAULT.length, Keys.PLUS, Keys.MINUS);
					break;

			}
			// Here, we display the preset names and controls for the players
			showMessage("Quick Play",
					Colorizer.colorize(" " + playerConfig.getName() + " ", TerminalForegroundColor.WHITE,
							playerConfig.getBackgroundColor(), TerminalColorMode.BRIGHT) + "\nLeft: "
							+ playerConfig.getTurnLeftKey().name() + "\nRight: "
							+ playerConfig.getTurnRightKey().name(),
					"Next");

			gameConfig.setPlayerConfig(i, playerConfig);
		}

		showMessage("Quick Play", "", "Start Game");

		return gameConfig;
	}

	/**
	 * Requests all information necessary to set up a custom game and customized
	 * players and returns a customized {@link GameConfig}
	 */
	public static GameConfig requestCustomConfig() {
		/*
		 * The users are going to pick colors themselves. These colors are
		 * either unusable or reserved and so we add them to selectedColors
		 * beforehand so that they are unselectable.
		 */
		selectedColors.add(TerminalForegroundColor.BLACK);
		selectedColors.add(TerminalForegroundColor.WHITE);
		selectedColors.add(TerminalForegroundColor.DEFAULT);

		showMessage("New config", "", "Start");

		// Number of players selection
		GameConfig gameConfig = new GameConfig(getNumPlayers());

		// Round count selection
		gameConfig.setGameRounds(getRoundCount());

		// Trail length selection
		int trailLength = getTrailLength();

		for (int i = 0; i < gameConfig.getPlayerCount(); i++) {
			KeyboardPlayerInfo playerConfig = new KeyboardPlayerInfo();

			/*
			 * The trail length is already fetched as it is global but each bike
			 * gets their own value.
			 */
			playerConfig.setTrailLength(trailLength);

			showMessage("Player " + (i + 1) + " setup", "", "Start");

			/*
			 * Here we fetch the player name. After this, we want to adress the
			 * player with their selected name so we save their name to a
			 * variable.
			 */
			String playerName = getPlayerName(i);
			playerConfig.setName(playerName);

			// Control selection
			showMessage(playerName + " setup", "Time to select your controls", "Ok");

			playerConfig.setTurnLeftKey(getPlayerButton(playerName, Direction.LEFT));
			playerConfig.setTurnRightKey(getPlayerButton(playerName, Direction.RIGHT));

			showMessage(playerName + " setup", "Finally, you need to pick a color", "Ok");

			// Color selection
			playerConfig.setColor(getPlayerColor(playerName));

			selectedColors.add(playerConfig.getForegroundColor());

			// The player config is saved
			gameConfig.setPlayerConfig(i, playerConfig);
		}
		// Arena selection
		gameConfig.setArena(getArena(gameConfig.getPlayerCount()));

		showMessage("Setup complete", "", "Start game");

		selectedColors.clear();

		return gameConfig;
	}

	/**
	 * Shorthand for showing a message menu item with a back button.
	 * 
	 * @param title
	 *            The title for this message
	 * @param message
	 *            The message to show
	 * @param buttonTitle
	 *            The title on the back button
	 */
	private static void showMessage(String title, String message, String buttonTitle) {
		(new SimpleMenu(title, message, new NavigationMenuItem(buttonTitle, MenuResult.EXIT))).show();
	}

	private static int getNumPlayers() {
		/*
		 * To prevent mixups we only allow as many players as there are colors
		 * (or a maximum of 9)
		 * 
		 * If there should be added more than 9 colors in the future, {@link
		 * getNumPlayers(int maxPlayers)}'s regex will need to be modified.
		 */
		int maxPlayers = Math.min(TerminalForegroundColor.values().length - selectedColors.size(), 9);
		return getNumPlayers(maxPlayers);
	}

	private static int getNumPlayers(int maxPlayers) {
		/*
		 * Handles 2-9 players. If numPlayers would grow bigger in the future,
		 * the regex will need to be modified.
		 */
		InputMenu inputMenu = new InputMenu("Number of players",
				"Please enter the number of players (2-" + maxPlayers + "): ", "[2-" + maxPlayers + "]", 1, 1);
		inputMenu.show();
		return Integer.parseInt(inputMenu.getInput());
	}

	private static int getRoundCount() {
		int roundCount;

		do {
			InputMenu inputMenu = new InputMenu("Rounds", "Please enter the amount of rounds you would like to play: ",
					"[0-9]", 1, 5);
			inputMenu.show();
			roundCount = Integer.parseInt(inputMenu.getInput());
		} while (roundCount == 0);

		return roundCount;
	}

	private static int getTrailLength() {
		int result;

		EnumSelectionMenu<TrailLength> trailLengthSelect = new EnumSelectionMenu<TrailLength>("Trail length",
				"How long should your bike trails be? ", TrailLength.class);

		trailLengthSelect.show();

		if (trailLengthSelect.getSelection() == TrailLength.CUSTOM) {
			/*
			 * Displays the numerical length of the original options and allow
			 * the user to input a custom numerical length.
			 */
			InputMenu inputMenu = new InputMenu("Trail length",
					"Default trail: " + TrailLength.DEFAULT.length + " units" //
							+ "\nLong trail: " + TrailLength.LONG.length + " units" //
							+ "\nShort trail: " + TrailLength.SHORT.length + " units" //
							+ "\n\nPlease enter a custom trail length: ",
					"\\d", 1, 5);
			inputMenu.show();
			result = Integer.parseInt(inputMenu.getInput());
		} else
			result = trailLengthSelect.getSelection().length;

		return result;
	}

	private static String getPlayerName(int playerIndex) {
		playerIndex++;
		InputMenu inputMenu = new InputMenu("Player " + playerIndex + " setup",
				"Please pick a name, player " + playerIndex + ": ", "[\\wÅÄÖ]", 2, 10);
		inputMenu.show();
		return inputMenu.getInput();
	}

	private static Keys getPlayerButton(String playerName, Direction dir) {
		KeyReaderMenu keyReader;
		do {
			// Requests a button to use for turning.
			keyReader = new KeyReaderMenu(playerName + " setup",
					"Select a button to use for turning " + dir.name() + ": ");
			keyReader.show();

			// Asks the user to confirm their choice
		} while ((new SimpleMenu(playerName + " setup",
				"Please confirm that you wish to use " + keyReader.getKey().name() + " to turn " + dir.name(),
				new NavigationMenuItem("Confirm", MenuResult.GO_BACK), new NavigationMenuItem("Change", MenuResult.EXIT)))
						.show() != MenuResult.COMPLETED);

		return keyReader.getKey();
	}

	private static TerminalForegroundColor getPlayerColor(String playerName) {
		EnumSelectionMenu<TerminalForegroundColor> colSelect = new EnumSelectionMenu<TerminalForegroundColor>(
				playerName + " setup", "Select your color", TerminalForegroundColor.class, selectedColors);
		colSelect.show();
		return colSelect.getSelection();
	}

	private static Arena getArena(int numPlayers) {
		EnumSelectionMenu<Arenas> arenaSelect = new EnumSelectionMenu<Arenas>("Arena select", "Select your arena",
				Arenas.class);
		arenaSelect.show();
		return Arenas.getArena(arenaSelect.getSelection());
	}
}
