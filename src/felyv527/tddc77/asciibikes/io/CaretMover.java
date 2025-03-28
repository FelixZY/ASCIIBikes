package felyv527.tddc77.asciibikes.io;

public class CaretMover {

	private static enum Direction {
		Right, Down
	}

	public static void resetCaret() {
		System.out.print("\033[H");
	}

	public static void moveCaretDown(int n) {
		n = move100(Direction.Down, n);
		n = move25(Direction.Down, n);
		n = move10(Direction.Down, n);
		n = move2(Direction.Down, n);
		move1(Direction.Down, n);
	}

	public static void moveCaretRight(int n) {
		n = move100(Direction.Right, n);
		n = move25(Direction.Right, n);
		n = move10(Direction.Right, n);
		n = move2(Direction.Right, n);
		move1(Direction.Right, n);
	}

	private static int move100(Direction dir, int n) {
		while (n >= 100) {
			n -= 100;

			if (dir == Direction.Down)
				OutputManager.print("\033[100B");
			else if (dir == Direction.Right)
				OutputManager.print("\033[100C");
		}
		return n;
	}

	private static int move25(Direction dir, int n) {
		while (n >= 25) {
			n -= 25;
			if (dir == Direction.Down)
				OutputManager.print("\033[25B");
			else if (dir == Direction.Right)
				OutputManager.print("\033[25C");
		}
		return n;
	}

	private static int move10(Direction dir, int n) {
		while (n >= 10) {
			n -= 10;
			if (dir == Direction.Down)
				OutputManager.print("\033[10B");
			else if (dir == Direction.Right)
				OutputManager.print("\033[10C");
		}
		return n;
	}

	private static int move2(Direction dir, int n) {
		while (n >= 2) {
			n -= 2;
			if (dir == Direction.Down)
				OutputManager.print("\033[2B");
			else if (dir == Direction.Right)
				OutputManager.print("\033[2C");
		}
		return n;
	}

	private static int move1(Direction dir, int n) {
		while (n >= 1) {
			n -= 1;
			if (dir == Direction.Down)
				OutputManager.print("\033[1B");
			else if (dir == Direction.Right)
				OutputManager.print("\033[1C");
		}
		return n;
	}
}
