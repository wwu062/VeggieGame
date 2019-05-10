package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import processing.core.PImage;
import veggie.textReader.FileIO;

public class Menu extends Screen {

	/**
	 * the DrawingSurface being used to draw
	 */
	private DrawingSurface surface;

	/**
	 * buttons used to click on and switch screens
	 */
	private Rectangle playbutton, instructbutton;

	/**
	 * background image
	 */
	private PImage backimg;

	/**
	 * initializes fields
	 * @param surface initial DrawingSurface object
	 */
	public Menu(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		playbutton = new Rectangle(800 / 2 - 100, 600 / 2 - 100, 200, 150);

		instructbutton = new Rectangle(800 / 2 - 100, 600 / 2 + 100, 200, 150);
	}

	/**
	 * sets up the backimg
	 */
	public void setup() {
		backimg = surface.loadImage("images" + FileIO.fileSep + "clouds.png");
	}

	/**
	 * draws to the screen
	 */
	public void draw() {

		surface.pushStyle();

		surface.background(250, 250, 250);

		surface.rect(playbutton.x, playbutton.y, playbutton.width, playbutton.height);
		surface.fill(0);
		String a = "Play";
		float w = surface.textWidth(a);
		surface.text(a, playbutton.x + playbutton.width / 2 - w / 2, playbutton.y + playbutton.height / 2);

		surface.popStyle();

		surface.pushStyle();
		surface.rect(instructbutton.x, instructbutton.y, instructbutton.width, instructbutton.height);
		surface.fill(0);
		String b = "Instructions";
		float y = surface.textWidth(b);
		surface.text(b, instructbutton.x + instructbutton.width / 2 - y / 2,
				instructbutton.y + instructbutton.height / 2);
		surface.popStyle();
	}

	/**
	 * checks if mouse is being pressed and switches screens if it is on a button
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if (playbutton.contains(p))
			surface.switchScreen(ScreenSwitcher.PLATFORM);
		if (instructbutton.contains(p))
			surface.switchScreen(ScreenSwitcher.INSTRUCTION);

	}

}
