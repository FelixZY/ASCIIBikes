package felyv527.tddc77.asciibikes.io;

/**
 * Responsible for moving the caret in the terminal.
 *
 */
public class CaretMover {

	/*
	 * Since java cannot (at least it is very hard to) create escape sequences
	 * on the fly, the sequences have been required to be hard coded which is
	 * why this class contains more methods and is less efficient than what I
	 * would have liked.
	 */

	private static enum CaretDirection {
		Right, Down
	}

	private CaretMover() {
		// Utility class. Should not be instantiated.
	}

	/**
	 * Resets the caret to the top right corner without clearing the screen.
	 */
	public static void resetCaret() {
		System.out.print("\033[H");
	}

	/**
	 * Moves the caret {@code n} lines down.
	 * 
	 * @param n
	 *            The number of lines to move the caret down.
	 */
	public static void moveCaretDown(int n) {
		n = move100(CaretDirection.Down, n);
		n = move25(CaretDirection.Down, n);
		n = move10(CaretDirection.Down, n);
		n = move2(CaretDirection.Down, n);
		move1(CaretDirection.Down, n);
	}

	/**
	 * Moves the caret {@code n} columns to the right
	 * 
	 * @param n
	 *            The number of columns to move the caret to the right
	 */
	public static void moveCaretRight(int n) {
		n = move100(CaretDirection.Right, n);
		n = move25(CaretDirection.Right, n);
		n = move10(CaretDirection.Right, n);
		n = move2(CaretDirection.Right, n);
		move1(CaretDirection.Right, n);
	}

	private static int move100(CaretDirection dir, int n) {
		while (n >= 100) {
			n -= 100;

			if (dir == CaretDirection.Down)
				OutputManager.print("\033[100B");
			else if (dir == CaretDirection.Right)
				OutputManager.print("\033[100C");
		}
		return n;
	}

	private static int move25(CaretDirection dir, int n) {
		while (n >= 25) {
			n -= 25;
			if (dir == CaretDirection.Down)
				OutputManager.print("\033[25B");
			else if (dir == CaretDirection.Right)
				OutputManager.print("\033[25C");
		}
		return n;
	}

	private static int move10(CaretDirection dir, int n) {
		while (n >= 10) {
			n -= 10;
			if (dir == CaretDirection.Down)
				OutputManager.print("\033[10B");
			else if (dir == CaretDirection.Right)
				OutputManager.print("\033[10C");
		}
		return n;
	}

	private static int move2(CaretDirection dir, int n) {
		while (n >= 2) {
			n -= 2;
			if (dir == CaretDirection.Down)
				OutputManager.print("\033[2B");
			else if (dir == CaretDirection.Right)
				OutputManager.print("\033[2C");
		}
		return n;
	}

	private static int move1(CaretDirection dir, int n) {
		while (n >= 1) {
			n -= 1;
			if (dir == CaretDirection.Down)
				OutputManager.print("\033[1B");
			else if (dir == CaretDirection.Right)
				OutputManager.print("\033[1C");
		}
		return n;
	}
}
