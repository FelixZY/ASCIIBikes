package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.game.AsciiBikesGame;
import felyv527.tddc77.asciibikes.game.config.GameConfig;
import felyv527.tddc77.asciibikes.game.config.GameConfigBuilder;
import felyv527.tddc77.asciibikes.io.OutputManager;
import felyv527.tddc77.asciibikes.menu.SimpleMenu;
import felyv527.tddc77.asciibikes.menu.MenuItem;
import felyv527.tddc77.asciibikes.menu.MenuResult;
import felyv527.tddc77.asciibikes.menu.ScoreboardMenu;
import felyv527.tddc77.asciibikes.menu.NavigationMenuItem;

/**
 * Main entry point for ASCIIBikes and menu driver
 */
public class Main {
	private static final Point minSize = new Point(60, 20);

	private static final String[][] launchCommands = new String[][] { //
			// Puts the terminal into a raw mode
			// Used to read input live from the terminal
			{ "/bin/sh", "-c", "stty raw </dev/tty" }, //

			// Turns off terminal echo
			{ "/bin/sh", "-c", "stty -echo </dev/tty" }, //

			// Hides the terminal cursor
			{ "/bin/sh", "-c", "tput civis >/dev/tty" } //

	};
	private static final String[][] exitCommands = new String[][] { //
			// Shows the terminal cursor
			{ "/bin/sh", "-c", "tput cnorm >/dev/tty" }, //

			// Turns on terminal echo
			{ "/bin/sh", "-c", "stty echo </dev/tty" }, //

			// Puts the terminal into cooked mode
			{ "/bin/sh", "-c", "stty cooked </dev/tty" } //
	};

	private static GameConfig customConfig = null;

	public static void main(String[] args) {
		if (!System.getProperty("os.name").startsWith("Linux")) {
			System.out.println("ASCIIBikes is unfortunately only compatible with linux.\n"
					+ "Please switch to a linux system to play!");

		} else if (TerminalDimensions.getNumColumns() < minSize.getX()
				|| TerminalDimensions.getNumLines() < minSize.getY()) {
			System.out.println("This terminal is too small for playing ASCIIBikes.\n"
					+ "Please resize your terminal to be at least " + minSize.getX() + " cols by " + minSize.getY()
					+ " rows to play");

		} else {
			// This system should be able to support ASCIIBikes

			// In order to read input live etc. we need to execute some commands
			// in the terminal before we start.
			for (String[] command : launchCommands) {
				try {
					Runtime.getRuntime().exec(command);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			OutputManager.clear();

			(new SplashScreen()).run();

			// Main menu
			new SimpleMenu("ASCIIBikes", "Welcome, racers.", //
					new MenuItem() {
						// Minimum-setup game mode

						@Override
						public String getTitle() {
							return "Quick Play";
						}

						@Override
						public boolean isEnabled() {
							return true;
						}

						@Override
						public MenuResult show() {
							startGame(GameConfigBuilder.requestQuickplayConfig());
							return MenuResult.COMPLETED;
						}

					}, //
					new SimpleMenu("Custom Game", "Create new config or use previous settings?", //
							new MenuItem() {

								@Override
								public String getTitle() {
									return "New game configuration";
								}

								@Override
								public boolean isEnabled() {
									return true;
								}

								@Override
								public MenuResult show() {
									customConfig = GameConfigBuilder.requestCustomConfig();
									startGame(customConfig);
									return MenuResult.GO_BACK;
								}

							}, //
							new MenuItem() {

								@Override
								public String getTitle() {
									return "Use previous settings";
								}

								@Override
								public MenuResult show() {
									startGame(customConfig);
									return MenuResult.GO_BACK;
								}

								@Override
								public boolean isEnabled() {
									// Previous settings are only available if
									// the user has previously created a custom
									// game during this session.
									return customConfig != null;
								}

							}, //
							new NavigationMenuItem("Back", MenuResult.GO_BACK) //
					), //
					new ScoreboardMenu("Scoreboard"), //
					new NavigationMenuItem("Exit", MenuResult.EXIT) //
			).show();

			// Resets the terminal to a more normal state.
			// Note that this totally disregards the user's standard settings.
			for (String[] command : exitCommands) {
				try {
					Runtime.getRuntime().exec(command);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			OutputManager.clear();
		}
	}

	private static void startGame(GameConfig config) {
		(new AsciiBikesGame(config)).run();
	}

}
