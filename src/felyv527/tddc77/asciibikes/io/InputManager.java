package felyv527.tddc77.asciibikes.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * {@code InputManager} provides methods for reading key presses from a terminal
 * in raw mode.
 * 
 * <p>
 * {@code InputManager} does not read uppercase letters.
 *
 */
public class InputManager {

	private static Reader inputReader;

	static {
		inputReader = new BufferedReader(new InputStreamReader(System.in));
	}

	private InputManager() {
		// Utility class. Should not be instantiated.
	}

	/**
	 * Returns a {@link Set<Keys>} of pressed keyboard keys.
	 * 
	 * <p>
	 * The {@link Set} returned by this method consists of all recognized
	 * keyboard keys that have been pressed since the last call to this method
	 * or {@link #getPressedKeys()}.
	 * 
	 * <p>
	 * If no keys have been pressed, this method will block until input is
	 * available.
	 */
	public static Set<Keys> readKeysBlocking() {
		Queue<Integer> keyData = new ArrayDeque<>();

		try {
			keyData.add(inputReader.read());
			// If there is more data, we keep reading it. This allows the
			// reading of all keys that have been pressed since the last read.
			while (inputReader.ready()) {
				keyData.add(inputReader.read());
			}
			return Keys.fromRawKeyCodeQueue(keyData);
		} catch (Exception e) {
			Set<Keys> keyList = new HashSet<Keys>();
			keyList.add(Keys.UNKNOWN);
			return keyList;
		}
	}

	/**
	 * Returns a {@link Set<Keys>} of pressed keyboard keys without blocking.
	 * 
	 * <p>
	 * The {@link Set} returned by this method consists of all recognized
	 * keyboard keys that have been pressed since the last call to this method
	 * or {@link #readKeysBlocking()}.
	 * 
	 * <p>
	 * If no keys have been pressed, the returned Set will be empty.
	 */
	public static Set<Keys> getPressedKeys() {
		Set<Keys> keyList = new HashSet<Keys>();
		try {
			// By checking if there is anything to read before we read it in a
			// blocking fashion, we avoid blocking when there is no data
			// available.
			if (inputReader.ready())
				return readKeysBlocking();
			else {
				return keyList;
			}
		} catch (IOException e) {
			keyList.add(Keys.UNKNOWN);
			return keyList;
		}
	}

}
