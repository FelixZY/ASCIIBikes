package felyv527.tddc77.asciibikes.menu;

public class GoBackMenuItem implements MenuItem {

	private String title;
	
	private boolean enabled = true;

	public GoBackMenuItem() {
		this("Back");
	}

	public GoBackMenuItem(String buttonText) {
		this.title = buttonText;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public MenuResult show() {
		return MenuResult.RESULT_GO_BACK;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
