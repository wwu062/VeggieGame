package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;


public class GameOver extends Screen{
	

		private DrawingSurface surface;

		private Rectangle backButton;

		private int timer;
		
		private static final int DRAWING_HEIGHT = 600, DRAWING_WIDTH = 800;


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
			timer = 0;
		}

		/**
		 * draws to the screen
		 */
		public void draw()
		{
			surface.background(255 - timer);
			
			surface.pushStyle();
			surface.fill(timer);
			surface.text("Game Over", DRAWING_WIDTH/2 - 50, DRAWING_HEIGHT/2);
			surface.popStyle();
			
			if(timer < 255) {
				timer++;
			} else {
				surface.pushStyle();
				surface.fill(255);
				surface.rect(backButton.x, backButton.y, backButton.width, backButton.height);
				surface.fill(0);
				String a = "Back";
				float w = surface.textWidth(a);
				surface.text(a, backButton.x + backButton.width / 2 - w / 2, backButton.y + backButton.height / 2);
				surface.popStyle();
			}


		}

		/**
		 * checks if mouse is being pressed and switches screens if it is on a button
		 */
		public void mousePressed()
		{
			if(timer >= 255) {
				Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
				if(backButton.contains(p)) {
					surface.setup();
					surface.switchScreen(ScreenSwitcher.MENU);
				}
			}

		}

	

}
