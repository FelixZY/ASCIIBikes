package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.CountDownOverlay.CountDownCompletedListener;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.OutputManager;
import felyv527.tddc77.asciibikes.io.TerminalInfo;
import felyv527.tddc77.asciibikes.menu.InputMenuItem;
import felyv527.tddc77.asciibikes.menu.GoBackMenuItem;
import felyv527.tddc77.asciibikes.menu.Menu;
import felyv527.tddc77.asciibikes.menu.ExitMenuItem;
import felyv527.tddc77.asciibikes.menu.MenuItem;
import felyv527.tddc77.asciibikes.menu.MenuResult;
import felyv527.tddc77.asciibikes.text.HeadlineFont;
import felyv527.tddc77.asciibikes.text.StandardFont;
import felyv527.tddc77.asciibikes.text.TextAlignment;

public class Main {
	private static final Point minSize = new Point(60, 20);

	private static final String[][] launchCommands = new String[][] { //
			{
					"/bin/sh", "-c", "stty raw </dev/tty"
			}, //
			{
					"/bin/sh", "-c", "stty -echo </dev/tty"
			}, //
			{
					"/bin/sh", "-c", "tput civis >/dev/tty"
			}
	//

	};
	private static final String[][] exitCommands = new String[][] { //
			{
					"/bin/sh", "-c", "tput cnorm >/dev/tty"
			}, //
			{
					"/bin/sh", "-c", "stty echo </dev/tty"
			}, //
			{
					"/bin/sh", "-c", "stty cooked </dev/tty"
			}, //
	};

	public static void main(String[] args) {
		if (!System.getProperty("os.name").startsWith("Linux")) {
			System.out.println("ASCIIBikes is unfortunately only compatible with linux.\n"
					+ "Please switch to a linux system to play!");
			return;
		}
		OutputManager.clear();
		for (String[] command : launchCommands)
			execCommand(command);

		if (TerminalInfo.getTerminalCols() < minSize.getX() || TerminalInfo.getTerminalLines() < minSize.getY()) {
			OutputManager.println("This terminal is too small for playing ASCIIBikes.\n"
					+ "Please resize your terminal to be at least " + minSize.getX() + " cols by " + minSize.getY()
					+ " rows to play");
		} else {
			SplashScreen.show();

			new Menu("ASCIIBikes", "Welcome, racers.", //
					new MenuItem() {

						@Override
						public String getTitle() {
							return "Start game";
						}

						@Override
						public MenuResult show() {
							Game g = new AsciiBikesGame();
							g.Run();

							return MenuResult.RESULT_OK;
						}

						@Override
						public boolean isEnabled() {
							return true;
						}

					}, new Menu("Debug menu", "", //
							new MenuItem() {

								@Override
								public String getTitle() {
									return "Splash screen";
								}

								@Override
								public MenuResult show() {
									SplashScreen.show();
									return MenuResult.RESULT_OK;
								}

								@Override
								public boolean isEnabled() {
									return true;
								}

							}, new MenuItem() {

								boolean loopDone = false;

								@Override
								public String getTitle() {
									return "Countdown overlay";
								}

								@Override
								public MenuResult show() {
									loopDone = false;
									InputMenuItem input = new InputMenuItem("Select duration", "Duration (millis): ",
											"\\d", 1);
									input.show();
									CountDownOverlay cdo = new CountDownOverlay(Integer.valueOf(input.getInput()));
									cdo.start(new CountDownCompletedListener() {

										@Override
										public void onCountDownCompleted() {
											loopDone = true;
										}

									});
									while (!loopDone) {
										cdo.update(50);
										DrawHandler.drawFrame();
										try {
											Thread.sleep(50);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}

									return MenuResult.RESULT_OK;
								}

								@Override
								public boolean isEnabled() {
									return true;
								}

							}, new InputMenuItem("Input menu test", "Please enter some text: ", 3, 18),//
							new MenuItem() {

								@Override
								public String getTitle() {
									return "Regex input test";
								}

								@Override
								public boolean isEnabled() {

									return true;
								}

								@Override
								public MenuResult show() {
									(new Menu(
											"Regex input test",
											"Some selected regexes will now be tested.\nOnly characters matching the regex will be displayed.",
											new GoBackMenuItem("Start"))).show();

									InputMenuItem IMU = new InputMenuItem(".*", "> ", ".*");
									IMU.show();

									IMU = new InputMenuItem("[A-Z]", "> ", "[A-Z]");
									IMU.show();

									IMU = new InputMenuItem("\\w", "> ", "\\w");
									IMU.show();

									IMU = new InputMenuItem("\\d", "> ", "\\d");
									IMU.show();

									IMU = new InputMenuItem("[0-9]", "> ", "[0-9]");
									IMU.show();

									return MenuResult.RESULT_OK;
								}

							}, new MenuItem() {

								@Override
								public String getTitle() {
									return "Text test";
								}

								@Override
								public MenuResult show() {
									printTextTest();
									pause();
									return MenuResult.RESULT_OK;
								}

								@Override
								public boolean isEnabled() {
									return true;
								}

							}, new Menu("Terminal dimensions", "Columns: " + TerminalInfo.getTerminalCols()
									+ "\nLines: " + TerminalInfo.getTerminalLines(), new GoBackMenuItem()),//
							new MenuItem() {

								@Override
								public String getTitle() {
									return "Basically an exit button";
								}

								@Override
								public boolean isEnabled() {
									return true;
								}

								@Override
								public MenuResult show() {
									return MenuResult.RESULT_CLOSE_MENU_TREE;
								}

							}, new MenuItem() {

								@Override
								public String getTitle() {
									return "Disabled menu entry";
								}

								@Override
								public boolean isEnabled() {
									return false;
								}

								@Override
								public MenuResult show() {
									return MenuResult.RESULT_CLOSE_MENU_TREE;
								}

							}, new GoBackMenuItem()), //
					new ExitMenuItem() //
			).show();

			OutputManager.clear();
		}

		for (String[] command : exitCommands)
			execCommand(command);
	}

	private static void printTextTest() {
		OutputManager.clear();

		int cursorLine = 1;

		for (String line : (new HeadlineFont()).applyFont(
				"1234567890_-+?!@\nThe quick, brown, fox\njumps over the lazy dog.", TextAlignment.CENTER)) {
			OutputManager.println(line);
			cursorLine++;
		}

		for (String line : (new HeadlineFont()).applyFont("Game Over")) {
			OutputManager.println(line);
			cursorLine++;
		}
		for (String line : (new StandardFont()).applyFont("Standard\nfont", TextAlignment.CENTER)) {
			OutputManager.println(line);
			cursorLine++;
		}

		while (cursorLine < TerminalInfo.getTerminalLines()) {
			cursorLine++;
			OutputManager.println();
		}
	}

	private static void pause() {
		OutputManager.print(Colorizer.colorize("Press any key to continue...", TerminalForegroundColor.BLACK,
				TerminalBackgroundColor.GREEN));
		InputManager.readKeysBlocking();
		OutputManager.clear();
	}

	private static void execCommand(String[] command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
