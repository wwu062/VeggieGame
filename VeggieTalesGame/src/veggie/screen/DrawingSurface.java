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

//		Instructions screen2 = new Instructions(this);
//		screens.add(screen2);

//		PlatformMode screen3 = new PlatformMode(this);
//		screens.add(screen3);

		activeScreen = screens.get(0);
	}

	public void setup() {

		surface.setResizable(true);
		for (Screen s : screens)
			s.setup();

		backimg = loadImage("images" + FileIO.fileSep + "clouds.png");

		playerimg = loadImage("images" + FileIO.fileSep + "lettuce-sprite.gif");
	}

	public void settings() {
		// size(DRAWING_WIDTH, DRAWING_HEIGHT, P2D);
		size(activeScreen.DRAWING_WIDTH, activeScreen.DRAWING_HEIGHT);
	}

	public void draw() {

		ratioX = (float) width / activeScreen.DRAWING_WIDTH;
		ratioY = (float) height / activeScreen.DRAWING_HEIGHT;

		pushMatrix();

		scale(ratioX, ratioY);

		activeScreen.draw();

		popMatrix();

	}

	public Point assumedCoordinatesToActual(Point assumed) {
		return new Point((int) (assumed.getX() * ratioX), (int) (assumed.getY() * ratioY));
	}

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
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

}
