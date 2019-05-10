package veggie.screen;


public interface ScreenSwitcher {
	public static final int MENU = 0;
	public static final int INSTRUCTION = 1;
	public static final int PLATFORM = 2;
	public static final int BATTLE = 3;

	
	public void switchScreen(int i);
}
