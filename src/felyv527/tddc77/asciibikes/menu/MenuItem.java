package felyv527.tddc77.asciibikes.menu;

public interface MenuItem {

	public String getTitle();
	
	public boolean isEnabled();
	
	public MenuResult show();
	
}
