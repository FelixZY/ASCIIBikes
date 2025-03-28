package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A menu specialized for reading user input.
 *
 */
public class InputMenu extends ButtonedMenu {

	private int minLength, maxLength;

	private StringBuilder input = new StringBuilder();

	private String hint, regex;

	private NavigationMenuItem doneButton = new NavigationMenuItem("Done", MenuResult.GO_BACK);

	/**
	 * Initializes a menu specialized for reading user input.
	 * 
	 * @param title
	 *            The title of this menu
	 * @param hint
	 *            A hint to show the user
	 * @param regex
	 *            A regex to compare entered characters to
	 * @param minInputLength
	 *            The minimum allowed length of input (inclusive)
	 */
	public InputMenu(String title, String hint, String regex, int minInputLength) {
		this(title, hint, regex, minInputLength, Integer.MAX_VALUE);
	}

	/**
	 * Initializes a menu specialized for reading user input.
	 * 
	 * @param title
	 *            The title of this menu
	 * @param hint
	 *            A hint to show the user
	 * @param regex
	 *            A regex to compare entered characters to
	 * @param minInputLength
	 *            The minimum allowed length of input (inclusive)
	 * @param maxInputLength
	 *            The maximum allowed length of input (inclusive)
	 */
	public InputMenu(String title, String hint, String regex, int minInputLength, int maxInputLength) {
		super(title);

		if (maxInputLength < minInputLength || maxInputLength < 1) {
			throw new RuntimeException("maxInputLength must not be lesser than minInputLength or 0: " + maxInputLength);
		}

		this.hint = hint;
		this.regex = regex;
		this.minLength = minInputLength;
		this.maxLength = maxInputLength;
		this.addButton(doneButton);
	}

	/**
	 * Gets the input given to this menu
	 */
	public String getInput() {
		return input.toString();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void onPreShow() {
		input = new StringBuilder();
		setDescription(hint);
		doneButton.setEnabled(input.length() >= minLength && input.length() <= maxLength);
	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		for (Keys key : pressedKeys) {
			switch (key) {
				case BACKSPACE:
					if (input.length() > 0)
						input.setLength(input.length() - 1);
					break;
				default:
					if (key.getCharRepresentation() != '\u0000'
							&& Character.toString(key.getCharRepresentation()).matches(regex))
						input.append(key.getCharRepresentation());
					break;
			}
		}

		// The hint and input are kept separately
		setDescription(hint + input.toString());

		doneButton.setEnabled(input.length() >= minLength && input.length() <= maxLength);

		return MenuResult.COMPLETED;
	}

	@Override
	protected void onPostShow() {

	}

}
