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
	public static final int GAME_OVER = 3;
	
	/**
	 * the 
	 */
	public static final int BATTLE = 4;
	
	/** Switches the screen based on the designated number.
	 * 
	 * @param i the number of the screen to be switched to
	 */
	public void switchScreen(int i);
}