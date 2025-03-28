package felyv527.tddc77.asciibikes.menu;

/**
 * A simple {@link MenuItem} suitable for back or exit buttons.
 *
 */
public class NavigationMenuItem implements MenuItem {

	private final String title;
	private final MenuResult result;

	private boolean enabled = true;

	/**
	 * Initializes a {@link MenuItem} suitable for back or exit buttons with
	 * custom text.
	 * 
	 * <p>
	 * If this item's {@link #show()} method is called, it will instantly return
	 * the supplied {@link MenuResult}
	 * 
	 * @param title
	 *            The text to show as this item's title
	 * @param result
	 *            The {@link MenuResult} to return if this item's
	 *            {@link #show()} method is called
	 */
	public NavigationMenuItem(String title, MenuResult result) {
		this.title = title;
		this.result = result;
	}

	/**
	 * Sets whether or not this item's {@link #show()} method can be called.
	 * 
	 * @param enabled
	 *            {@code true} if {@link #show()} can be called. Else
	 *            {@code false}
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public MenuResult show() {
		return result;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
