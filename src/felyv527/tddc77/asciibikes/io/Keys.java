package felyv527.tddc77.asciibikes.io;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Keys readable by InputManager's readKey method
 * 
 */
public enum Keys {
	UNKNOWN(-1, '\u0000'), NONE(0, '\u0000'), CTRL_C(3, '\u0000'), ESCAPE(27, '\u0000'), ENTER(13, '\n'),
	SPACEBAR(32, ' '), TAB(9, '\u0000'), BACKSPACE(127, '\u0000'), DELETE(279151126, '\u0000'), //
	A(97, 'A'), B(98, 'B'), C(99, 'C'), D(100, 'D'), E(101, 'E'), F(102, 'F'), G(103, 'G'), H(104, 'H'), I(105, 'I'),
	J(106, 'J'), K(107, 'K'), L(108, 'L'), M(109, 'M'), N(110, 'N'), O(111, 'O'), P(112, 'P'), Q(113, 'Q'), R(114, 'R'),
	S(115, 'S'), T(116, 'T'), U(117, 'U'), V(118, 'V'), W(119, 'W'), X(120, 'X'), Y(121, 'Y'), Z(122, 'Z'),
	A_RING(229, '\u00C5'), A_UML(228, '\u00C4'), O_UML(246, '\u00D6'), //
	NUM_0(48, '0'), NUM_1(49, '1'), NUM_2(50, '2'), NUM_3(51, '3'), NUM_4(52, '4'), NUM_5(53, '5'), NUM_6(54, '6'),
	NUM_7(55, '7'), NUM_8(56, '8'), NUM_9(57, '9'), //
	COMMA(44, ','), PERIOD(46, '.'), PLUS(43, '+'), MINUS(45, '-'), SLASH(47, '/'), STAR(42, '*'), //
	ARROW_LEFT(279168, '\u0000'), ARROW_RIGHT(279167, '\u0000'), ARROW_UP(279165, '\u0000'),
	ARROW_DOWN(279166, '\u0000');

	private static final Map<Integer, Keys> keysByValue = new HashMap<Integer, Keys>();

	private static final int maxKeyValue;

	static {
		int max = 0;
		for (Keys key : Keys.values()) {
			keysByValue.put(key.keyCode, key);
			if (key.keyCode > max)
				max = key.keyCode;
		}
		maxKeyValue = max;
	}

	private final int keyCode;
	private final char representation;

	Keys(int keyCode, char representation) {
		this.keyCode = keyCode;
		this.representation = representation;
	}

	/**
	 * Returns the keycode this {@link Keys} represents.
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/**
	 * Returns the char this {@link Keys} represents.
	 * 
	 * <p>
	 * Note that this is not an accurate representation, meaning that some keys
	 * will return {@code \u0000} and that letters will return their uppercase
	 * representation though the keyCode is for lowercase.
	 */
	public char getCharRepresentation() {
		return representation;
	}

	/**
	 * Retrieves the {@link Keys} that is represented by the specified keyCode
	 * or {@link Keys#UNKNOWN} if there is no match.
	 * 
	 * @param keyCode
	 *            The keyCode for which to get a {@link Keys} representation
	 * @return A {@link Keys} enum representing the provided {@code keyCode} or
	 *         {@link Keys#UNKNOWN} if no match was found
	 */
	protected static Keys fromKeyCode(int keyCode) {
		if (keysByValue.containsKey(keyCode))
			return keysByValue.get(keyCode);
		else
			return Keys.UNKNOWN;
	}

	/**
	 * Converts a queue of key codes read raw from the terminal into a
	 * {@link Set} of {@link Keys}
	 * 
	 * @param rawKeyCodeQueue
	 *            The rawly inputed key codes to to retrieve {@link Keys} for.
	 * @return A {@link Set} of all contained {@link Keys}
	 */
	protected static Set<Keys> fromRawKeyCodeQueue(Queue<Integer> rawKeyCodeQueue) {
		List<Keys> keys = new ArrayList<Keys>();
		int index = -1;

		while (rawKeyCodeQueue.size() > 0) {
			index++;

			if (fromKeyCode(rawKeyCodeQueue.peek()) == Keys.ESCAPE) {
				/*
				 * Most special keys start out with the sequence for {@link
				 * Keys#ESCAPE}. These, including {@link Keys#ESCAPE} are
				 * checked here.
				 */

				keys.add(Keys.UNKNOWN);

				/*
				 * Since special keys may contain multiple key codes, we need to
				 * look ahead without affecting our original queue in case it's
				 * nothing.
				 */
				Queue<Integer> escapeSequenceQueue = new ArrayDeque<>(rawKeyCodeQueue);
				int keyCode = escapeSequenceQueue.poll();

				while (escapeSequenceQueue.size() > 0 && keyCode <= maxKeyValue && keys.get(index) == Keys.UNKNOWN) {
					keyCode = Integer.parseInt(keyCode + "" + escapeSequenceQueue.poll());
					if (keysByValue.containsKey(keyCode))
						keys.set(index, fromKeyCode(keyCode));
				}

				if (keys.get(index) == Keys.UNKNOWN) {
					// No special key other than {@link Keys#ESCAPE} was found.
					keys.set(index, Keys.ESCAPE);
					// We pop only the topmost key code.
					rawKeyCodeQueue.remove();
				} else {
					/*
					 * We found a special key. Now we need to remove all key
					 * codes consumed to find this key from our original queue.
					 */
					int toPop = rawKeyCodeQueue.size() - escapeSequenceQueue.size();
					for (int i = 0; i < toPop; i++)
						rawKeyCodeQueue.remove();
				}

			} else {
				// This is a regular key.
				keys.add(fromKeyCode(rawKeyCodeQueue.poll()));
			}
		}

		return new HashSet<Keys>(keys);
	}
}
