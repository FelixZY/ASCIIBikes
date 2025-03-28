package felyv527.tddc77.asciibikes.menu;

/**
 * An item in a menu.
 * 
 * <p>
 * All items in a menu must belong to the class {@link MenuItem}, or one of its
 * subclasses.
 *
 */
public interface MenuItem {

	/**
	 * Returns the title to be shown when navigating to this {@link MenuItem}.
	 */
	public String getTitle();

	/**
	 * Returns {@code true} if {@link #show()} can be called, else
	 * {@code false}.
	 */
	public boolean isEnabled();

	/**
	 * Shows this {@link MenuItem}.
	 * 
	 * @return One of the {@link MenuResult} values
	 */
	public MenuResult show();

}
