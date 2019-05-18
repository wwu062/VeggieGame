package veggie.screen;

import java.awt.Point;
import java.awt.Rectangle;

import gifAnimation.Gif;
import processing.core.PGraphics;
import veggie.model.Entity;
import veggie.model.PlayerController;
import veggie.textReader.FileIO;

/**
 * The battle screen for the game (think pokemon battle)
 * 
 * @author williamwu
 *
 */
public class BattleMode extends Screen {

	private Entity player, enemy;
	private DrawingSurface surface;

	private Rectangle[] button;

	private Gif hitimg, player_attackimg;

	// graphics for the panel, initial state of the players, and final state during
	// a move
	private PGraphics panels, istate, fstate, healthpanel;

	// keeps check of if it is the players turn or the enemy's
	private int turncounter = 0;

	// sets to 0 before and after each mouse click
	private int MouseClick = 0;

	private int timer;
	
	/**
	 * initializes the fields
	 * 
	 * @param surface DrawingSurface object
	 * @param player  the Player Entity object
	 * @param enemy   the Enemy Entity object
	 */
	public BattleMode(DrawingSurface surface) {
		super(800, 600);

		this.surface = surface;

		button = new Rectangle[4];

		// attacks column 1 initialization
		for (int i = 0; i < 2; i++) {
			button[i] = new Rectangle(150, 375 + 125 * i, 225, 75);
		}

		// attacks column 2 initialization
		for (int i = 0; i < 2; i++) {
			button[i + 2] = new Rectangle(425, 375 + 125 * i, 225, 75);
		}
		
		timer = 0;

	}

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
			button[i] = new Rectangle(150, 375 + 125 * i, 225, 75);
		}

		// attacks column 2 initialization
		for (int i = 0; i < 2; i++) {
			button[i + 2] = new Rectangle(425, 375 + 125 * i, 225, 75);
		}
		
		timer = 0;

	}

	public void addBattlePlayers(Entity player, Entity enemy) {
		this.player = player;
		this.enemy = enemy;

		// changes location of the player and enemy for battle arena
		player.getControls().changeBy(200, 200);
		enemy.getControls().changeBy(600, 200);

	}

	public void setup() {
		hitimg = new Gif(surface, "images" + FileIO.fileSep + "hit-effect.gif");
		player_attackimg = new Gif(surface, "images" + FileIO.fileSep + "lettuce-sprite-attack.gif");

		panels = surface.createGraphics(800, 600);
		istate = surface.createGraphics(800, 300);
		fstate = surface.createGraphics(800, 300);
		healthpanel = surface.createGraphics(800, 600);

	}

	/**
	 * Draws the battle
	 * 
	 * @post changes background color
	 */
	public void draw() {
		surface.background(255, 255, 255);

		// remember to initialize the main player and enemy player at different
		// locations

		// draws buttons
		
		panels.beginDraw();

		for(int i = 0; i < button.length; i++) {
			panels.pushStyle();
			panels.rect(button[i].x, button[i].y, button[i].width, button[i].height);
			panels.fill(0);
			String move = player.getMoveList()[i].getName();
			float w = panels.textWidth(move);
			panels.text(move, button[i].x + button[i].width / 2 - w / 2, button[i].y + button[i].height / 2);
			panels.popStyle();
		}

		panels.endDraw();

		surface.image(panels, 0, 0);

		istate.beginDraw();

		// if no mouseclicking. background is white.
		if (0 == MouseClick)
			istate.background(255);

		player.getControls().draw(istate);
		enemy.getControls().draw(istate);

		istate.endDraw();

		surface.image(istate, 0, 0);

		if (turncounter % 2 == 0) {
			// when mouse is clicked, clears with white screen.
			if (0 != MouseClick) {
				istate.beginDraw();
				istate.background(255);
				istate.endDraw();

				surface.image(istate, 0, 0);
				
				turncounter++;
			}

			if (1 == MouseClick) {
				System.out.println("1");
				fstate.beginDraw();

				fstate.image(player_attackimg, 200, 200);
				surface.delay(1000);
				hitimg("player");

				fstate.endDraw();

				surface.image(fstate, 0, 0);

				boolean crit = crit(player.getStatistics().getCritrate());
				if (crit)
					changeHealth(enemy, player.getMoveList()[0].getAttackval() + 10);
				else
					changeHealth(enemy, player.getMoveList()[0].getAttackval());

				MouseClick = 0;

			}
			if (2 == MouseClick) {
				fstate.beginDraw();

				fstate.image(player_attackimg, 200, 200);
				surface.delay(1000);
				hitimg("player");

				fstate.endDraw();

				surface.image(fstate, 0, 0);

				boolean crit = crit(player.getStatistics().getCritrate());
				if (crit)
					changeHealth(enemy, player.getMoveList()[1].getAttackval() + 10);
				else
					changeHealth(enemy, player.getMoveList()[1].getAttackval());

				MouseClick = 0;

			}
			if (3 == MouseClick) {
				fstate.beginDraw();

				fstate.image(player_attackimg, 200, 200);
				hitimg("player");

				fstate.endDraw();

				surface.image(fstate, 0, 0);

				boolean crit = crit(player.getStatistics().getCritrate());
				if (crit)
					changeHealth(enemy, player.getMoveList()[2].getAttackval() + 10);
				else
					changeHealth(enemy, player.getMoveList()[2].getAttackval());

				MouseClick = 0;

			}
			if (4 == MouseClick) {
				fstate.beginDraw();

				
				fstate.image(player_attackimg, 200, 200);
				surface.delay(1000);
				hitimg("player");

				fstate.endDraw();

				surface.image(fstate, 0, 0);

				boolean crit = crit(player.getStatistics().getCritrate());
				if (crit)
					changeHealth(enemy, player.getMoveList()[3].getAttackval() + 10);
				else
					changeHealth(enemy, player.getMoveList()[3].getAttackval());

				MouseClick = 0;
				
			}

		} else {
			
			istate.beginDraw();
			istate.background(255);
			istate.endDraw();
			surface.image(istate, 0, 0);
			
			fstate.beginDraw();

			fstate.image(player_attackimg, 600, 200);
			surface.delay(1000);
			hitimg("enemy");

			fstate.endDraw();

			
			surface.image(fstate, 0, 0);
			
			int enemyattack = enemy.getMoveList()[((int)(Math.random()*4))].getAttackval();
			
			changeHealth(player, enemyattack);
			
			turncounter++; 
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		healthpanel.beginDraw();
		//player
		
		healthpanel.fill(0);
		healthpanel.rect(100, 50, 100, 50);
		healthpanel.fill(255, 0, 0);
		healthpanel.rect(100, 50, player.getStatistics().getHealth(), 50);
		//enemy
		healthpanel.fill(0);
		healthpanel.rect(600, 50, 100, 50);
		healthpanel.fill(255, 0, 0);
		healthpanel.rect(600, 50, enemy.getStatistics().getHealth(), 50);
	
		
		healthpanel.endDraw();
		
		surface.image(healthpanel, 0, 0);

		
		timer++;
	}

	/**
	 * Checks if the hit image should land on the player or enemy then draws the gif
	 * 
	 * @pre Argument only takes "player" or "enemy"
	 * @param Entity the "player" or "enemy" that will be damaged
	 */
	
	public void hitimg(String Entity) {
		hitimg.play();
		if (Entity.equalsIgnoreCase("enemy"))
			fstate.image(hitimg, 590, 190, 32, 32);
		if (Entity.equalsIgnoreCase("player"))
			fstate.image(hitimg, 190, 190, 32, 32);

	}

	/**
	 * checks if mouse is being pressed and switches screens if it is on a button
	 */
	public void mousePressed() {
		Point p = surface.actualCoordinates(new Point(surface.mouseX, surface.mouseY));
		if (button[0].contains(p))
			MouseClick = 1;
		if (button[1].contains(p))
			MouseClick = 2;
		if (button[2].contains(p))
			MouseClick = 3;
		if (button[3].contains(p))
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
