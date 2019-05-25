package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import processing.core.PConstants;
import processing.core.PImage;
import veggie.textReader.FileIO;

/**
 * 
 * @author Alex
 * 
 * Represents the "Instructions" screen when you click the instructions button on the menu.
 *
 */
public class Instructions extends Screen {

	private DrawingSurface surface;
	private PImage backimg;

	private Rectangle backbutton;

	/** Constructs a new Instructions screen
	 * 
	 * @param surface the DrawingSurface on which the screen is to be drawn on.
	 */
	public Instructions(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;

		backbutton = new Rectangle(800 / 2 - 75, 600 / 2 + 100, 150, 100);
	}
	
	public void setup() {
		backimg = surface.loadImage("images" + FileIO.fileSep + "clouds.png");
	}
	
	public void draw() {

		backimg.resize(DRAWING_WIDTH, DRAWING_HEIGHT);

		surface.background(250, 250, 250);

		surface.imageMode(PConstants.CORNER);

		surface.image(backimg, 0, 0);

		surface.pushStyle();
		surface.textAlign(PConstants.CENTER);
		surface.textSize(30);
		surface.text("ARROW KEYS - move around", DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2 - 100);
		surface.textSize(20);
		surface.text("Click to use abilities in battle", DRAWING_WIDTH / 2, DRAWING_HEIGHT / 2 );
		surface.popStyle();

		surface.pushStyle();
		surface.rect(backbutton.x, backbutton.y, backbutton.width, backbutton.height);
		surface.fill(0);
		String a = "Back";
		float w = surface.textWidth(a);
		surface.text(a, backbutton.x + backbutton.width / 2 - w / 2, backbutton.y + backbutton.height / 2);
		surface.popStyle();

	}
	
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if (backbutton.contains(p))
			surface.switchScreen(ScreenSwitcher.MENU);
	}

}