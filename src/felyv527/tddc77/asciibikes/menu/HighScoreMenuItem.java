package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

public class HighScoreMenuItem extends ButtonedMenuBase {

	public HighScoreMenuItem(String title) {
		super(title);
	}

	@Override
	protected void onPreShow() {

	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		return MenuResult.RESULT_OK;		
	}

	@Override
	protected void onPostShow() {
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
