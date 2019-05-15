package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import veggie.model.Entity;
import veggie.model.PlayerController;
import veggie.textReader.FileIO;

public class BattleMode extends Screen {

	private Entity player, enemy;

	private DrawingSurface surface;

	private Rectangle[] button;

	private Gif hitimage;

	// sets to 0 before and after each mouseclick
	private int MouseClick = 0;

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
		hitimage = new Gif(surface, "images" + FileIO.fileSep + "hit-effect.gif");

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

		// remember to initialize the main player and enemy player at different
		// locations

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

		if (1 == MouseClick) {
			hitimage.play();
			surface.image(hitimage, , b);
		}

	}

	/**
	 * checks if mouse is being pressed and switches screens if it is on a button
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if (button[1].contains(p))
			MouseClick = 1;
		if (button[2].contains(p))
			MouseClick = 2;
		if (button[3].contains(p))
			MouseClick = 3;
		if (button[4].contains(p))
			MouseClick = 4;

	}

	/**
	 * changes the health of the player or the enemy to account for healing or
	 * damage done
	 * 
	 * @param e      the Entity that is being attacked/ healed
	 * @param damage the damage/healing that is occurring to the Entity object
	 */
	public void changeHealth(Entity e, int damage) {
		int health = e.getStatistics().getHealth();

		e.getStatistics().setHealth(health - damage);

	}

	/**
	 * returns whether or not there is a successful critical hit on the opponent
	 * 
	 * @param critChance the critical hit rate of the attacking entity
	 * @return true if there was a critical, else false.
	 */
	public boolean crit(double critChance) {
		double crit = Math.random();

		if (crit <= critChance)
			return true;
		else
			return false;

	}

}
