package felyv527.tddc77.asciibikes.menu;

public class ExitMenuItem implements MenuItem {

	private boolean enabled = true;

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getTitle() {
		return "Exit";
	}

	@Override
	public MenuResult show() {
		return MenuResult.RESULT_CLOSE_MENU_TREE;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
