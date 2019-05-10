package veggie.screen;

import java.awt.Point;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import veggie.textReader.FileIO;

public class DrawingSurface extends PApplet implements ScreenSwitcher {

	public float ratioX, ratioY;

	private ArrayList<Integer> keys;

	private Screen activeScreen;
	private ArrayList<Screen> screens;

	protected PImage backimg;


	protected PImage playerimg;

	public DrawingSurface() {

		screens = new ArrayList<Screen>();

		keys = new ArrayList<Integer>();
		

		Menu screen1 = new Menu(this);
		screens.add(screen1);

		Instructions screen2 = new Instructions(this);
		screens.add(screen2);

		// add screens 3 and 4 when figure out how to add pictures in different screens

		activeScreen = screens.get(0);
	}
	
	public void settings() {
		// size(DRAWING_WIDTH, DRAWING_HEIGHT, P2D);
		size(activeScreen.DRAWING_WIDTH, activeScreen.DRAWING_HEIGHT);
	}

	public void setup() {
		
		playerimg = loadImage("images" + FileIO.fileSep + "lettuce-sprite.gif");
		

		surface.setResizable(true);
		for (Screen s : screens)
			s.setup();

	}

	public void draw() {
		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		pushMatrix();

		scale(ratioX, ratioY);

		activeScreen.draw();

		popMatrix();

	}

	/**
	 * returns the current x and y coordinate correctly modified by screen size
	 * @param actual the Point
	 * @return the new Point that is at the correct position with relation to screen size
	 */
	public Point actualCoordinatesToAssumed(Point actual) {
		return new Point((int) (actual.getX() / ratioX), (int) (actual.getY() / ratioY));
	}

	@Override
	public void switchScreen(int i) {
		activeScreen = screens.get(i);
	}

	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	public void mousePressed() {
		activeScreen.mousePressed();
	}
	
	public void mouseMoved() {
		activeScreen.mouseMoved();
	}
	
	public void mouseDragged() {
		activeScreen.mouseDragged();
	}
	
	public void mouseReleased() {
		activeScreen.mouseReleased();
	}
}

