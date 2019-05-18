package veggie.screen;


public interface ScreenSwitcher {
	/**
	 * the integer code of the menu screen
	 */
	
	public static final int MENU = 0;
	/**
	 * the integer code of the instruction screen
	 */
	
	public static final int INSTRUCTION = 1;
	
	/**
	 * the integer code of the platformMode screen
	 */
	public static final int PLATFORM = 2;
	
	/**
	 * the integer code of the battleMode screen
	 */
	public static final int BATTLE = 3;
	
	
	public void switchScreen(int i);
}