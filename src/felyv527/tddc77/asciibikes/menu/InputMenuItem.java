package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

public class InputMenuItem extends ButtonedMenuBase {

	private int minLength, maxLength;

	private StringBuilder input = new StringBuilder();

	private String hint, regex;

	private GoBackMenuItem doneButton = new GoBackMenuItem("Done");

	public InputMenuItem(String title, String hint) {
		this(title, hint, ".*", 0, Integer.MAX_VALUE);
	}

	public InputMenuItem(String title, String hint, String regex) {
		this(title, hint, regex, 0, Integer.MAX_VALUE);
	}

	public InputMenuItem(String title, String hint, int minInputLength) {
		this(title, hint, ".*", minInputLength, Integer.MAX_VALUE);
	}

	public InputMenuItem(String title, String hint, String regex,
			int minInputLength) {
		this(title, hint, regex, minInputLength, Integer.MAX_VALUE);
	}

	public InputMenuItem(String title, String hint, int minInputLength,
			int maxInputLength) {
		this(title, hint, ".*", minInputLength, maxInputLength);
	}

	public InputMenuItem(String title, String hint, String regex,
			int minInputLength, int maxInputLength) {
		super(title);
		this.hint = hint;
		this.regex = regex;
		this.minLength = minInputLength;
		this.maxLength = maxInputLength;
		this.addButton(doneButton);
	}

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
		setMessage(hint);
		doneButton.setEnabled(input.length() >= minLength
				&& input.length() <= maxLength);
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
						&& Character.toString(key.getCharRepresentation())
								.matches(regex))
					input.append(key.getCharRepresentation());
				break;
			}
		}

		setMessage(hint + input.toString());

		doneButton.setEnabled(input.length() >= minLength
				&& input.length() <= maxLength);

		return MenuResult.RESULT_OK;
	}

	@Override
	protected void onPostShow() {

	}

}
