package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import javax.tools.FileObject;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import veggie.textReader.FileIO;

public class GameOver extends Screen{
	

		private DrawingSurface surface;

		private Rectangle backButton;

		private PImage backimg;
		private PImage logoimg;


		/**
		 * initializes fields
		 * 
		 * @param surface initial DrawingSurface object
		 */
		public GameOver(DrawingSurface surface)
		{
			super(800, 600);
			this.surface = surface;

			backButton = new Rectangle(800 / 2 - 100, 1000 / 2 - 100, 200, 150);
		}

		/**
		 * sets up the backimg
		 */
		public void setup()
		{
			backimg = surface.assets.get("background1");
			logoimg = surface.assets.get("logo");
		}

		/**
		 * draws to the screen
		 */
		public void draw()
		{
			backimg.resize(DRAWING_WIDTH, DRAWING_HEIGHT);

			logoimg.resize(backimg.width / 2, backimg.height / 7);

			surface.background(250, 250, 250);

			surface.imageMode(PConstants.CORNER);

			surface.image(backimg, 0, 0);
			surface.image(logoimg, DRAWING_WIDTH / 2 - 200, DRAWING_HEIGHT / 4 - 75);

			surface.pushStyle();
			surface.fill(255);
			surface.rect(backButton.x, backButton.y, backButton.width, backButton.height);
			surface.fill(0);
			String a = "Back";
			float w = surface.textWidth(a);
			surface.text(a, backButton.x + backButton.width / 2 - w / 2, backButton.y + backButton.height / 2);
			surface.popStyle();

		}

		/**
		 * checks if mouse is being pressed and switches screens if it is on a button
		 */
		public void mousePressed()
		{
			Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
			if(backButton.contains(p)) {
				surface.setup();
				surface.switchScreen(ScreenSwitcher.MENU);
			}

		}

	

}
