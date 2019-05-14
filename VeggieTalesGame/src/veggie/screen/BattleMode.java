package veggie.screen;

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
		this.surface = surface;

		button = new Rectangle[4];

		// column 1 initialization
		for (int i = 0; i < 2; i++) {
			button[i] = new Rectangle(150, 400 + 125 * i, 225, 75);
		}

		// column 2 initialization
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

		// draws buttons
		surface.pushStyle();
		surface.rect(button[1].x, button[1].y, button[1].width, button[1].height);
		surface.fill(0);
		String a = "Play";
		float w = surface.textWidth(a);
		surface.text(a, button[1].x + button[1].width / 2 - w / 2, button[1].y + button[1].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[2].x, button[2].y, button[2].width, button[2].height);
		surface.fill(0);
		String b = "Instructions";
		float y = surface.textWidth(b);
		surface.text(b, button[2].x + button[2].width / 2 - y / 2, button[2].y + button[2].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[3].x, button[3].y, button[3].width, button[3].height);
		surface.fill(0);
		String c = "Play";
		float d = surface.textWidth(c);
		surface.text(c, button[3].x + button[3].width / 2 - d / 2, button[3].y + button[3].height / 2);
		surface.popStyle();

		surface.pushStyle();
		surface.rect(button[4].x, button[4].y, button[4].width, button[4].height);
		surface.fill(0);
		String e = "Instructions";
		float f = surface.textWidth(e);
		surface.text(b, button[4].x + button[4].width / 2 - f / 2, button[4].y + button[4].height / 2);
		surface.popStyle();

	}

}
