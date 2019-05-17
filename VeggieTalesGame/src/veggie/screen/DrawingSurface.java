package veggie.screen;

import java.awt.Point;
import java.util.ArrayList;
import processing.core.PApplet;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	/**
	 * ratio for when the screen is resized
	 */
	public float ratioX, ratioY;

	private Screen activeScreen;
	
	protected ArrayList<Screen> screens;

	//used to hold key codes from the keyboard
	private ArrayList<Integer> keys;
	
	/**
	 * initializes fields
	 */
	public DrawingSurface() {

		screens = new ArrayList<Screen>();

		keys = new ArrayList<Integer>();
		
		Menu screen1 = new Menu(this);
		screens.add(screen1);

		Instructions screen2 = new Instructions(this);
		screens.add(screen2);
		
		PlatformMode screen3 = new PlatformMode(this);
		screens.add(screen3);
		
		/*
		BattleMode screen4 = new BattleMode(this);
		screens.add(screen4);
		*/
		
		switchScreen(ScreenSwitcher.MENU);
	}
	
	/**
	 * initializes the size of the screen
	 */
	public void settings() {
		// size(DRAWING_WIDTH, DRAWING_HEIGHT, P2D);
		size(activeScreen.DRAWING_WIDTH, activeScreen.DRAWING_HEIGHT);
		pixelDensity(displayDensity());
	}

	/**
	 * runs setup() in the different screens
	 */
	public void setup() {
		
		surface.setResizable(true);
		for (Screen s : screens)
			s.setup();

	}

	/**
	 * draws the screen
	 */
	public void draw() {
		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		pushMatrix();

		scale(ratioX, ratioY);

		activeScreen.draw();
		

		popMatrix();
		System.out.println("redrawn");
	}

	/**
	 * returns the current x and y coordinate correctly modified by screen size
	 * @param actual the Point
	 * @return the new Point that is at the correct position with relation to screen size
	 */
	public Point actualCoordinates(Point actual) {
		return new Point((int) (actual.getX() / ratioX), (int) (actual.getY() / ratioY));
	}

	@Override
	/**
	 * switches the screen to the indicated one
	 * @param i the screen to switch to
	 */
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}

	/**
	 * records the key that is pressed
	 */
	public void keyPressed() {
		keys.add(keyCode);
	}

	/**
	 * records which key has been released
	 */
	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	/**
	 * records the key that is pressed 
	 * @param code the code of the key that is pressed
	 * @return
	 */
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	/**
	 * records the mouse being pressed
	 */
	public void mousePressed() {
		activeScreen.mousePressed();
	}
	
	/**
	 * records the mouse the mouse being moved
	 */
	public void mouseMoved() {
		activeScreen.mouseMoved();
	}
	
	/**
	 * records the mouse being dragged 
	 */
	public void mouseDragged() {
		activeScreen.mouseDragged();
	}
	
	/**
	 * records the mouse being released
	 */
	public void mouseReleased() {
		activeScreen.mouseReleased();
	}
	
	public void addScreen(Screen temp) {
		temp.setup();
		screens.add(temp);
		activeScreen = temp;
	}

	public void removeScreen(int i) {
		screens.remove(screens.size() - 1);
	}
}

