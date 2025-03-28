package felyv527.tddc77.asciibikes.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class InputManager {

	private static Reader inputReader;

	static {
		inputReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public static Set<Keys> readKeysBlocking() {
		Queue<Integer> keyData = new ArrayDeque<>();

		try {
			keyData.add(inputReader.read());
			while (inputReader.ready()) {
				keyData.add(inputReader.read());
			}
			return Keys.fromKeyQueue(keyData);
		} catch (Exception e) {
			Set<Keys> keyList = new HashSet<Keys>();
			keyList.add(Keys.UNKNOWN);
			return keyList;
		}
	}

	public static Set<Keys> getPressedKeys() {
		Set<Keys> keyList = new HashSet<Keys>();
		try {
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

	protected static char readRawBlockingInput() {
		try {
			return (char) inputReader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return '\0';
	}

}
