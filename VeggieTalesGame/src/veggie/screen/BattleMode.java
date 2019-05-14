package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import veggie.model.Entity;
import veggie.model.PlayerController;

public class BattleMode extends Screen {

	private Entity player, enemy;

	private DrawingSurface surface;

	private Rectangle[] button;

	/**
	 * initializes the fields
	 * 
	 * @param surface DrawingSurface object
	 * @param player  the Player Entity object
	 * @param enemy   the Enemy Entity object
	 */
	public BattleMode(DrawingSurface surface, Entity player, Entity enemy) {
		super(800, 600);
		this.player = player;
		this.enemy = enemy;
		
		// changes location of the player and enemy for battle arena
		player.getControls().changeBy(200, 200);
		enemy.getControls().changeBy(600, 200);
		
		this.surface = surface;

		button = new Rectangle[4];

		// attacks column 1 initialization
		for (int i = 0; i < 2; i++) {
			button[i] = new Rectangle(150, 400 + 125 * i, 225, 75);
		}

		// attacks column 2 initialization
		for (int i = 0; i < 2; i++) {
			button[i] = new Rectangle(425, 400 + 125 * i, 225, 75);
		}

	}

	public void setup() {

	}

	/**
	 * Draws the battle
	 * 
	 * @post changes background color
	 */
	public void draw() {
		PlayerController mainplayer = player.getControls();
		PlayerController enemyplayer = enemy.getControls();

		surface.background(255, 255, 255);

		mainplayer.draw(surface);
		enemyplayer.draw(surface);
		
		// remember to initialize the main player and enemy player at different locations

		// draws buttons
		surface.pushStyle();
		surface.rect(button[1].x, button[1].y, button[1].width, button[1].height);
		surface.fill(0);
		String move1 = player.getMoveList()[1].getName();
		float w = surface.textWidth(move1);
		surface.text(move1, button[1].x + button[1].width / 2 - w / 2, button[1].y + button[1].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[2].x, button[2].y, button[2].width, button[2].height);
		surface.fill(0);
		String move2 = player.getMoveList()[2].getName();
		float y = surface.textWidth(move2);
		surface.text(move2, button[2].x + button[2].width / 2 - y / 2, button[2].y + button[2].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[3].x, button[3].y, button[3].width, button[3].height);
		surface.fill(0);
		String move3 = player.getMoveList()[3].getName();
		float d = surface.textWidth(move3);
		surface.text(move3, button[3].x + button[3].width / 2 - d / 2, button[3].y + button[3].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[4].x, button[4].y, button[4].width, button[4].height);
		surface.fill(0);
		String move4 = player.getMoveList()[4].getName();
		float f = surface.textWidth(move4);
		surface.text(move4, button[4].x + button[4].width / 2 - f / 2, button[4].y + button[4].height / 2);
		surface.popStyle();
		
		

	}
	
	/**
	 * checks if mouse is being pressed and switches screens if it is on a button
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if (button[1].contains(p))
			surface.switchScreen(ScreenSwitcher.PLATFORM);
		if (button[2].contains(p))
			surface.switchScreen(ScreenSwitcher.INSTRUCTION);
		if (button[3].contains(p))
			
		if (button[4].contains(p))

	}

	
}
